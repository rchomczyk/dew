package dev.shiza.dew.result.registry;

import dev.shiza.dew.event.Event;
import dev.shiza.dew.result.ResultProcessor;

public interface ResultProcessorRegistry {

  <E extends Event, T> void register(
      final Class<T> resultType, final ResultProcessor<E, T> resultProcessor);

  ResultProcessor<?, ?> getResultHandlerByClass(final Class<?> resultType);

  boolean isProcessingRequired();
}
