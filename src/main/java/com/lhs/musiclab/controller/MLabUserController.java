package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.pojo.MLabUserCount;
import com.lhs.musiclab.pojo.MoodSign;
import com.lhs.musiclab.pojo.StarFolder;
import com.lhs.musiclab.service.MLabUserCountService;
import com.lhs.musiclab.service.MLabUserService;
import com.lhs.musiclab.service.MoodSignService;
import com.lhs.musiclab.service.StarFolderService;
import com.lhs.musiclab.utils.MD5Utils;
import com.lhs.musiclab.utils.MyRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mlabuser")
//origins  ： 允许可访问的域列表
//maxAge：准备响应前的缓存持续的最大时间（以秒为单位）。
@CrossOrigin(origins = "*", maxAge = 3600)
public class MLabUserController {
    @Autowired
    private MLabUserService mLabUserService;
    @Autowired
    private MLabUserCountService mLabUserCountService;
    @Autowired
    private MoodSignService moodSignService;
    @Autowired
    private StarFolderService starFolderService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /***
     * 返回所有用户
     * @return 用户数组
     */
    @GetMapping("/list")
    public List<MLabUser> list(){
        return mLabUserService.list();
    }

    /***
     * 根据用户id查找用户
     * @param id 用户id
     * @return 用户集合
     */
    @GetMapping("/get/{id}")
    public List<MLabUser> get(@PathVariable(value = "id") String id){
        MLabUser mLabUser = new MLabUser();
        mLabUser.setUid(id);
        return mLabUserService.get(mLabUser);
    }

    /***
     * 获取当前用户的登陆信息
     * @param request
     * @return 执行结果
     */
    @GetMapping("/isLogin")
    public Map getLoginInfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();

        String userID = (String) request.getSession().getAttribute("userID");
        String userName = (String) request.getSession().getAttribute("userName");
        String headImg = (String) request.getSession().getAttribute("headImg");
        if (userName!=null){
            MoodSign moodSign = moodSignService.getByUid(userID);
            map.put("msg", "true");
            map.put("moodsign",moodSign);
            map.put("userID", userID);
            map.put("userName", userName);
            map.put("headImg", headImg);
            return map;
        }else {
            map.put("msg", "false");
            return map;
        }
    }

    /***
     * 当前用户登出
     * @param request
     * @return 执行结果
     */
    @GetMapping("/logout")
    public Map logout(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        session.removeAttribute("userName");
        session.removeAttribute("headImg");
        session.removeAttribute("userID");
        map.put("msg", "true");
        return map;
    }

    /***
     * 处理登陆请求
     * @param account 账号
     * @param pwd 密码
     * @param request
     * @return 用户相关信息与执行结果
     */
    @PostMapping("/login")
    public Map login(@RequestParam("account") String account,
                     @RequestParam("pwd") String pwd,HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        logger.debug(account+"-"+pwd);
        MLabUser mLabUser = new MLabUser();
        mLabUser.setName(account);
        mLabUser.setEmail(account);
        mLabUser.setPhone(account);
        List<MLabUser> list = mLabUserService.matchOr(mLabUser);
        if(!list.isEmpty() && MD5Utils.md5(pwd).equals(list.get(0).getPwd())){
            MLabUser muser =list.get(0);
            map.put("msg", "true");
            map.put("userName", muser.getName());
            map.put("headImg", muser.getHead_img());
            map.put("userID",muser.getUid());
            HttpSession session = request.getSession();
            session.setAttribute("userName",muser.getName());
            session.setAttribute("headImg",muser.getHead_img());
            session.setAttribute("userID",muser.getUid());
            mLabUserCountService.updateRecentlyLoginForRedis(muser.getUid(),new Date());
        }else {
            map.put("msg", "false");
        }
        return map;
    }

    /***
     * 注册新的用户
     * @param mLabUser 用户信息
     * @param request
     * @return 用户信息以及执行结果
     */
    @PostMapping("/add")
    @Transactional
    public Map register(MLabUser mLabUser, HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        mLabUser.setUid(MyRandom.getUUID());
        mLabUser.setPwd(MD5Utils.md5(mLabUser.getPwd()));
        mLabUser.setHead_img("/image/defaulthead.jpg");
        mLabUser.setBlogbcg_img("/image/defaulthead.jpg");
        mLabUser.setRqcode_img("default");
        if(mLabUserService.add(mLabUser)==1){
            //生成用户统计
            mLabUserCountService.add(mLabUser.getUid(), new Date());
            //生成用户的默认收藏夹
            StarFolder starFolder = new StarFolder();
            starFolder.setUid(mLabUser.getUid());
            starFolder.setFolder(0);
            starFolder.setFolderName("默认收藏夹");
            starFolderService.add(starFolder);
            //往session放用户凭证
            HttpSession session = request.getSession();
            session.setAttribute("userName",mLabUser.getName());
            session.setAttribute("headImg",mLabUser.getHead_img());
            session.setAttribute("userID",mLabUser.getUid());
            map.put("userName", mLabUser.getName());
            map.put("headImg", mLabUser.getHead_img());
            map.put("msg", "true");
        }else {
            map.put("msg", "false");
        }
        return map;
    }

    /***
     * 获取个人页面信息
     * @param uid
     * @return 信息集合
     */
    @GetMapping(value = "/info/{uid}")
    public Map<String,Object> getUserInfo(@PathVariable(value = "uid")String uid){
        MLabUser mLabUser = new MLabUser();
        mLabUser.setUid(uid);
        MLabUser match = mLabUserService.match(mLabUser);
        MLabUserCount mLabUserCount = mLabUserCountService.get(uid);
        MoodSign moodSign = moodSignService.getByUid(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("moodsign", moodSign);
        map.put("mLabUser", match);
        map.put("mLabUserCount", mLabUserCount);
        return map;
    }

    /***
     * 检查有无重复用户名
     * @param name
     * @return 执行结果
     */
    @GetMapping("/name/{name}")
    public Map<String, String> nameIsExist(@PathVariable(value = "name")String name){
        Map<String, String> map = new HashMap<>();
        MLabUser mLabUser = new MLabUser();
        mLabUser.setName(name);
        if(mLabUserService.match(mLabUser)==null){
            map.put("msg", "false");
        }else {
            map.put("msg", "true");
        }
        return map;
    }

    /***
     * 检查重复邮箱
     * @param email
     * @return 执行结果
     */
    @GetMapping("/email/{email}")
    public Map<String, String> emailIsExist(@PathVariable(value = "email")String email){
        Map<String, String> map = new HashMap<>();
        MLabUser mLabUser = new MLabUser();
        mLabUser.setEmail(email);
        if(mLabUserService.match(mLabUser)==null){
            map.put("msg", "false");
        }else {
            map.put("msg", "true");
        }
        return map;
    }

    /***
     * 检查手机号
     * @param phone
     * @return 执行结果
     */
    @GetMapping("/phone/{phone}")
    public Map<String, String> findByPhone(@PathVariable(value = "phone")String phone){
        Map<String, String> map = new HashMap<>();
        MLabUser mLabUser = new MLabUser();
        mLabUser.setPhone(phone);
        if(mLabUserService.match(mLabUser)==null){
            map.put("msg", "false");
        }else {
            map.put("msg", "true");
        }
        return map;
    }

    //日期类型转换
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
