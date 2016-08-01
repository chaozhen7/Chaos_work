package com.chaos.service.Impl;

import com.chaos.dao.UserMapper;
import com.chaos.model.User;
import com.chaos.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sina on 16/8/1.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userDao;

    @Override
    public User getUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public PageInfo<User> queryByPage(String userName, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null?1:pageNo;
        pageSize = pageSize == null?10:pageSize;
        PageHelper.startPage(pageNo, pageSize);
        List<User> list = userDao.selectByName(userName);
        //用PageInfo对结果进行包装
        PageInfo<User> page = new PageInfo<User>(list);
        return page;
    }

    @Override
    public boolean deleteById(Integer id) {
        return userDao.deleteById(id);
    }
}
