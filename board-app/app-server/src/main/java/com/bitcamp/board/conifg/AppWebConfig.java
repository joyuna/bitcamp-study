package com.bitcamp.board.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// Web에 관련된 component만 만들어라.

@ComponentScan(value="com.bitcamp.board.controller",
excludeFilters = @Filter(
    type = FilterType.REGEX,
    pattern = "com.bitcamp.board.controller.admin.*") // 컨트롤러 다하지 말고 admin은 제외하고 해라.
    )
public class AppWebConfig {

  public AppWebConfig() {
    System.out.println("AppWebConfig() 생성자 호출됨!");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
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
