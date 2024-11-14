package dev.shiza.dew.event;

@FunctionalInterface
public interface EventExecutor {

  void execute(final Runnable task);
}
