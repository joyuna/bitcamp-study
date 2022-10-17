package com.bitcamp.board.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// Web에 관련된 component만 만들어라.

@ComponentScan(value="com.bitcamp.board.controller.admin",
excludeFilters = @Filter(
    type = FilterType.REGEX,
    pattern = "com.bitcamp.board.controller.admin") // admin 패키지에 있는 것만 생성
    )
public class AdminWebConfig {

  public AdminWebConfig() {
    System.out.println("AdminWebConfig() 생성자 호출됨!");
  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class); // 주어진 URL을 처리할 객체 => JSP를 실행시켜주는 객체
    viewResolver.setPrefix("/WEB-INF/jsp/"); // WEB_INF로 이동했으니 경로 설정해준다.
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }
}
