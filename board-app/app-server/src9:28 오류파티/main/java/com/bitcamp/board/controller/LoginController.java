package com.bitcamp.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

@WebServlet("/auth/login")
public class LoginController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  MemberDao memberDao;

  @Override
  public void init() {
    memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      Member member = memberDao.findByEmailPassword(email, password);

      if (member != null) {
        HttpSession session = request.getSession(); // 요청한 클라이언트의 전용 HttpSession 보관소를 얻는다.
        session.setAttribute("loginMember", member); // 로그인한 멤버 정보를 세션 보관소에 저
      }

      // 클라이언트에게 쿠키 보내기
      // 쿠키는 문자열이다. 객체 안됨, 숫자도 문자열로 바꿔서 저장해야
      Cookie cookie = new Cookie("email", email); // 클라이언트 쪽에 저장할 쿠키 생성

      if (request.getParameter("saveEmail") == null) {
        cookie.setMaxAge(0); // 클라이언트에게 해당 이름의 쿠키를 지울 것을 명령한다.-2
      } else {
        // 쿠키의 지속시간을 설정하지 않으면 웹브라우저가 실행되는 동안만 유효하다.-> 웹브라우저가 꺼지면 날아감
        // 만약 웹브라우저를 종료하더라도 쿠키를 유지하고 싶다면,
        // 지속 시간을 설정해야 한다.
        cookie.setMaxAge(60 * 60 * 24 * 7); // 유지시간을 7일로 저장
      }
      response.addCookie(cookie); // 응답헤더에 쿠키를 포함시킨다.-1

      request.setAttribute("member", member);

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/auth/loginResult.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response); 
    }
  }
}






