// 1) 스레드 사용전
// 2) 스레드 사용후 => 패키지 멤버 클래스로 스레드 구현하기
// 3) 인스턴스 생성후 즉시 메서드 호출하기

package com.eomcs.concurrent;

public class Exam0120 {

  public static void main(String[] args) {

    int count = 1000;

    new MyThread(count).start();

    for (int i = 0; i < count; i++) {
      System.out.println(">>> " + i);
    }
  }
}

// 자바는 main() 메서드를 실행하는 한 개의 "실행 흐름"이 있다.
// 실행 흐름에 따라 순서대로 코드가 실행된다.


