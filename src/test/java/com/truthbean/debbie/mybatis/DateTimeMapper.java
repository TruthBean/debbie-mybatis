package com.truthbean.debbie.mybatis;

import java.time.LocalDateTime;

import com.truthbean.debbie.bean.BeanComponent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@BeanComponent
@Mapper
public interface DateTimeMapper {

  @Select("SELECT current_timestamp")
  LocalDateTime now();

}