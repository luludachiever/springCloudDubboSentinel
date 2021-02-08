package com.lagou.edu.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface EmailService {
    boolean sendEmail(String email, String code);
}
