package com.bitcamp.board.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Transactional 애노테이션으로 트랜젝션을 제어할 수 있게 한다.
@EnableTransactionManagement

//JDBC 프로퍼티 값이 저장되어 있는 .properties 파일을 로딩한다.
@PropertySource("classpath:com/bitcamp/board/config/jdbc.properties")
// DatabaseConfig와 DataSource만 설정
public class DatabaseConfig {

  public DatabaseConfig() {
    System.out.println("DatabaseConfig() 생성자 호출됨!");
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource ds) {
    System.out.println("PlatformTransactionManager 객체 생성!");
    return new DataSourceTransactionManager(ds);
  }

  // ${jdbc.url} ==> .properties 파일에서 jdbc.url 이름으로 저장된 값을 가져온다.(프라퍼티 값을 가져옴)
  // @Value("...") ==> 애노테이션에 지정된 문자열을 파라미터 변수에 전달한다.(프라퍼티 값을 전달함)
  @Bean
  public DataSource dataSource(
      @Value("${jdbc.driverClassName}") String driverClassName,
      @Value("${jdbc.url}") String url,
      @Value("${jdbc.username}") String username,
      @Value("${jdbc.password}") String password) {

    System.out.println("DataSource 객체 생성!");
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driverClassName); // 드라이버 클래스 이름은 따로 안줘도 되는데 이번엔 줘볼게
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }
}
