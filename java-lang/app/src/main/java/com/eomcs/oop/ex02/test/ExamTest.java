package com.eomcs.oop.ex02.test;

//# 관련된 기능(메서드)을 묶어 분류하기 
//1) 분류 전
//2) 메서드를 클래스로 묶어 분류하기
//3) 클래스 변수 도입
//4) 클래스 변수의 한계 확인
//5) 인스턴스 변수 도입 
//6) 인스턴스 메서드 활용
//7) 패키지 멤버 클래스로 분리
//8) 클래스를 역할에 따라 패키지로 분류하기
public class ExamTest {


  static class Calculator {

    static int result = 0;

    static void plus(int value) {
      result += value;
    }

    static void minus(int value) {
      result -= value; 
    }

    static void multiple(int value) {
      result *= value;
    }

    static void divide(int value) {
      result /= value;
    }

    static int abs(int a) {

      return a >=0 ?  a : a * -1;
    }
  }

  public static void main(String[] args) {

    Calculator c1 = new Calculator();
    Calculator c2 = new Calculator();

    Calculator.plus(c1, 2);
    Calculator.plus(c2, 3);

    Calculator.plus(c1, 3);    
    Calculator.multiple(c2, 2);

    Calculator.minus(c1, 1);
    Calculator.plus(c2, 7);

    Calculator.multiple(c1, 7);
    Calculator.divide(c2, 4);

    Calculator.divide(c1, 3);
    Calculator.minus(c2, 5);




    System.out.printf("result = %d\n", c1.result);
    System.out.printf("result = %d\n", c2.result);

  }
}


