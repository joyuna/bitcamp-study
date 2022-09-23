<%@page import="com.bitcamp.board.dao.BoardDao"%>
<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--     
out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='1; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 입력</h1>");

    try {
      Board board = new Board();
      board.title = req.getParameter("title");
      board.content = req.getParameter("content");
      board.memberNo = Integer.parseInt(req.getParameter("writerNo"));

      if (boardDao.insert(board) == 0) {
        out.println("<p>게시글을 등록할 수 없습니다!</p>");

      } else {
        out.println("<p>게시글을 등록했습니다.</p>");
      }
    } catch (Exception e) {
      out.println("<p>실행 중 오류 발생!</p>");
    }

    out.println("</body>");
    out.println("</html>");
--%>
<%!
BoardDao boardDao;

public void jspInit() {
  System.out.println("테스트!!");
  boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
}
%>