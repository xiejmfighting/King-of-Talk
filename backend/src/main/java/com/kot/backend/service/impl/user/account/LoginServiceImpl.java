package com.kot.backend.service.impl.user.account;

import com.kot.backend.pojo.User;
import com.kot.backend.service.impl.utils.UserDetailsImpl;
import com.kot.backend.service.user.account.LoginService;
import com.kot.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        //将用户名和密码封装成一个密文的类
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(username,password);

        //验证其是否登录,登录失败会自动处理报异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //登录成功后将用户取出来
        UserDetailsImpl loginUser=(UserDetailsImpl) authenticate.getPrincipal();

        User user=loginUser.getUser();
        String jwt= JwtUtil.createJWT(user.getId().toString());

        Map<String,String> map=new HashMap<>();

        map.put("error_message","success");
        map.put("token",jwt);


        return map;
    }
}
