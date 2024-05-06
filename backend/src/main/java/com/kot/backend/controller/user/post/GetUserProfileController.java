package com.kot.backend.controller.user.post;

import com.alibaba.fastjson2.JSONObject;
import com.kot.backend.service.user.post.GetUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetUserProfileController {
    @Autowired
    private GetUserProfileService getUserProfileService;

    @GetMapping("/userprofile/")
    JSONObject getuserprofile(@RequestParam Map<String,String> data){
        Integer userId=Integer.parseInt(data.get("userId"));
        return getUserProfileService.getuserprofile(userId);
    }
}
