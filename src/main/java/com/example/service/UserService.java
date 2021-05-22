package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

import java.util.List;


public interface UserService extends IService<User> {

    List<User> selectAllByName(String name);

    IPage<User> selectPageByPage(Page<?> page,Integer age);

}
