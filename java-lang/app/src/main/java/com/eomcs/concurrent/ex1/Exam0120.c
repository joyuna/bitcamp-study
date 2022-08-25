#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
  int i = 0;
  pid_t processId = 0; // pid_t=> int 값을 받는 변수
  
  processId = fork(); // 현재 실행 실행 위치에서 프로세스 복제
  
  for (i = 0; i < 10000; i++) {
    printf("[%d] ==> %d\n", processId, i);
    int temp = rand() * rand(); // 시간을 지연시키기 위해 일부러 넣음
  }
  
  return 0;
}
