package com.proper.transport;

public interface EventPublisher<T> {
  //No need to implement this class, use it as you were given the implementation

  void put(T element);

  /**
   * @return the head of this queue, or {@code null} if this queue is empty
   */
  T poll();
}
