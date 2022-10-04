package com.bitcamp.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bitcamp.board.dao.BoardDao;
import com.bitcamp.board.domain.AttachedFile;
import com.bitcamp.board.domain.Board;
import com.bitcamp.board.domain.Member;

@WebServlet("/board/fileDelete")
public class BoardFileDeleteController extends HttpServlet {
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
      int no = Integer.parseInt(request.getParameter("no"));

      // 첨부파일 정보를 가져온다.
      AttachedFile attachedFile = boardDao.findFileByNo(no);

      // 첨부파일의 게시글 정보를 가져온다.
      Board board = boardDao.findByNo(attachedFile.getBoardNo());

      // 게시글의 작성자가 로그인 사용자인지 검사한다.
      Member loginMember = (Member) request.getSession().getAttribute("loginMember");
      if (boardDao.findByNo(attachedFile.getBoardNo()).getWriter().getNo() != loginMember.getNo()) {
        throw new Exception("게시글 작성자가 아닙니다.");
      }

      // 첨부파일을 삭제한다.
      if (boardDao.deleteFile(no) == 0) {
        throw new Exception("게시글 첨부파일 삭제 실패!");
      }

      // Redirect:
      // - 클라이언트에게 콘텐트를 보내지 않는다.
      // - 응답 프로토콜
      //      HTTP/1.1 302   <=== 응답 상태 코드
      //      Location: list  <=== 자동으로 요청할 URL
      //      Content-Length: 0  <=== 콘텐트는 보내지 않는다.
      //      Date: Mon, 26 Sep 2022 05:21:22 GMT
      //      Keep-Alive: timeout=20
      //      Connection: keep-alive
      // 
      //      (콘텐트 없음!)
      //
      // 자바 코드:
      response.sendRedirect("list");

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}






