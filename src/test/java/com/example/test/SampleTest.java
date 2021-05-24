package com.example.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

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
    //根据id删除数据
    @Test
    public void delete(){
        int i = userMapper.deleteById("1395570306390736897");
        System.out.println("受影响的行数:"+ i);
    }
    //统计出总数
    @Test
    public void testCount(){
        int count = userService.count();
        System.out.println("总记录数："+ count);
    }
    //批量添加数据
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
    //根据姓名查询
    @Test
    public void testSelectAllByName(){
        List<User> users = userService.selectAllByName("Jone");
        users.forEach(System.out::println);
    }
    //分页
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
    //查询名字中包含n 年龄大于等于10且小于20的 邮箱不能为空
    @Test
    public void test1(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","n")
                    .between("age",10,30)
                    .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    //按照年龄降序 如果年龄相等按照id升序
    @Test
    public void test2(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }
    //删除邮箱为空的数据
    @Test
    public void  test3(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.isNull("email");
        int delete = userMapper.delete(userQueryWrapper);
        System.out.println( delete);
    }
    //根据名字包含n的和年龄小于18岁并邮箱为空
    @Test
    public void test4(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name","n").and(i-> i.lt("age",18).or().isNull("email"));
        User user = new User();
        user.setAge(18);
        user.setEmail("user@qq.com");
        int update = userMapper.update(user, wrapper);
        System.out.println(update);
    }
    //根据名字和年龄查询
    @Test
    public void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("name","age");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }
    @Test
    public void test6(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.inSql("id","select * from user wher id <= 3 or true");
        queryWrapper.le("id",3);
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }
    @Test
    public void test7(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age",18).set("email","user@qq.com").like("name","n").and(i->i.lt("age",18).or().isNull("email"));
        User user = new User();
        int update = userMapper.update(user, updateWrapper);
        System.out.println(update);
    }

    @Test
    public void test8(){
        String name = null;
        Integer ageBegin = 10;
        Integer ageEnd = 20;

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name),"name","n").ge(ageBegin!=null,"age",ageBegin).le(ageEnd !=null,"age",ageEnd);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test9(){
        String name = null;
        Integer ageBegin = 10;
        Integer ageEnd = 20;
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name),User::getName,"n").ge(ageBegin !=null,User::getAge,ageBegin).le(ageEnd!=null,User::getAge,ageEnd);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test10(){
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(User::getAge,18)
                .set(User::getEmail,"user@zhang.com")
                .like(User::getName,"n")
                .and(i->i.lt(User::getAge,18).or().isNotNull(User::getEmail));
        User user = new User();
        int update = userMapper.update(user, updateWrapper);
        System.out.println(update);
    }
}
