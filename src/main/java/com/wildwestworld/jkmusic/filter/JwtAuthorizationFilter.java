package com.wildwestworld.jkmusic.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wildwestworld.jkmusic.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// 检验token请求头

//一定要去再次构造一次JwtAuthorizationFilter，因为父级也构造了，所以要构造一次
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //在请求里面过滤
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取header
        String token = request.getHeader(SecurityConfig.HEADER_STRING);
        //如果token是空的，或者token不是以我们自定义的token前半部分开始的
        if(token ==null || !token.startsWith(SecurityConfig.TOKEN_PREFIX)){
            //把request和response继续传递下去
            //拦截下来做预处理，处理之后便执行chain.doFilter(request, response)这个方法.
            // chain.doFilter将请求转发给过滤器链下一个filter , 如果没有filter那就是你请求的资源
            //此处也可以不加，不过走个流程还是必要的
            chain.doFilter(request,response);
            return;
        }
        //getAuthentication(token) 使我们自己定义的一个参数 ，看下面你就知道了
        //不同于上次的UsernamePasswordAuthenticationToken本次搞得UsernamePasswordAuthenticationToken没有密码鉴权
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        //Spring Security使用一个Authentication对象来描述当前用户的相关信息。
        // SecurityContextHolder中持有的是当前用户的SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //把request和response继续传递下去
        //拦截下来做预处理，处理之后便执行chain.doFilter(request, response)这个方法.
        // chain.doFilter将请求转发给过滤器链下一个filter , 如果没有filter那就是你请求的资源
        //必须要加，不然后面走不通
        chain.doFilter(request,response);
    }

    //检验token是否是有效的
    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        if (token != null){
            //去除我们给token人工添加的字符
            //将前缀替换为字符串
            String trueToken = token.replace(SecurityConfig.TOKEN_PREFIX, "");
            //jwtVerifier验证器+解码器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SecurityConfig.SECRET)).build();
            //jwtVerifier.verify后我们能拿到解码后的token，此时的token我们可以拿到对应的信息
            DecodedJWT decodeToken = jwtVerifier.verify(trueToken);

            String username = decodeToken.getClaim("userName").toString();
            if (username != null){
                //不同于上次的UsernamePasswordAuthenticationToken本次搞得UsernamePasswordAuthenticationToken没有密码鉴权
                return new UsernamePasswordAuthenticationToken(username,null,new ArrayList<>());
            }
        }
            return null;


    }

}
