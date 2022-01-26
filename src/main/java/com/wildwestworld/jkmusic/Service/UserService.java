package com.wildwestworld.jkmusic.Service;

import com.wildwestworld.jkmusic.Entity.User;
import com.wildwestworld.jkmusic.transport.dto.UserDto;
import com.wildwestworld.jkmusic.transport.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;


//专门写方法的地方

public interface UserService {

    List<UserDto> list();


}
