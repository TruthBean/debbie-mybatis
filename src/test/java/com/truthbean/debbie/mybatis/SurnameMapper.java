package com.truthbean.debbie.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SurnameMapper {

    Surname selectOne(@Param("id") Long id);
}
