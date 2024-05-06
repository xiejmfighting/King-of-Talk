package com.kot.backend.controller.user.userlist;

import com.alibaba.fastjson2.JSONObject;
import com.kot.backend.service.user.userlist.GetUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetUserListController {
    @Autowired
    private GetUserListService getUserListService;

    @GetMapping("/user/list/")
    JSONObject getuserlist(@RequestParam Map<String,String> data){
        Integer page=Integer.parseInt(data.get("page"));
        return getUserListService.getuserlist(page);
    }
}
