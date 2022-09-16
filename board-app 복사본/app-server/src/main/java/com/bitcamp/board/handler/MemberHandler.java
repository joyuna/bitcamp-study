/*
 * 회원 메뉴 처리 클래스
 */
package com.bitcamp.board.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import com.bitcamp.board.dao.MemberDao;
import com.bitcamp.board.domain.Board;
import com.bitcamp.board.domain.Member;
import com.bitcamp.handler.AbstractHandler;
import com.bitcamp.util.Prompt;

public class MemberHandler extends AbstractHandler {

  private MemberDao memberDao;

  public MemberHandler(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void list(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<style>");
    out.println("tr:hover {");
    out.println("  background-color: navy;");
    out.println("  color: white;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원</h1>");
    out.println("<a href='form'>새 글</a>");
    out.println("<table border='1'>");
    out.println("  <tr>");
    out.println("    <th>번호</th>");
    out.println("    <th>이름</th>");
    out.println("    <th>이메일</th>");
    out.println("    <th>등록일</th>");
    out.println("  </tr>");

    List<Member> members = memberDao.findAll();
    for (Member member : members) {
      out.println("<tr>");
      out.printf("  <td>%d</td>", member.no);
      out.printf("  <td><a href='detail?no=%d'>%s</a></td>", member.no, member.name);
      out.printf("  <td>%s</td>", member.email);
      out.printf("  <td>%s</td>", member.createdDate);
      out.println("</tr>");
    }

    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
  }

  public void detail(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 상세 정보</h1>");

    int memberNo = Integer.parseInt(paramMap.get("no"));
    Member member = memberDao.findByNo(memberNo);

    if (member == null) {
      out.println("<p>해당 번호의 회원이 없습니다.</p>");

    } else {
      out.println("<form action='update'>");
      out.println("<table border='1'>");
      out.println("  <tr>");
      out.printf("    <th>번호</th><td><input name='no' type='number' value='%d' readonly></td>", member.no);
      out.println("  </tr>");
      out.println("  <tr>");
      out.printf("    <th>이름</th><td><input name='name' type='text' value='%s' size='60'></td>", member.name);
      out.println("  </tr>");
      out.println("  <tr>");
      out.printf("    <th>이메일</th><td><textarea name='email' rows='10' cols='60'>%s</textarea></td>", member.email);
      out.println("  </tr>");
      out.println("  <tr>");
      out.printf("    <th>등록일</th><td>%s</td>", member.createdDate);
      out.println("  </tr>");
      out.println("</table>");
      out.println("<p>");
      out.println("  <button type='submit'>변경</button>");
      out.printf("  <a href='delete?no=%d'>삭제</a>", member.no);
      out.println("</p>");
      out.println("</form>");
    }

    out.println("</body>");
    out.println("</html>");
  }

  public void delete(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 삭제</h1>");

    int no = Integer.parseInt(paramMap.get("no"));

    if (memberDao.delete(no) == 0) {
      out.println("<p>해당 번호의 회원이 없습니다.</p>");

    } else {
      out.println("<p>해당 회원 정보를 삭제했습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");

  }

  public void update(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 정보 변경</h1>");

    Member member = new Member();
    member.no = Integer.parseInt(paramMap.get("no"));
    member.name = paramMap.get("name");
    member.email = paramMap.get("email");

    if (memberDao.update(member) == 0) {
      out.println("<p>해당 번호의 회원이 없습니다.</p>");

    } else {
      out.println("<p>해당 회원 정보를 변경했습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }

  public void form(Map<String, String> paramMap, PrintWriter out) {
    // TODO Auto-generated method stub

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 정보 입력</h1>");


    out.println("<form action='add'>");
    out.println("<table border='1'>");
    out.println("  <tr>");
    out.println("    <th>이름</th><td><input name='name' type='text' size='60'></td>");
    out.println("  </tr>");
    out.println("  <tr>");
    out.printf("    <th>이메일</th><td><textarea name='email' rows='10' cols='60'></textarea></td>");
    out.println("  </tr>");
    out.println("</table>");
    out.println("<p>");
    out.println("  <button type='submit'>등록</button>");
    out.println("  <a href='list'>목록</a>");
    out.println("</p>");
    out.println("</form>");


    out.println("</body>");
    out.println("</html>");
  }

  public void add(Map<String,String> paramMap, PrintWriter out) throws Exception {

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>bitcamp</title>");
    out.println("<meta http-equiv='Refresh' content='3; url=list'>"); // 같은 주소내면 그
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원정보 입력</h1>");

    Member member = new Member();
    member.name = paramMap.get("name");
    member.email = paramMap.get("email");


    if (memberDao.insert(member) == 0) {
      out.println("<p>게시글을 등록할 수 없습니다!</p>");

    } else {
      out.println("<p>게시글을 등록했습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");

  }


  /* try {
      switch (menuNo) {
        case 1: this.onList(in, out); break;
        case 2: this.onDetail(in, out); break;
        case 3: this.onInput(in, out); break;
        case 4: this.onDelete(in, out); break;
        case 5: this.onUpdate(in, out); break;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void onList(DataInputStream in, DataOutputStream out) throws Exception {
    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut)) {

      List<Member> members = memberDao.findAll();

      tempOut.println("번호\t이름\t이메일");

      for (Member member : members) {
        tempOut.printf("%d\t%s\t%s\n",
            member.no, member.name, member.email);
      }

      out.writeUTF(strOut.toString());
    }
  }

  private void onDetail(DataInputStream in, DataOutputStream out) throws Exception {
    Prompt prompt = new Prompt(in, out);

    int no = prompt.inputInt("조회할 회원 번호? ");

    Member member = memberDao.findByNo(no);

    if (member == null) {
      out.writeUTF("해당 번호의 회원이 없습니다!");
      return;
    }

    try (StringWriter strOut = new StringWriter();
        PrintWriter tempOut = new PrintWriter(strOut)) {

      tempOut.printf("이름: %s\n", member.name);
      tempOut.printf("이메일: %s\n", member.email);
      tempOut.printf("등록일: %tY-%1$tm-%1$td %1$tH:%1$tM\n", member.createdDate);
      out.writeUTF(strOut.toString());
    }
  }

  private void onInput(DataInputStream in, DataOutputStream out) throws Exception {
    Prompt prompt = new Prompt(in, out);
    Member member = new Member();

    member.name = prompt.inputString("이름? ");
    member.email = prompt.inputString("이메일? ");
    member.password = prompt.inputString("암호? ");

    memberDao.insert(member);
    out.writeUTF("회원을 등록했습니다.");
  }

  private void onDelete(DataInputStream in, DataOutputStream out) throws Exception {
    Prompt prompt = new Prompt(in, out);
    int no = prompt.inputInt("삭제할 회원 번호? ");

    if (memberDao.delete(no) == 1) {
      out.writeUTF("삭제하였습니다.");
    } else {
      out.writeUTF("해당 번호의 회원이 없습니다!");
    }
  }

  private void onUpdate(DataInputStream in, DataOutputStream out) throws Exception {
    Prompt prompt = new Prompt(in, out);
    int no = prompt.inputInt("변경할 회원 번호? ");

    Member member = memberDao.findByNo(no);

    if (member == null) {
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    member.name = prompt.inputString("이름?(" + member.name + ") ");
    member.email = prompt.inputString("이메일?(" + member.email + ") ");
    member.password = prompt.inputString("암호?");

    String input = prompt.inputString("변경하시겠습니까?(y/n) ");

    if (input.equals("y")) {
      if (memberDao.update(member) == 1) {
        out.writeUTF("변경했습니다.");
      } else {
        out.writeUTF("변경 실패입니다!");
      }

    } else {
      out.writeUTF("변경 취소했습니다.");
    }
  }
}




