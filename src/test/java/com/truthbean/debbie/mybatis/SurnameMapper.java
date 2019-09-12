package com.truthbean.debbie.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SurnameMapper {

    Surname selectOne(@Param("id") Long id);

    List<Surname> selectAll();

    int update(Surname surname);

    long insert(Surname surname);
}
