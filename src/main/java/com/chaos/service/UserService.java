package com.chaos.service;

import com.chaos.model.User;
import com.github.pagehelper.PageInfo;

/**
 * Created by sina on 16/8/1.
 */
public interface UserService {

    public User getUserById(Integer id);

    public int insertUser(User user);

    public PageInfo<User> queryByPage(String userName, Integer pageNo, Integer pageSize);

    public boolean deleteById(Integer id);
}
