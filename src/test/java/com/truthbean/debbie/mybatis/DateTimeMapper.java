package com.truthbean.debbie.mybatis;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DateTimeMapper {

  @Select("SELECT current_timestamp")
  LocalDateTime now();

}