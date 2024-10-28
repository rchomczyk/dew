package dev.shiza.dew.result;

import dev.shiza.dew.event.Event;

public interface ResultHandler<E extends Event, T> {

  void handle(final E event, final T result);
}
