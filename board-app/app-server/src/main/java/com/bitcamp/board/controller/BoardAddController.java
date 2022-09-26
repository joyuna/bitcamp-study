package com.bitcamp.board.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.Board;

@WebServlet("/board/add")
public class BoardAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  BoardDao boardDao;


  @Override
  public void init() {
    boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Board board = new Board();
      board.title = request.getParameter("title");
      board.content = request.getParameter("content");
      board.memberNo = Integer.parseInt(request.getParameter("writerNo"));

      if (boardDao.insert(board) == 0) {
        throw new Exception("게시글 등록 실패!");
      }

      // JSP에게 UI 생성을 위임한다.
      resp.setContentType("text/html;charset=UTF-8"); //  JSP가 출력할 콘텐트의 MiMe 타입 설
      RequestDispatcher 요청배달자 = req.getRequestDispatcher("/board/list.jsp");
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
