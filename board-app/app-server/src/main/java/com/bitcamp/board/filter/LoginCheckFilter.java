package com.bitcamp.board.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoginCheckFilter implements Filter{

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    // 필터의 파라미터로 넘어오는 객체는 원래 HttpServletRequest 객체이다.
    // 세션처럼 HTTP 프로토콜과 관련된 기능을 쓰고 싶다면,
    // 원래 타입으로 형변환 한 다음에 사용하라!
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    request.getSession()

  }

}
