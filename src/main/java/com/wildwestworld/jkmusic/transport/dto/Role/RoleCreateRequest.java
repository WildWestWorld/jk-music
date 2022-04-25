package com.wildwestworld.jkmusic.transport.dto.Role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleCreateRequest {
    @NotBlank(message = "角色英文名不能为空")

    private String name;

    @NotBlank(message = "角色中文名不能为空")

    private String title;

    private List<String> userIdList;

}
