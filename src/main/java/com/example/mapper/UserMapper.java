package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectAllByName(String name);

    IPage<User> selectPageByPage(Page<?> page,Integer age);
}
