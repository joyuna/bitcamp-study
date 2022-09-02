// 1) 스레드 사용전
// 2) 스레드 사용후 => 패키지 멤버 클래스로 스레드 구현하기
// 3) 인스턴스 생성후 즉시 메서드 호출하기
// 4) 패키지 멤버를 스태틱 중첩 클래스로 만든다.
// 5) 스태틱 중첩 클래스를 로컬 클래스로 만든다.
// 6) 로컬 클래스가 바깥 메서드의 변수를 사용할 때
//    로컬 클래스에서 그 변수의 값을 다룰 수 있도록
//    그와 관련된 컴파일러가 인스턴스 필드와 생성자 파라미터를 자동으로 만드는 기법을 활용한다.


package com.eomcs.concurrent;

public class Exam0160 {

  public static void main(String[] args) {

    int count = 1000;

    class MyThread extends Thread{
      // 컴파일러가 바깥 메서드의 count 변수 값을 담을 수 있도록 필드를 자동으로 생성한다.
      //      int count;// 1. 카운트값을 받기 위해 인스턴스 필드 생성
      //
      // 컴파일러가 바깥 메서드의 count 변수를 값을 필드에 담을 수 있게
      // 생성자에 파라미터를 추가하고 필드에 파라미터 값을 저장하는 코드를 자동생성한다.
      //
      //      public MyThread(int count) {
      //        this.count = count; // 2. 생성자에 카운트값 담음
      //      }

      @Override
      public void run() {
        for (int i = 0; i < 1000; i++) {
          System.out.println("==> " + i);
        }
      }
    }
    // 컴파일러가 MyThread 생성자를 호출할 때 count 변수의 값을 넘기는
    // 코드를 자동으로 생성할 것이니 개발자가 직접 넘길 필요가 없다.
    //    new MyThread(count).start();
    new MyThread().start();

    for (int i = 0; i < count; i++) {
      System.out.println(">>> " + i);
    }
  }
}

//자바는 main() 메서드를 실행하는 한 개의 "실행 흐름"이 있다.
//실행 흐름에 따라 순서대로 코드가 실행된다.