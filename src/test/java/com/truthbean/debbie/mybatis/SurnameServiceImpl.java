package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.bean.BeanComponent;
import com.truthbean.debbie.bean.BeanInject;
import com.truthbean.debbie.jdbc.annotation.JdbcTransactional;
import com.truthbean.debbie.jdbc.datasource.DataSourceFactory;
import com.truthbean.debbie.jdbc.transaction.TransactionInfo;
import com.truthbean.debbie.jdbc.transaction.TransactionService;
import com.truthbean.debbie.mybatis.transaction.MybatisTransactionInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

@BeanComponent("surnameService")
@JdbcTransactional
public class SurnameServiceImpl implements SurnameService, TransactionService {

    @BeanInject
    private SqlSessionFactory sqlSessionFactory;

    @BeanInject
    private SurnameMapper surnameMapper;

    @BeanInject
    private SurnameRepository surnameRepository;

    @JdbcTransactional(rollbackFor = ArithmeticException.class, forceCommit = false, readonly = false)
    public boolean insert(Surname surname) {
        TransactionInfo transaction = getTransaction();
        /*SurnameMapper surnameMapper = this.surnameMapper;
        if (transaction instanceof MybatisTransactionInfo) {
            MybatisTransactionInfo transactionInfo = (MybatisTransactionInfo) transaction;
            SqlSession sqlSession = transactionInfo.getSession();
            surnameMapper = sqlSession.getMapper(SurnameMapper.class);
        }*/
        long id = surnameMapper.insert(surname);
        var all = surnameMapper.selectAll();
        System.out.println(all);
        surname.setId(id);
        return id > 0L;
    }

    @JdbcTransactional(rollbackFor = ArithmeticException.class, forceCommit = false, readonly = false)
    public boolean save(Surname surname) {
        /*SqlSession sqlSession = getSqlSession();
        var surnameMapper = sqlSession.getMapper(SurnameMapper.class);*/
        var all = surnameMapper.selectAll();
        System.out.println(all);
        int id = surnameMapper.update(surname);
        System.out.println(surname.getId() / 0L);
        return id > 0;
    }

    public Optional<Surname> selectById(Long id) {
        /*SqlSession sqlSession = getSqlSession();
        var surnameMapper = sqlSession.getMapper(SurnameMapper.class);*/
        Surname surname = surnameMapper.selectOne(id);
        if (surname == null)
            return Optional.empty();
        else
            return Optional.of(surname);
    }

    @Override
    public List<Surname> selectAll() {
        surnameRepository.findAll();
        // SqlSession sqlSession = sqlSessionFactory.openSession(getConnection());
        // var surnameMapper = sqlSession.getMapper(SurnameMapper.class);
        return surnameMapper.selectAll();
    }

    @Override
    public void doNothing() {
        System.out.println("none ...");
    }
}
