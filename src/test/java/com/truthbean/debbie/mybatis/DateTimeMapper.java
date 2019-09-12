package com.truthbean.debbie.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface DateTimeMapper {

  @Select("SELECT current_timestamp")
  LocalDateTime now();

}