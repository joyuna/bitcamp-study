package com.bitcamp.board.conifg;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// 스프링 IoC
// 1) DB 커넥션 객체 관리자 준비 : DataSource
// 2) 트랜잭션 관리자 준비 : PlatformTransactionManager
// 3) 어떤 패키지에 있는 객체를 자동으로 생성할 패키지를 지정한다.
//
@ComponentScan(value="com.bitcamp.board")
// -com.bitcamp.board 패키지 및 그 하위 패키지에 소속된 클래스 중에서
// @Component, @Controller, @Service, @Repository등의 애노테이션이 붙은 클래스를 찾아
// 객체를 생성한다. => ComponentScan 애노테이션의 의미
public class AppConfig {

  public AppConfig() {
    System.out.println("AppConfig() 생성자 호출됨!");
  }

  // @ Bean 애노테이션을 붙일 때 객체 이름을 지정하면
  // 그 이름으로 리턴 값을 컨테이너에 보관한다.
  // 이름을 지정하지 않으면 메서드 이름으로 보관한다.
  // @Bean("transactionManager")
  @Bean
  public PlatformTransactionManager transactionManager(DataSource ds) {
    return new DataSourceTransactionManager(ds);
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.mariadb.jdbc.Driver"); // 드라이버 클래스 이름은 따로 안줘도 되는데 이번엔 줘볼게
    ds.setUrl("jdbc:mariadb://localhost:3306/studydb");
    ds.setUsername("study");
    ds.setPassword("1111");
    return ds;
  }

  // multipart/form-data 형식으로 보내온 요청 데이터를 
  // 도메인 객체로 받는 일을 할 도우미 객체를 등록한다.
  // 이 객체가 등록된 경우 multipart/form-data를 도메인 객체로 받을 수 있다.
  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  // Spring WebMVC의 기본 ViewResolver를 교체한다.
  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class); // 주어진 URL을 처리할 객체 => JSP를 실행시켜주는 객체
    viewResolver.setPrefix("/WEB-INF/jsp/"); // WEB_INF로 이동했으니 경로 설정해준다.
    viewResolver.setSuffix(".jsp");
    return viewResolver;

  }
}
