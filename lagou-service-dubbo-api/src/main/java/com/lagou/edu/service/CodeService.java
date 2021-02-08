package com.lagou.edu.service;

public interface CodeService {
    String validateCode(String email, String code) ;
     String createCode(String email);
}
