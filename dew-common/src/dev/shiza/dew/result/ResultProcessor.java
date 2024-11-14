package dev.shiza.dew.result;

import dev.shiza.dew.event.Event;

@FunctionalInterface
public interface ResultProcessor<E extends Event, T> {

  void process(final E event, final T result);
}
