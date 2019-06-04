package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.core.bean.BeanComponent;
import com.truthbean.debbie.core.bean.BeanInject;
import com.truthbean.debbie.jdbc.annotation.JdbcTransactional;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

@BeanComponent("surnameService")
@JdbcTransactional
public class SurnameServiceImpl implements SurnameService, SqlSessionService {

    @JdbcTransactional(rollbackFor = ArithmeticException.class, forceCommit = false, readonly = false)
    public boolean save(Surname surname) {
        SqlSession sqlSession = getSqlSession();
        var surnameMapper = sqlSession.getMapper(SurnameMapper.class);
        var all = surnameMapper.selectAll();
        System.out.println(all);
        int id = surnameMapper.update(surname);
        System.out.println(surname.getId() / 0L);
        return id > 0;
    }

    public Optional<Surname> selectById(Long id) {
        SqlSession sqlSession = getSqlSession();
        var surnameMapper = sqlSession.getMapper(SurnameMapper.class);
        Surname surname = surnameMapper.selectOne(id);
        if (surname == null)
            return Optional.empty();
        else
            return Optional.of(surname);
    }

    @Override
    public List<Surname> selectAll() {
        SqlSession sqlSession = getSqlSession();
        var surnameMapper = sqlSession.getMapper(SurnameMapper.class);
        return surnameMapper.selectAll();
    }

    @Override
    public void doNothing() {
        System.out.println("none ...");
    }
}
