package com.wildwestworld.jkmusic.transport.dto.Role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleUpdateRequest {
    @NotBlank(message = "更改的角色英文名不能为空")

    private String name;

    @NotBlank(message = "更改的角色中文名不能为空")

    private String title;

    private List<String> userIdList;

}
