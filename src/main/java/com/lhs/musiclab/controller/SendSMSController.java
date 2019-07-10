package com.lhs.musiclab.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.lhs.musiclab.utils.SendCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sendsms")
//origins  ： 允许可访问的域列表
//maxAge：准备响应前的缓存持续的最大时间（以秒为单位）。
@CrossOrigin(origins = "*", maxAge = 3600)
public class SendSMSController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private Map<String, String> msg = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(getClass());

    /***
     * 发送短信验证码
     * @param phone 手机号
     * @return 执行结果
     */
    @GetMapping("/phone/{phone}")
    public Map sendPhoneSms(@PathVariable(value = "phone") String phone){
        String code= SendCode.getVerifyCode(SendCode.CODE_NUMBER,SendCode.CODE_LENGTH);
        try {
            SendSmsResponse sendSmsResponse=SendCode.sendSms(phone,code);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                msg.put("msg","发送成功");
                stringRedisTemplate.opsForValue().set("sendCodeTo"+phone,code,60*10, TimeUnit.SECONDS);
                return msg;
            }
        } catch (ClientException e) {
            e.printStackTrace();

        }
        msg.put("msg","发送失败");
        return msg;
        /*String code="123456";
        msg.put("message","发送成功");
        stringRedisTemplate.opsForValue().set("sendCodeTo"+phone,code,60*10, TimeUnit.SECONDS);
        return msg;*/
    }

    /***
     * 校验验证码
     * @param phone
     * @param code
     * @return 执行结果
     */
    @GetMapping("/code/{phone}/{code}")
    public Map checkPhoneCode(@PathVariable(value = "phone") String phone,
                              @PathVariable(value = "code") String code){
        if(code.equals(stringRedisTemplate.opsForValue().get("sendCodeTo"+phone))){
            msg.put("msg", "true");
        }else {
            msg.put("msg", "false");
        }
        return msg;
    }

}
