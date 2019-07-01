package com.example.authorization.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.authorization.entity.UserOAuthEntity;

public interface IUserOAuth2Service extends IService<UserOAuthEntity> {

    UserOAuthEntity getUserOAuth2(String userName);
}
