package com.example.authorization.controller;


import com.example.authorization.entity.UserOAuthEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LvNing
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {


    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserOAuthEntity> queryAll() {
        return null;

    }


}
