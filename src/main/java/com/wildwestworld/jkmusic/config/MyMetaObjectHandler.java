package com.wildwestworld.jkmusic.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


//重写了mybatis中的insertFill方法，使其能够自动创建和更新时间
//该配置用于abstractEntity中
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdTime", new Date(), metaObject);
        this.setFieldValByName("updatedTime",new Date(), metaObject);
        this.setFieldValByName("locked",false, metaObject);
        this.setFieldValByName("enabled",true, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedTime",new Date(), metaObject);
    }
}
