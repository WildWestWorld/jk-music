package com.wildwestworld.jkmusic.config;

import com.wildwestworld.jkmusic.exception.RestAuthenticationEntryPoint;
import com.wildwestworld.jkmusic.filter.JWTAuthenticationFilter;
import com.wildwestworld.jkmusic.filter.JwtAuthorizationFilter;
import com.wildwestworld.jkmusic.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

//JWT Security的配置
@Configuration
//使其变Security成配置文件
@EnableWebSecurity
//开启用户的角色访问限制
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true,jsr250Enabled=true)

//extends WebSecurityConfigurerAdapter 是为了配置http请求
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //密匙，用于生成Token
    public static final String SECRET = "JKMusic";
    //过期时间
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    //token的前缀，一般都是Bearer
    public static final String TOKEN_PREFIX = "Bearer ";
    //放在网页请求头的名字叫什么
    public static final String HEADER_STRING = "Authorization";
    //登录的网页路径
    public static final String SIGN_UP_URL = "/users";

    @Resource
    UserService userService;

    @Resource
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    //配置http
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //cors()开启跨域
      http.cors()
              //关闭csrf
              .and().csrf().disable()
              //开启authorizeRequests，也就是鉴权请求
              .authorizeRequests()
              //antMatchers白名单 HttpMethod.POST：采用的方法是POST方法 白名单地址：SIGN_UP_URL   permitAll():可以被所有人访问
              //注意这个白名单，少个/也认不出来，要反复核对
              .antMatchers(SIGN_UP_URL,"/login","/users/**","/tokens/**","/tokens").permitAll()
              //anyRequest() 所有请求   authenticated()：必须鉴权才能用（除开白名单）
              .anyRequest().authenticated()

              .and()
              //过滤器
              //JWTAuthenticationFilter账号密码验证过滤器

              .addFilter(new JWTAuthenticationFilter(authenticationManager()))
              //JwtAuthorizationFilter token 过滤器
              .addFilter(new JwtAuthorizationFilter(authenticationManager(),userService))
              //处理异常
              .exceptionHandling()
              //指定异常的处理方式
              .authenticationEntryPoint(restAuthenticationEntryPoint)
              //关闭session 因为JWT是无状态的模式
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //配置白名单，上面也能配置，但因为要配置太多了，不怎么优雅，所以改用这个方法
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger**/**")
                      .antMatchers("/webjars/**")
                      .antMatchers("/webjars/**")
                      .antMatchers( "/v3/**")
                      .antMatchers("/doc.html")
                      .antMatchers("/wechat/**")
                      .antMatchers("/files/**");

    }

    //指定新的service，当他调用LoadUserByUsername的时候就会调用我们在自己service层写的那个方法了
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}

