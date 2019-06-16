package com.lhs.musiclab.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.lhs.musiclab.utils.SendCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sendsms")
public class SendSMSController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private Map<String, String> msg = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/phone/{phone}")
    public Map sendPhoneSms(@PathVariable(value = "phone") String phone){
        /*String code= SendCode.getVerifyCode(SendCode.CODE_NUMBER,SendCode.CODE_LENGTH);
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
        return msg;*/
        String code="123456";
        msg.put("message","发送成功");
        stringRedisTemplate.opsForValue().set("sendCodeTo"+phone,code,60*10, TimeUnit.SECONDS);
        return msg;
    }

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
