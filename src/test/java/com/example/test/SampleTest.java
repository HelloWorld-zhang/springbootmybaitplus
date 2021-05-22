package com.example.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class SampleTest {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    //查询所有数据
    @Test
    public void selectList(){
        //不根据任何条件进行查询所有的数据
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
    //添加数据
    @Test
    public void createUser(){
        User user = new User();
        user.setName("张飞");
        user.setAge(18);
        int insert = userMapper.insert(user);
        System.out.println("影响到行数:" + insert);
        System.out.println("id:" + user.getId());
    }

    //根据不同的条件进行查询
    @Test
    public void  testSelect(){
        //根据id进行查询出结果
        User id = userMapper.selectById(1);
        System.out.println(id);
        System.out.println("-----------根据id进行数据的查询-----------");

        //根据id列表进行查询
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
        System.out.println("----------根据id列表进行查询------------");
        //根据条件进行查询
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","Jone");
        map.put("age",18);
        List<User> users1 = userMapper.selectByMap(map);
        users1.forEach(System.out::println);
        System.out.println("-------根据name 和 age 进行数据查询--------");
    }

    //修改数据
    @Test
    public void update(){
        User user = new User();
        user.setId(1L);
        user.setAge(28);
        int i = userMapper.updateById(user);
        System.out.println("影响的行数："+ i);
    }

    @Test
    public void delete(){
        int i = userMapper.deleteById("1395570306390736897");
        System.out.println("受影响的行数:"+ i);
    }
    @Test
    public void testCount(){
        int count = userService.count();
        System.out.println("总记录数："+ count);
    }

    @Test
    public void testSaveBatch(){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("helen" + i);
            user.setAge(10 + i);
            users.add(user);
        }
        userService.saveBatch(users);
    }

    @Test
    public void testSelectAllByName(){
        List<User> users = userService.selectAllByName("Jone");
        users.forEach(System.out::println);
    }
    @Test
    public void testPage(){
        Page<User> userPage = new Page<>(1, 3);
        Page<User> page = userMapper.selectPage(userPage, null);
        System.out.println(page);
    }

    @Test
    public void testSelectPageVo(){
        Page<User> userPage = new Page<>(1, 3);
        userMapper.selectPageByPage(userPage,18);
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }

}
