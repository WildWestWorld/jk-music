package com.wildwestworld.jkmusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

//该文件用于Swagger的配置
@Configuration

public class SwaggerConfig {
    @Bean
    public Docket docket(){
        //我们这里选择的参数是 DocumentationType.OAS_30,这是一个Swagger实例的接口文档版本，我们这里是3.0，所以选择用 OAS_30，
        //其他的类型还有如下几种，分别对应着Swagger历史版本
        // public static final DocumentationType SWAGGER_12 = new DocumentationType("swagger", "1.2");
        //    public static final DocumentationType SWAGGER_2 = new DocumentationType("swagger", "2.0");
        //    public static final DocumentationType OAS_30 = new DocumentationType("openApi", "3.0");
        return new Docket(DocumentationType.OAS_30)

                //ApiInfo 主要作用是构建我们接口文档的页面显示的基础信息
                //apiInfoBuild() 自定义函数，用于的apiInfo的构建，在下面有兴趣看看
                //enable()是否启用Swagger，默认是true
                .apiInfo(apiInfoBuild()).enable(true)


                /* 设置安全模式，swagger可以设置访问token */

                //Collections.singletonList()返回的是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。

                //securityContexts 存储认证授权的相关信息 我们现在存储的是一个名为JWT 作用范围是全部的 ，这是为我们后面传输JWT做铺垫，这个属于是容器，下面这个就是放东西进容器
                //securityContext() 我们自定义的方法
                .securityContexts(Collections.singletonList(securityContext()))

                //apiKey() 我们自定义的方法
                //关键是在securitySchemes()方法配置里增加需要token的配置。
                //securitySchemes  让右上角多了个锁的图标
                //apiKey这个里面放的是点击锁后出现的内容
                //  public Docket securitySchemes(List<SecurityScheme> securitySchemes) {
                .securitySchemes(Collections.singletonList(apiKey()))


                //构建Docket时通过 select() 方法配置扫描接口
                .select()
                 // RequestHandlerSelectors,配置要扫描接口的方法
                //下面是各种扫描接口的方法

                 // basePackage指定要扫描的包
                // any()扫描所有，项目中的所有接口都会被扫描到
                 // none()不扫描
                 // withClassAnnotation()扫描类上的注解
                // withMethodAnnotation()扫描方法上的注解

                .apis(RequestHandlerSelectors.basePackage("com.wildwestworld.jkmusic.controller"))
                // paths()过滤某个路径
                .paths(PathSelectors.any())
                .build();
    }

    //自定义方法，用于
    //SecurityContext 来自于swagger springfox
    private SecurityContext securityContext(){
        //  //securityContexts(List<SecurityContext> securityContexts)
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        //AuthorizationScope(scope = “”, description = “”)
        //scope 要使用的 OAuth2 授权方案的范围
        //description	描述，随便写
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "assessEveryThing");
        //新建一个长度为1的AuthorizationScope类型的list
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        //将刚刚我们写的AuthorizationScope 范围放入到该list里面
        authorizationScopes[0] =authorizationScope;

        //SecurityReference(String reference, AuthorizationScope[] scopes)
        SecurityReference SecurityReference = new SecurityReference("Authorization", authorizationScopes);


//      上层方法需要   public SecurityContextBuilder securityReferences(List<SecurityReference> securityReferences)

        return Collections.singletonList(SecurityReference);

    }


    private ApiKey apiKey(){
        return new ApiKey("Authorization","Authorization","header");
    }



    //自定义函数，用于上面的apiInfo的构建
    //该函数就是配置一些简单的接口文档的页面显示的基础信息
    private ApiInfo apiInfoBuild(){
        //ApiInfoBuilder 网页相关的配置的构造器
        //title 网页最上面的标题
        //description 标题下面的小子
        //contact 作者信息
        //version 版本
        //build 构造
        return new ApiInfoBuilder()
                .title("捷凯音乐")
                .description("捷凯音乐接口文档")
                .contact(new Contact("作者", "作者URL", "作者Email"))
                .version("1.0.0")
                .build();
    }
}