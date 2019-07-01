package com.example.authorization.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class BaseMainController {


    @GetMapping("/auth/login")
    public String loginPage(Model model) {

        model.addAttribute("loginProcessUrl", "/auth/authorize");

        model.addAttribute("pictureCodeParameterName", "p_code");

        return "base-login";
    }

}
