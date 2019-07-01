package com.example.authorization.config;

import com.example.authorization.service.impl.CustomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    CustomDetailsService customDetailsService;

    @Autowired
    DataSource dataSource;

    /**
     * 声明 ClientDetails实现
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 使用最基本的JdbcTokenStore生成token
     *
     * @return
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 配置客户端详情服务
     * 客户端详细信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients.inMemory()
                .withClient("client1")//用于标识用户ID
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")//授权方式
                .scopes("test")//授权范围
                //    //客户端安全码,secret密码配置从 Spring Security 5.0开始必须以 {bcrypt}+加密后的密码 这种格式填写
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .redirectUris("http://www.baidu.com")
                .autoApprove(true);*/

        //使用数据库初始化客户信息
        clients.withClientDetails(clientDetails())
                .jdbc()
                .dataSource(dataSource);
        //clients.jdbc(dataSource);


    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * <p>配置token获取合验证时的策略 允许表单验证，浏览器直接发送post请求即可获取token</p>
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置tokenStore,需要配置userDetailsService，否则refresh_token会报错
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(jdbcTokenStore())
                .userDetailsService(customDetailsService);
    }
}
