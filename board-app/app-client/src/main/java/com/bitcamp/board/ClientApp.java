package com.bitcamp.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import com.bitcamp.util.Prompt;

public class ClientApp {

  public static void main(String[] args) {

    System.out.println("[게시글 관리 클라이언트]");
    // ++변경)한덩어리의 문자열 => 데코레이터 구조로 만들면 부품처럼 뺐다꼈다 하기 좋기 때무넹 기능 확장이 쉽다.
    // 상속은 바꾸기가 쉽지 않음
    try (Socket socket = new Socket("localhost", 8888);  // 1)
        DataInputStream in = new DataInputStream(socket.getInputStream());  // 4) 스캐너나 버퍼드리더를 통해 한줄씩 읽는다.
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      String response = null; // 5)


      // 5-3) 제거가능해서       line = in.readUTF();  // 6)
      System.out.println(response);

      // 5-1) 응답 요청 무한 받
      while (true) {
        //4-1)))) 사용자의 입력값을 서버에 전달한 후 서버의 응답을 출력한다.
        response = in.readUTF();  // 6) -> //5-2)자리이
        System.out.println(response);

        String input = Prompt.inputString("> "); 
        out.writeUTF(input);


      } 

    } catch (Exception e) { //2)
      System.out.println("서버와 통신 중 오류 발생!");
      e.printStackTrace(); //3)
    }

    //    loop: while (true) {
    //
    //      printTitle();
    //      printMenus(menus);
    //      System.out.println();
    //
    //      try {
    //        int mainMenuNo = Prompt.inputInt(String.format(
    //            "메뉴를 선택하세요[1..%d](0: 종료) ", handlers.size()));
    //
    //        if (mainMenuNo < 0 || mainMenuNo > menus.length) {
    //          System.out.println("메뉴 번호가 옳지 않습니다!");
    //          continue; // while 문의 조건 검사로 보낸다.
    //
    //        } else if (mainMenuNo == 0) {
    //          break loop;
    //        }
    //
    //        // 메뉴에 진입할 때 breadcrumb 메뉴바에 그 메뉴를 등록한다.
    //        breadcrumbMenu.push(menus[mainMenuNo - 1]);
    //
    //        // 메뉴 번호로 Handler 레퍼런스에 들어있는 객체를 찾아 실행한다.
    //        handlers.get(mainMenuNo - 1).execute();
    //
    //        breadcrumbMenu.pop();
    //
    //      } catch (Exception ex) {
    //        System.out.println("입력 값이 옳지 않습니다.");
    //      }
    //
    //
    //    } // while
    Prompt.close();

    System.out.println("종료!");


  }
}
