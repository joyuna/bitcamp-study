package com.eomcs.quiz.ex01;

// 출처: codefights.com
//
<<<<<<< HEAD
// 한번에 한 자리의 숫자만 바꿀 때 이전 값과 다르게 만들 수 있는 경우는 몇가지인가?
// 단 맨 앞의 숫자는 0이 될 수 없다. 바꾸는 숫자는 현재의 숫자보다 커야한다.
//
// 예)
// 10 => 17 번 
//
// [시간 복잡도]
// - O(k) : k는 10진수의 자릿수이다.
//
public class Test05 {

  public static void main(String[] args) {
    System.out.println(countWaysToChangeDigit(10) == 17);
    System.out.println(countWaysToChangeDigit(2345) == 22);  //test 

  }

  static int countWaysToChangeDigit(int value) {
    int answer = 0;  
    while (value != 0) {
      answer += 9 - value % 10;  // 맨 뒷자리를 9번 바꿀수 있다. // 
      value /= 10;  // 맨 뒷자리를 제거한다. 10로 나누면 소숫점으로 되고 그건 날아가기 때문에
    }
    // 이 메서드를 완성하시오!
    return answer - 1;
=======
// 숫자 배열에서 각 이웃하는 숫자간의 차가 가장 큰 것을 알아내기
// 예)
// [2, 4, 1, 0] => 3
// 

public class Test05 {

  public static void main(String[] args) {
    System.out.println(maxDiff(new int[]{2, 4, 1, 0}) == 3);
    System.out.println(maxDiff(new int[]{3, 1, 4, 3, 8, 7}) == 5);
  }

  static int maxDiff(int[] values) {
    int answer = 1;
    // 이 메서드를 완성하시오!
    return answer;
>>>>>>> f0ffe98 (22.07.21)
  }
}
