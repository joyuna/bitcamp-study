package com.bitcamp.board.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

@WebServlet("/member/detail")
public class MemberDetailController extends HttpServlet {

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
      int no = Integer.parseInt(request.getParameter("no"));
      Member member = memberDao.findByNo(no);

      if (member == null) { 
        throw new Exception("해당 번호의 회원이 없습니다.");
      }

      request.setAttribute("member",member);

      response.setContentType("text/html;charset=UTF-8"); //  JSP가 출력할 콘텐트의 MiMe 타입 설
      RequestDispatcher 요청배달자 = request.getRequestDispatcher("/member/detail.jsp");
      요청배달자.include(request, response); // JSP 실행후 리턴된다.

    } catch (Exception e) {
      RequestDispatcher 요청배달자 = request.getRequestDispatcher("/error.jsp");

      request.setAttribute("exception", e);

      요청배달자.forward(request, response); // JSP를 실행한 후 리턴된다.
    } 
  }
}
