package com.bitcamp.sql;

import java.sql.DriverManager;

// 스레드 전용 DB 커넥션을 제공하는 일을 한다.
//
public class DataSource {

  String jdbcUrl;
  String username;
  String password;

  public DataSource(String driver, String jdbcUrl, String username, String password) throws Exception{
    // JDBC Driver 클래스 로딩하기
    Class.forName(driver);

    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws Exception {
    return DriverManager.getConnection(jdbcUrl, username, password);

  }
}