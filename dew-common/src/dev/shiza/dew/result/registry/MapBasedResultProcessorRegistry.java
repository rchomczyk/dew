package dev.shiza.dew.result.registry;

import dev.shiza.dew.event.Event;
import dev.shiza.dew.result.ResultProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class MapBasedResultProcessorRegistry implements ResultProcessorRegistry {

  private final Map<Class<?>, ResultProcessor<?, ?>> handlers;

  MapBasedResultProcessorRegistry() {
    this.handlers = new HashMap<>();
  }

  @Override
  public <E extends Event, T> void register(
      final Class<T> resultType, final ResultProcessor<E, T> resultProcessor) {
    handlers.put(resultType, resultProcessor);
  }

  @Override
  public ResultProcessor<?, ?> getResultHandlerByClass(final Class<?> resultType) {
    final ResultProcessor<?, ?> resultProcessor = handlers.get(resultType);
    if (resultProcessor != null) {
      return resultProcessor;
    }

    for (final Entry<Class<?>, ResultProcessor<?, ?>> entry : handlers.entrySet()) {
      if (isAssignableFrom(entry.getKey(), resultType)) {
        return entry.getValue();
      }
    }

    return null;
  }

  @Override
  public boolean isProcessingRequired() {
    return !handlers.isEmpty();
  }

  private boolean isAssignableFrom(final Class<?> type, final Class<?> otherType) {
    return type.isAssignableFrom(otherType) || otherType.isAssignableFrom(type);
  }
}
