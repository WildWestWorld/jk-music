package com.wildwestworld.jkmusic.config;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qcloud.cos.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.DataUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


//重写了mybatis中的insertFill方法，使其能够自动创建和更新时间
//该配置用于abstractEntity中
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        DateTime date = DateUtil.date();
        DateTime offsetDate = DateUtil.offsetHour(date, 8);

        this.setFieldValByName("createdTime", offsetDate, metaObject);
        this.setFieldValByName("updatedTime",offsetDate, metaObject);
        this.setFieldValByName("locked",false, metaObject);
        this.setFieldValByName("enabled",true, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        DateTime date = DateUtil.date();
        DateTime offsetDate = DateUtil.offsetHour(date, 8);
        this.setFieldValByName("updatedTime",offsetDate, metaObject);
    }
}
