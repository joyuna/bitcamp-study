package com.bitcamp;

import java.util.Arrays;

public class javajoa {
  public static void main (String[] args) {
    int[] score1 = new int[5];
    for (int i = 0; i < 5; i++) {
      System.out.println(score1[i]);
    }

    System.out.println("==========1==========");

    int[] score2 = new int[6];
    int tmp = score2.length;
    System.out.println(tmp);

    System.out.println("==========2==========");

    int[] score3 = new int[] {50, 60, 70, 80, 90};
    System.out.println(Arrays.toString(score3));

    System.out.println("==========3==========");

    int[] score4 = {45, 55, 65, 75, 85, 95};
    System.out.println(score4);

    System.out.println("==========4==========");

    int[] iArr1 = {100, 90, 80, 70, 60};
    for (int i = 0; i < iArr1.length; i++) {
      System.out.println(iArr1[i]);
    }

    System.out.println("==========5==========");

    int[] iArr2 = {101, 91, 81, 71, 61};
    for (int i = 0; i < iArr2.length; i++) {
      System.out.print(iArr2[i]+", ");
    }



  }
}
