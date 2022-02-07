package com.wildwestworld.jkmusic.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildwestworld.jkmusic.config.SecurityConfig;
import com.wildwestworld.jkmusic.entity.User;
import com.wildwestworld.jkmusic.exception.BizException;
import com.wildwestworld.jkmusic.exception.BizExceptionType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
//UsernamePasswordAuthenticationFilter 来自于security
//我们需要重写两个方法，
//一个是attemptAuthentication，这个方法的作用是我们去从request中拿到username和password，然后set到AuthenticationManager 里面让他去鉴权
// 一个是successfulAuthentication，这个方法的作用是 账号密码验证成功后生成JWT并放到http里面的header里面

//逻辑是验证账号密码是否正确，弱正确，就给他token放到请求头上


//JWTAuthenticationFilter 鉴权过滤器
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //AuthenticationManager需要实例化一下，不然没法用
    //token验证器
   private  final AuthenticationManager authenticationManager;
    //AuthenticationManager需要实例化一下，不然没法用
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    //一个是attemptAuthentication，这个方法的作用是我们去从request中拿到username和password，然后set到AuthenticationManager 里面让他去鉴权
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//principal 认证前需要传 String 类型的 username，认证后需要传实现了UserDetails 接口的用户对象；
// 而 credentials 认证前需要传前端传来的String类型的password，认证后需要传 String类型的密码 或者 null。
// 通常都是，接收 前端传来的 username 和 password，调用 两个参数的构造器，创建一个未认证的对象，
// 交给AuthenticationManager进行认证。认证成功后，调用三个参数的构造器，创建一个已认证的对象。

        //getInputStream爆红 try,catch一下Error就行了毕竟是IO可能没办法进行映射，所以要用catch
        try {
            //该方法来自于security官方，作用是把json里面的变量映射到user实体类里面去
            //就是从request中的stream里面获取json映射到user实体类里面去
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            // new UsernamePasswordAuthenticationToken(principal,  credentials,List)
            //这里的对应的principal和credentials就是Username和Password
            UsernamePasswordAuthenticationToken needVerifyUser = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
            // 通常都是，接收 前端传来的 username 和 password，调用 两个参数的构造器，创建一个未认证的对象，
            // 交给AuthenticationManager进行认证。认证成功后，调用三个参数的构造器，创建一个已认证的对象。
            return authenticationManager.authenticate(needVerifyUser);
        } catch (IOException e) {
            throw new BizException(BizExceptionType.FORBIDDEN);
        }

    }
    // 一个是successfulAuthentication，这个方法的作用是 账号密码验证成功后生成JWT并放到http里面的header里面
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //创建JWT的构造器
        JWTCreator.Builder builder = JWT.create();
        //authResult.getPrincipal()可以拿到我们上面放进去的Username
        User saveUser = (User) authResult.getPrincipal();
        String saveUsername = saveUser.getUsername();

        //withSubject("userName", saveUsername) 存放信息userName
        String token = builder.withSubject(saveUsername)
                //System.currentTimeMillis() 获得的是自1970-1-01 00:00:00.000 到当前时刻的时间距离,类型为long
                //SecurityConfig.EXPIRATION_TIME来自于我们自定义的数据
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET));

        // response.addHeader(参数1：在header里面token的名字,参数1：完整token);
        //把response中添加请求头
        response.addHeader(SecurityConfig.HEADER_STRING,SecurityConfig.TOKEN_PREFIX+token);
    }
}
