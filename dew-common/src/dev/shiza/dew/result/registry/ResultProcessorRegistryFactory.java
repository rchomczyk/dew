package dev.shiza.dew.result.registry;

import dev.shiza.dew.event.Event;
import dev.shiza.dew.result.ResultProcessor;
import java.util.Map;

public final class ResultProcessorRegistryFactory {

  private ResultProcessorRegistryFactory() {}

  public static ResultProcessorRegistry createMapBasedRegistry(
      final Map<Class<?>, ResultProcessor<? extends Event, ?>> resultProcessors) {
    return new MapBasedResultProcessorRegistry(resultProcessors);
  }
}
