package com.bitcamp.board.config;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.bitcamp.board.filter.AdminCheckFilter;
import com.bitcamp.board.filter.LoginCheckFilter;


public class AdminWebApplicationInitializer 
/* extends AbstractAnnotationConfigDispatcherServletInitializer */ {

  // @Override -> 일반 클래스로 바꿔버리기
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  // @Override
  protected String getServletName() {
    return "admin";
  }

  // @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {AdminWebConfig.class}; // 프론트 컨트롤러가 사용할 IoC 설정
  }

  // @Override
  protected String[] getServletMappings() {
    return new String[] {"/admin/*"}; // URL이 하나여도 배열로 리턴해야한다.
  }

  // @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {
        new CharacterEncodingFilter("UTF-8"),
        new LoginCheckFilter(),
        new AdminCheckFilter()
    };
  }
}
