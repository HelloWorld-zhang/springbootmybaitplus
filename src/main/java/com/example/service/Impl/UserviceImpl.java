package com.example.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserviceImpl extends ServiceImpl<UserMapper, User> implements UserService  {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectAllByName(String name) {
        return userMapper.selectAllByName(name);
    }

    @Override
    public IPage<User> selectPageByPage(Page<?> page, Integer age) {
        return userMapper.selectPageByPage(page,age);
    }
}
