package com.bitcamp.board.conifg;

import java.io.InputStream;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

// DatabaseConfig와 DataSource만 설정
public class MybatisConfig {

  public MybatisConfig() {
    System.out.println("MybatisConfig() 생성자 호출됨!");
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource ds) {
    System.out.println("PlatformTransactionManager 객체 생성!");
    String resource = "com/bitcamp/board/config/mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory =
        new SqlSessionFactoryBuilder().build(inputStream);

    return new DataSourceTransactionManager(ds);
  }
}

