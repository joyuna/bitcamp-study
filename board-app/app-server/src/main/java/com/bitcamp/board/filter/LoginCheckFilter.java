package com.bitcamp.board.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("*")
public class LoginCheckFilter implements Filter{

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    // 요청 URL을 통해 로그인 여부를 검사할 지 결정한다.
    // 요청 URL은 HTTP 프로토콜과 관련된 값이다.
    // ServletRequest 타입은 HTTP 프로토콜과 관련된 기능을 다룰 수 있는 메서드가 없다.
    // ServletRequest 타입의 객체를 HttpServletRequest 객체이기 때문에 형변환 할 수 있다.
    // 즉 HTTP 프로토콜과 관련된 기능을 쓰고 싶다면,
    // 원래 타입으로 형변환 한 다음에 사용하라!
    HttpServletRequest httpRequest = (HttpServletRequest) request;

    // 요청 URL에서 서블릿 경로만 추출한다.
    // 예) 요청 URL   : http://localhost:8888/app/board/add?title=aaa&content=bbb
    //    서블릿 경로 : /board/add <= 웹 애플리케이션
    String servletPath = httpRequest.getServletPath();
    System.out.println(servletPath);

    //   Member loginMember = (Member) httpRequest.getSession().getAttribute("loginMember");
  }

}
