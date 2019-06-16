package com.lhs.musiclab.controller;

import com.lhs.musiclab.pojo.MLabUser;
import com.lhs.musiclab.service.MLabUserService;
import com.lhs.musiclab.utils.MD5Utils;
import com.lhs.musiclab.utils.MyRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/mlabuser")
public class MLabUserController {
    @Autowired
    private MLabUserService mLabUserService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public List<MLabUser> list(){
        return mLabUserService.list();
    }

    @GetMapping("/get/{id}")
    public List<MLabUser> get(@PathVariable(value = "id") String id){
        MLabUser mLabUser = new MLabUser();
        mLabUser.setUid(id);
        return mLabUserService.get(mLabUser);
    }
    @GetMapping("/isLogin")
    public Map isLogin(HttpServletRequest request){
        Map<String, String> msg = new HashMap<>();
        String userName = (String) request.getSession().getAttribute("userName");
        String headImg = (String) request.getSession().getAttribute("headImg");
        if (userName!=null){
            msg.put("msg", "true");
            msg.put("userName", userName);
            msg.put("headImg", headImg);
            return msg;
        }else {
            msg.put("msg", "false");
            return msg;
        }
    }

    @GetMapping("/logout")
    public Map logout(HttpServletRequest request){
        Map<String, String> msg = new HashMap<>();
        HttpSession session = request.getSession();
        session.removeAttribute("userName");
        session.removeAttribute("headImg");
        session.removeAttribute("userID");
        msg.put("msg", "true");
        return msg;
    }

    @PostMapping("/login")
    public Map login(@RequestParam("account") String account,
                     @RequestParam("pwd") String pwd,HttpServletRequest request){
        Map<String, String> msg = new HashMap<>();
        logger.debug(account+"-"+pwd);
        MLabUser mLabUser = new MLabUser();
        mLabUser.setName(account);
        mLabUser.setEmail(account);
        mLabUser.setPhone(account);
        List<MLabUser> list = mLabUserService.matchOr(mLabUser);
        if(!list.isEmpty() && MD5Utils.md5(pwd).equals(list.get(0).getPwd())){
            MLabUser muser =list.get(0);
            msg.put("msg", "true");
            msg.put("userName", muser.getName());
            msg.put("headImg", muser.getHead_img());
            msg.put("userID",muser.getUid());
            HttpSession session = request.getSession();
            session.setAttribute("userName",muser.getName());
            session.setAttribute("headImg",muser.getHead_img());
            session.setAttribute("userID",muser.getUid());
        }else {
            msg.put("msg", "false");
        }
        return msg;
    }

    @PostMapping("/add")
    public Map register(MLabUser mLabUser, HttpServletRequest request){
        Map<String, String> msg = new HashMap<>();
        mLabUser.setUid(MyRandom.getUUID());
        mLabUser.setPwd(MD5Utils.md5(mLabUser.getPwd()));
        mLabUser.setHead_img("default");
        mLabUser.setBlogbcg_img("default");
        mLabUser.setRqcode_img("default");
        if(mLabUserService.add(mLabUser)==1){
            msg.put("msg", "true");
            HttpSession session = request.getSession();
            session.setAttribute("userName",mLabUser.getName());
            session.setAttribute("headImg",mLabUser.getHead_img());
            session.setAttribute("userID",mLabUser.getUid());
            msg.put("userName", mLabUser.getName());
            msg.put("headImg", mLabUser.getHead_img());
        }else {
            msg.put("msg", "false");
        }
        return msg;
    }

    //日期类型转换
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/name/{name}")
    public Map<String, String> nameIsExist(@PathVariable(value = "name")String name){
        Map<String, String> msg = new HashMap<>();
        MLabUser mLabUser = new MLabUser();
        mLabUser.setName(name);
        if(mLabUserService.match(mLabUser)==null){
            msg.put("msg", "false");
        }else {
            msg.put("msg", "true");
        }
        return msg;
    }
    @GetMapping("/email/{email}")
    public Map<String, String> emailIsExist(@PathVariable(value = "email")String email){
        Map<String, String> msg = new HashMap<>();
        MLabUser mLabUser = new MLabUser();
        mLabUser.setEmail(email);
        if(mLabUserService.match(mLabUser)==null){
            msg.put("msg", "false");
        }else {
            msg.put("msg", "true");
        }
        return msg;
    }
    @GetMapping("/phone/{phone}")
    public Map<String, String> findByPhone(@PathVariable(value = "phone")String phone){
        Map<String, String> msg = new HashMap<>();
        MLabUser mLabUser = new MLabUser();
        mLabUser.setPhone(phone);
        if(mLabUserService.match(mLabUser)==null){
            msg.put("msg", "false");
        }else {
            msg.put("msg", "true");
        }
        return msg;
    }
}
