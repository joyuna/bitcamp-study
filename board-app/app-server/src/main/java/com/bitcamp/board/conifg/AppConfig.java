package com.bitcamp.board.conifg;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

// 1) DB 커넥션 객체 관리자 준비 : DataSource
public class AppConfig {

  public DataSource createDataSource() {
    System.out.println("createDataSource() 호출됨!");

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.mariadb.jdbc.Driver"); // 드라이버 클래스 이름은 따로 안줘도 되는데 이번엔 줘볼게
    ds.setUrl("jdbc:mariadb://localhost:3306/studydb");
    ds.setUsername("study");
    ds.setPassword("1111");
    return ds;
  }

  public PlatformTransactionManager createTransactionManager(DataSource ds) {
    System.out.println("createTransactionManager() 호출됨!");

    return new DataSourceTransactionManager(ds);
  }
}
