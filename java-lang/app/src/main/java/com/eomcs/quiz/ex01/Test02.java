package com.eomcs.quiz.ex01;

// [문제] 
// 패리티(parity)를 구하라!
// - 1의 개수가 홀수 개이면 1
// - 1의 개수가 짝수 개이면 0
// [훈련 목표]
// - 비교 연산자 및 비트 연산자 활용
// - 반복문 활용
// - 메서드 파라미터 및 리턴 값 다루기
//
public class Test02 {

  public static void main(String[] args) {
    int p = parity(0b01100011);
    System.out.println(p == 0); // true

    p = parity(0b01010111_01100011);
    System.out.println(p == 1); // true
  }

  static int parity(int value) {
    int r = 0;
    // 이 메서드를 완성하시오!

    //parity 비트 계산
    //1의 개수 (홀수:1, 짝수:0)
    // 맨끝에 1비트를 추출해서 exclusive or( r= r^x) => 같은 거 ex 1 1이면 0이고, 1 0 이면 1이다. => 익스클루시브 방식
    /*while (value != 0) {
      //r = r ^(value & 1); // 맨끝에 1비트 추출
        r ^= (value & 1);
        value >>> 1;
      }

      비트 개수만큼 이동해야하는 계산 방식?
     */

     /*보너스 풀이 - 성능을 더 올리는 방법
      하위비트를 계속 날리면서 가는 거
      01100100(=> 4 => x) 보다 하나 작은 01100011(=>3 => x-1)를 and 시킴
      
        01100100 <- x
      & 01100011 <- x-1
       ----------
        011000000(뒤에 3개를 한번에 날려버림) <- 얘를 또 x로 두고
      & 010111111 <- x보다 하나 작은 x-1
       ---------- 
        010000000
      & 001111111
       ----------
        000000000  

        3번 실행하고 답을 구할수 있다.

        while (value != 0) {
          r ^=1;
          value &= (value -1);
        }
      */
    return r;
  }

}
