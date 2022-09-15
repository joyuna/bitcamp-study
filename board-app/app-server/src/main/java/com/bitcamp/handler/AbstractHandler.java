package com.bitcamp.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.bitcamp.util.BreadCrumb;

// Handler 규격에 맞춰 서브 클래스에게 물려줄 공통 필드나 메서드를 구현한다.
// 
public abstract class AbstractHandler implements Handler {

  // 핸들러가 사용할 메뉴 목록을 담을 배열을 준비한다.
  // => 메뉴 목록은 생성자를 통해 초기화시킨다.
  //
  private String[] menus;

  // 반드시 메뉴 목록을 초기화시키도록 강제하기 위해 
  // 기본 생성자 대신 메뉴 목록을 배열로 받는 생성자를 준비한다.
  public AbstractHandler(String[] menus) {
    this.menus = menus;
  }

  // 다음 메서드는 execute()에서 메뉴를 출력할 때 사용된다.
  // 다만 서브 클래스서 출력 형식을 바꾸기 위해 오버라이딩 할 수 있도록 
  // 접근 범위를 protected로 설정한다.
  protected void printMenus(DataOutputStream out) throws Exception { //9-2)
    try (StringWriter strOut = new StringWriter();  // 7-6) // 1))
        PrintWriter tempOut = new PrintWriter(strOut)) {

      tempOut.println(BreadCrumb.getBreadCrumbOfCurrentThread().toString());

      for (int i = 0; i < menus.length; i++) {
        tempOut.printf("  %d: %s\n", i + 1, menus[i]);
      }

      tempOut.printf("메뉴를 선택하세요[1..%d](0: 이전) ", menus.length);// 10-2 ㅜ
      out.writeUTF(strOut.toString());
    }
  }

  protected static void printHeadline(PrintWriter out) { // 7-1) 프린트메뉴로 이름 병
    out.println("=========================================");
  }

  protected static void printBlankLine(PrintWriter out) {
    out.println(); // 메뉴를 처리한 후 빈 줄 출력
  }


  static void error(DataOutputStream out, Exception e) { // 7-1) 프린트메뉴로 이름 병
    try (StringWriter strOut = new StringWriter();  // 7-6) // 1))
        PrintWriter tempOut = new PrintWriter(strOut)) {
      tempOut.printf("실행 오류: %s\n", e.getMessage()); // system은 지워버리
      out.writeUTF(strOut.toString());
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

  // 11-1)처음 메인 페이지는 주되 나머지는 요청하면 주자.
  @Override
  public void execute(DataInputStream in, DataOutputStream out) throws Exception { // 9-1) 실행할때 입출력 스트림을 받을게요홍!

    printMenus(out);

    while (true) {
      //4-1))) 클라이언트가 보낸 요청을 읽는다.
      String request = in.readUTF();
      if (request.equals("0")) { // 6-1)
        break; 

      } else if (request.equals("menu")) {
        printMenus(out);
        continue;
      }

      try { 
        int menuNo = Integer.parseInt(request);
        if (menuNo < 1 || menuNo > menus.length) {
          throw new Exception("메뉴 번호가 옳지 않습니다."); 
        }
        // 10-4) 핸들러에 들어가기 전에 breadcrumb 메뉴에 하위 메뉴 이름을 추가한다.
        BreadCrumb.getBreadCrumbOfCurrentThread().put(memus[menuNo - 1]);

        // 사용자가 입력한 메뉴번호에
        service(menuNo, in, out);


      } catch (Exception e) {
        error(out, e); // while 문의 조건 검사로 보낸다.
      } finally {

        // 메뉴에서 나올 때 브레드크럼 메뉴바에서 그 메뉴를 제거한다.
        BreadCrumb.getBreadCrumbOfCurrentThread().pickUp();
      }
    } // while
  }

  // 서브 클래스가 반드시 만들어야 할 메서드
  // => 메뉴 번호를 받으면 그 메뉴에 해당하는 작업을 수행한다.
  // => 서브 클래스에게 구현을 강제하기 위해 추상 메서드로 선언한다.
  public abstract void service(int menuNo, DataInputStream in, DataOutputStream out);
}






