package com.kot.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kot.backend.pojo.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
