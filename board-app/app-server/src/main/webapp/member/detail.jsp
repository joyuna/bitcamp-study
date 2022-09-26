<%@page import="com.bitcamp.board.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bitcamp</title>
</head>
<body>
<h1>회원 상세 정보 -JSP self</h1>
<%
try {
  int no = Integer.parseInt(request.getParameter("no"));
  Member member = memberDao.findByNo(no);

  if (member == null) {
    %>
<p>해당 번호의 회원이 없습니다.</p>
<%
} else {
  %>
<form action='update'>
<table border='1'>
  <tr>
<th>번호</th><td><input name='no' type='number' value='<%=member.no%>' readonly></td>
  </tr>
  <tr>
<th>이름</th><td><input name='name' type='text' value='<%=member.name%>' size='60'></td>
  </tr>
  <tr>
<th>이메일</th><td><input name='email' type='email' value='%s' size='60'></td>", member.email);
  </tr>
  <tr>
    <th>암호</th><td><input name='password' type='password' size='10'></td>
  </tr>
  <tr>
<th>등록일</th><td><%=member.createdDate%></td>
  </tr>
</table>
<p>
  <button type='submit'>변경</button>
out.printf("  <a href='delete?no=<%=member.no%>'>삭제</a>
</p>
</form>
<%
  }
} catch (Exception e) {
  %>
<p>실행 중 오류 발생!</p>
<%
}
%>

</body>
</html>
<%!
MemberDao memberDao;


public void jspInit() {
  memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");

}
 %>