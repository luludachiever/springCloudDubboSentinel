package com.lagou.edu.controller;

import com.lagou.edu.service.CodeService;
import com.lagou.edu.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @Reference(timeout = 1800000)
    private EmailService emailFeignClient;

    @GetMapping("/create/{email}")
    public Boolean createCode(@PathVariable("email") String email) {
        String codeStr = codeService.createCode(email);
        try {
            emailFeignClient.sendEmail(email, codeStr);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @GetMapping("/validate/{email}/{code}")
    public String validateCode(@PathVariable("email") String email,@PathVariable("code") String code) {
        return codeService.validateCode(email,code);
    }
}
