/*
 * 키보드 입력을 받는 도구를 구비하고 있다.
 */
package com.bitcamp.util;

public class Prompt {

  private static java.util.Scanner keyboardInput = new java.util.Scanner(System.in);

  public static int inputInt() {
    String str = keyboardInput.nextLine();
    return Integer.parseInt(str); //"123" ==> 123, "5" ==> 5, "ok" ==> 실행 오류!
  }

  public static int inputInt(String title) {
    System.out.print(title);
    String str = keyboardInput.nextLine();
    try {
      return Integer.parseInt(str); 
    } catch (java.lang.Throwable ex) {
      // 예외처리: 
      // => 예외가 발생했음을 알리는 메시지를 출력한다.
      System.out.println("숫자가 아닙니다.");

      //예외를 처리한 후에도 메서드는 값을 리턴해야한다.
      // 그런 사용자가 입력할법한 값을 리턴하면 호출하는 쪽에서는 예외가 발생했는지 모른다.
      // 그래서 다음과 같이 사용자가 입력할 것 같지 않은 아주 독특한 값을 리턴한다.
      return -12121212;
    }  
  }

  public static String inputString() {
    return keyboardInput.nextLine();
  }

  public static String inputString(String title) {
    System.out.print(title);
    return keyboardInput.nextLine();
  }

  public static void close() {
    keyboardInput.close();
  }
}
