package com.bitcamp.board.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Member;

@WebServlet("/member/list")
public class MemberListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  MemberDao memberDao;


  @Override
  public void init() {
    memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try  {
      List<Member> members = memberDao.findAll();

      // JSP가 사용할 수 있도록 ServletRequest 보관소에 저장한다.
      req.setAttribute("members",members);

      // JSP에게 UI 생성을 위임한다.
      resp.setContentType("text/html;charset=UTF-8"); //  JSP가 출력할 콘텐트의 MiMe 타입 설
      RequestDispatcher 요청배달자 = req.getRequestDispatcher("/member/list.jsp");
      요청배달자.include(req, resp); // JSP 실행후 리턴된다.

    } catch (Exception e) {
      // 예외가 발생하면 예외를 처리하는 JSP에게 UI 생성을 위임한다.
      RequestDispatcher 요청배달자 = req.getRequestDispatcher("/error.jsp");

      // JSP를 실행하기 전에 ServletRequest 보관소에 오류 정보를 담는다.
      req.setAttribute("exception", e);

      // forward():
      // - 예외가 발생하면 기존의 출력 내용을 모두 버린다.
      // -JSP에게 처음부터 새로 출력하게 전권을 위임한다.
      요청배달자.forward(req, resp); // JSP를 실행한 후 리턴된다.
    } 
  }
}
