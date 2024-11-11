package com.wkk.algor;

import java.util.Comparator;

public class HeapBuff {
  private int[] arr;
  private int size;
  private Comparator<Integer> comparator;

  public HeapBuff(int size, Comparator<Integer> comparator) {
    this.arr = new int[size];
    this.comparator = comparator;
  }

  public HeapBuff(int[] arr, Comparator<Integer> comparator) {
    this.arr = arr;
    size = arr.length;
    this.comparator = comparator;
    for (int i = arr.length - 1; i >= 0; i--) {
      swim(i);
    }
  }

  public void swim(int index) {
    while (index != 0) {
      int parent = (index - 1) / 2;
      boolean flag = comparator.compare(arr[index], arr[parent]) < 0;
      if (!flag) {
        break;
      }
      swap(index, parent);
      index = parent;
    }
  }

  public void sink(int index) {
    while (index < size) {
      int leftChild = index * 2 + 1;
      if (leftChild >= size) {
        break;
      }
      int rightChild = index * 2 + 2;
      int hikeChild = leftChild;
      if (rightChild < size) {
        boolean childFlag = comparator.compare(arr[leftChild], arr[rightChild]) < 0;
        hikeChild = childFlag ? leftChild : rightChild;
      }
      if (comparator.compare(arr[hikeChild], arr[index]) >= 0) {
        break;
      }
      swap(index, hikeChild);
      index = hikeChild;
    }
  }

  public void add(int val) {
    arr[size++] = val;
    swim(size - 1);
  }

  public int poll() {
    if (size == 0) {
      return -1;
    }
    int val = arr[0];
    swap(0, size - 1);
    size--;
    sink(0);
    return val;
  }



  private void swap(int index1, int index2) {
    int temp = arr[index1];
    arr[index1] = arr[index2];
    arr[index2] = temp;
  }

  public static void main(String[] args) {
    int[] arr = {5, 3, 2, 4, 1, 6};
    HeapBuff heapBuff = new HeapBuff(arr, Comparator.naturalOrder());
    for (int i = 0; i < arr.length; i++) {
      System.out.println(heapBuff.poll());
    }
  }


}
