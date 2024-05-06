package com.kot.backend.service.postlist;

import com.alibaba.fastjson2.JSONObject;

public interface GetPostListService {
    JSONObject getlist(Integer page);
}
