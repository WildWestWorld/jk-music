package com.wildwestworld.jkmusic.transport.vo;

import lombok.Data;
//VO专门用于接口的返回值 也就是后端传给前端的数据
@Data
public class RoleVo {
    private String  id;

    private String name;

    private String title;
}
