package com.yl.myoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yl.myoj.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 用户数据库操作
 */


public interface UserMapper extends BaseMapper<User> {

}




