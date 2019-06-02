package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.core.bean.BeanComponent;
import com.truthbean.debbie.core.bean.BeanInject;
import com.truthbean.debbie.mybatis.transaction.MybatisTransactional;

import java.util.List;
import java.util.Optional;

@BeanComponent("surnameService")
@MybatisTransactional
public class SurnameServiceImpl implements SurnameService {

    @BeanInject
    private SurnameMapper surnameMapper;

    @MybatisTransactional(rollbackFor = ArithmeticException.class, forceCommit = false, readonly = false)
    public boolean save(Surname surname) {
        var all = surnameMapper.selectAll();
        System.out.println(all);
        int id = surnameMapper.update(surname);
        System.out.println(surname.getId() / 0L);
        return id > 0;
    }

    public Optional<Surname> selectById(Long id) {
        Surname surname = surnameMapper.selectOne(id);
        if (surname == null)
            return Optional.empty();
        else
            return Optional.of(surname);
    }

    @Override
    public List<Surname> selectAll() {
        return surnameMapper.selectAll();
    }

    @Override
    public void doNothing() {
        System.out.println("none ...");
    }
}
