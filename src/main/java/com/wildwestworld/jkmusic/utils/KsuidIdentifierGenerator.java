package com.wildwestworld.jkmusic.utils;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.ksuid.KsuidGenerator;

//重写了mybatis的id填充方法，我们就自定义了一个id生成器
//其实也可以不重写，ksuid其实就是uuid的近亲，没什么太大区别
//这次就当体验一下吧，下次可以用雪花算法重写id
public class KsuidIdentifierGenerator implements IdentifierGenerator {

    //重写下面这个方法前，得重写该方法才行
    @Override
    public Number nextId(Object entity) {
//        long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        return IdUtil.getSnowflake(0,0).nextId();
    }

    @Override
    public String nextUUID(Object entity) {
        return KsuidGenerator.generate();
    }
}
