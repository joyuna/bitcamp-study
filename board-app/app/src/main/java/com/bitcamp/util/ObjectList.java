package com.bitcamp.util;

public class ObjectList {

  private static final int DEFAULT_CAPACITY = 10;

  private int size;
  private Object[] elementData;

  public ObjectList() {
    elementData = new Object[DEFAULT_CAPACITY];
  }

  public ObjectList(int initialCapacity) {
    elementData = new Object[initialCapacity];
  }

  public void add(Object e) {
    if (size == elementData.length) {
      grow();
    }

    elementData[size++] = e;
  }

  public Object[] toArray() {
    Object[] arr = new Object[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = elementData[i];
    }
    return arr;
  }

  // 개발자가 예외 클래스 이름만 보고도
  // 어떤 작업을 하다가 예외가 발생했는지
  // 직관적으로 알수 있도록 사용자 정의 예외를 던진다!!!
  // => RuntimeException 계열의 예외는 메서드 선언부에 표시할 필요가 없다.
  public Object get(int index) /*throws ListException*/ {
    if (index < 0 || index >= size) {
      throw new ListException("인덱스가 무효함!");
    }

    return elementData[index];
  }

  // 예외를 보고하는 메서드인 경우 
  // 메서드 선언부에 어떤 예외를 보고하는지 표시해야 한다.
  public boolean remove(int index) /*throws ListException*/ {
    if (index < 0 || index >= size) {
      // 인덱스가 무효할 때 false를 리턴하는 대신
      // 예외 정보를 호출자에게 던진다.
      // 예외 상황을 호출자에게 보고한다.
      throw new ListException("인덱스가 무효합니다!");
    }

    // 삭제할 항목의 다음 항목을 앞으로 당긴다.
    for (int i = index + 1; i < size; i++) {
      elementData[i - 1] = elementData[i];
    }

    // 목록의 개수를 한 개 줄인 후 
    // 맨 뒤의 있던 항목의 주소를 0으로 설정한다.
    elementData[--size] = null;

    return true;
  }

  public int size() {
    return size;
  }

  private void grow() {
    int newCapacity = elementData.length + (elementData.length >> 1);
    Object[] newArray = new Object[newCapacity];
    for (int i = 0; i < elementData.length; i++) {
      newArray[i] = elementData[i];
    }
    elementData = newArray;
  }
}








