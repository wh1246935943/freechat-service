package com.doudou.freechat.controllers;

import com.doudou.freechat.common.api.CommonResult;
import com.doudou.freechat.dao.UserDao;
import com.doudou.freechat.dto.UserRegisterParamDto;
import com.doudou.freechat.service.VerCodeCacheService;
import com.doudou.freechat.service.UserService;
import com.doudou.freechat.util.DDUtil;
import com.doudou.freechat.vo.LoginVo;
import com.doudou.freechat.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.token}")
    private String token;

    @Resource
    UserService userService;

    @Resource
    DDUtil ddUtil;

    @Resource
    VerCodeCacheService verCodeCacheService;

    /**
     * 用户登录，
     * 登录成功设置token
     * 并将用户名存入redis
     */
    @PostMapping("/login")
    public CommonResult login(
            @RequestBody Map<String, String> user,
            HttpServletResponse response
    ) {
        String userName = user.get("userName");
        String password = user.get("password");

        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao != null && password.equals(userDao.getPassword())) {
            String jwt = ddUtil.getToken(userDao.getUserName());

            Cookie cookie = new Cookie(token, jwt);
            cookie.setMaxAge(expiration);
            cookie.setPath("/");
            response.addCookie(cookie);

            LoginVo loginVo = new LoginVo();
            loginVo.setToken(jwt);
            loginVo.setId(userDao.getId());

            ddUtil.setRedisValue(userName, userDao.getId() + "");
            return CommonResult.success(loginVo, "登录成功");
        }
        return CommonResult.validateFailed("用户名或密码错误");
    }

    @PostMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        String userName = ddUtil.getUserNameByToken(request);
        ddUtil.delRedisValue(userName);
        return CommonResult.success(null, "退出成功");
    }

    @GetMapping("/verCode")
    public CommonResult getVerCode(@RequestParam(name = "email", required = true) String email) {
        // 校验邮箱是否合法
        if (!ddUtil.isEmail(email)) {
            return CommonResult.failed("邮箱格式不正确");
        }

        // 检查邮箱是否已经绑定
        List<UserDao> users = userService.getUserByEmail(email);
        if (users.size() > 0) {
            return CommonResult.failed("该邮箱账号已注册");
        }

        // 从缓存中获取验证码，如果有则已经发送过，将该验证码重新发送给用户邮箱
        String codeValue = verCodeCacheService.get(email);
        // 检查是否发送过验证码
        if (codeValue == null) {
            codeValue = ddUtil.generateRandom(6);
        }

        // 发送验证码
        boolean isSend = ddUtil.sendEmail(email, "免聊注册验证码", codeValue);
        if (isSend) {
            // 将已经发送的验证码放入缓存
            verCodeCacheService.save(email, codeValue);
            return CommonResult.success(null, "验证码已发送到: " + email);
        } else {
            return CommonResult.failed("验证码发送失败，请重试");
        }
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody UserRegisterParamDto userRegisterParam) {
        // 先检查用户名是否存在
        String userName = userRegisterParam.getUserName();
        UserDao userDao = userService.getUserInfoByName(userName);
        if (userDao != null) {
            return CommonResult.failed("用户名已存在");
        }

        // 检查验证码是否正确
        String verCode = userRegisterParam.getVerCode();
        String email = userRegisterParam.getEmail();
        String cacheVerCode = verCodeCacheService.get(email);
        // 如果用户提交的邮箱账号查不到验证码信息则提示先获取验证码
        if (cacheVerCode == null) {
            return CommonResult.failed("请先获取验证码");
        }
        if (!cacheVerCode.equals(verCode)) {
            return CommonResult.failed("验证码输入错误");
        }

        // 添加用户 这里不做判断是因为前面的操作已经覆盖了失败的场景
        userDao = userService.addUser(userRegisterParam);

        // 返回用户注册信息
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDao, userVo);
        verCodeCacheService.delete(email);
        return CommonResult.success(userVo, "账号注册成功");
    }
}
