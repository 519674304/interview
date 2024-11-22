package com.wkk.algor;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TrainStop {

  public static void main(String[] args) {
    System.out.println(res());
  }

  public static int res() {
    double[] input = new double[]{9, 9.4, 9.5, 11, 15, 18};
    double[] output = new double[]{10, 12, 11.2, 11.3, 19, 20};
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((v1, v2) -> output[v1] - output[v2] > 0 ? 1 : -1);
    int max = 0;
    for (int i = 0; i < input.length; i++) {
      while (!priorityQueue.isEmpty()) {
        if (output[priorityQueue.peek()] > input[i]) {
          break;
        }
        priorityQueue.poll();
      }
      priorityQueue.add(i);
      max = Math.max(max, priorityQueue.size());
    }
    return max;
  }

  @Test
  public void test32() {
    System.out.println("hello");
  }

  @Test
  public void test157() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
    System.out.println("position: " + byteBuffer.position());
    System.out.println("byteBuffer.limit() = " + byteBuffer.limit());

    byteBuffer.put("aab".getBytes(StandardCharsets.UTF_8));
    byteBuffer.flip();
    byte b = byteBuffer.get();
    System.out.println((char) b);
    System.out.println("byteBuffer.limit() = " + byteBuffer.limit());
    byteBuffer.compact();
    System.out.println("byteBuffer.limit() = " + byteBuffer.limit());
    byteBuffer.put("dd".getBytes());
    System.out.println(byteBuffer.position());
    System.out.println(byteBuffer.limit());
    byteBuffer.flip();
    System.out.println(byteBuffer.position());
    System.out.println(byteBuffer.limit());
  }


}
