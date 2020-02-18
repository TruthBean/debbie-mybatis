package com.truthbean.debbie.mybatis.transaction;

import com.truthbean.debbie.util.Assert;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

public final class SqlSessionHolder {

  private final SqlSession sqlSession;

  private final ExecutorType executorType;

  /**
   * Creates a new holder instance.
   *
   * @param sqlSession
   *          the {@code SqlSession} has to be hold.
   * @param executorType
   *          the {@code ExecutorType} has to be hold.
   */
  public SqlSessionHolder(SqlSession sqlSession, ExecutorType executorType) {

    Assert.notNull(sqlSession, "SqlSession must not be null");
      Assert.notNull(executorType, "ExecutorType must not be null");

    this.sqlSession = sqlSession;
    this.executorType = executorType;
  }

  public SqlSession getSqlSession() {
    return sqlSession;
  }

  public ExecutorType getExecutorType() {
    return executorType;
  }
}
