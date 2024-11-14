package dev.shiza.dew.subscription;

public interface Subscriber {

  default String topic() {
    return null;
  }
}
