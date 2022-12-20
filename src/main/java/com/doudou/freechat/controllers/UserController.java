package com.doudou.freechat.controllers;
 
import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public CommonResult getUserInfo(@PathVariable long id) {

        UserVo userVo = userService.getUserInfoById(id);

        if (userVo == null) {
            return CommonResult.failed("");
        }

        return CommonResult.success(userVo);
    }
}
