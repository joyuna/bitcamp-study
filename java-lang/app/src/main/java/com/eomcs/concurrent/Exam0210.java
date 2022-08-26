// 멀티 스레드 적용 전 - 멀티 태스킹을 적용하기 전
package com.eomcs.concurrent;

public class Exam0210 {

  public static void main(String[] args) {

    int count = 1000;

    for (int i = 0; i < 1000; i++) {
      System.out.println("==> " + i);
    }

    for (int i = 0; i < 1000; i++) {
      System.out.println(">>> " + i);
    }
  }
}

// 자바는 main() 메서드를 실행하는 한 개의 "실행 흐름"이 있다.
// 실행 흐름에 따라 순서대로 코드가 실행된다.


