package com.truthbean.debbie.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.0.2
 * Created on 2020-03-23 17:09
 */
@Mapper
public interface SystemUserMapper {

    List<SystemUser> selectAll();
}
