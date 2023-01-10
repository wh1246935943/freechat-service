package com.doudou.freechat.controllers;
 
import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.util.DDUtil;
import com.doudou.freechat.vo.UserVo;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    DDUtil ddUtil;

    @GetMapping("/{id}")
    public CommonResult getUserInfo(@PathVariable long id) {
        UserDao userDao = userService.getUserInfoById(id);
        if (userDao == null) {
            return CommonResult.failed("查询的用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);
        return CommonResult.success(userVo);
    }

    @GetMapping("/info")
    public CommonResult getMyUserInfo(HttpServletRequest request) {
        String userName = ddUtil.getUserNameByToken(request);
        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao == null) {
            return CommonResult.validateFailed();
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);
        return CommonResult.success(userVo);
    }

    @PostMapping("/delete")
    public CommonResult deleteUser(HttpServletRequest request) {
        String userName = ddUtil.getUserNameByToken(request);
        int tag = userService.deleteUser(userName);
        if (tag == 1) {
            ddUtil.delRedisValue(userName);
            return CommonResult.success(null, "账号删除成功");
        }
        return CommonResult.failed("操作失败");
    }
}
