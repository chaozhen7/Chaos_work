package com.chaos.dao;

import com.chaos.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    List<User> selectByName(@Param("userName")String name);

    boolean deleteById(Integer id);
}