package dev.shiza.dew.result.registry;

import dev.shiza.dew.event.Event;
import dev.shiza.dew.result.ResultProcessor;
import java.util.Map;
import java.util.Map.Entry;

final class MapBasedResultProcessorRegistry implements ResultProcessorRegistry {

  private final Map<Class<?>, ResultProcessor<?, ?>> resultProcessors;

  MapBasedResultProcessorRegistry(final Map<Class<?>, ResultProcessor<?, ?>> resultProcessors) {
    this.resultProcessors = resultProcessors;
  }

  @Override
  public <E extends Event, T> void register(
      final Class<T> resultType, final ResultProcessor<E, T> resultProcessor) {
    resultProcessors.put(resultType, resultProcessor);
  }

  @Override
  public ResultProcessor<?, ?> getResultHandlerByClass(final Class<?> resultType) {
    final ResultProcessor<?, ?> resultProcessor = resultProcessors.get(resultType);
    if (resultProcessor != null) {
      return resultProcessor;
    }

    for (final Entry<Class<?>, ResultProcessor<?, ?>> entry : resultProcessors.entrySet()) {
      if (isAssignableFrom(entry.getKey(), resultType)) {
        return entry.getValue();
      }
    }

    return null;
  }

  @Override
  public boolean isProcessingRequired() {
    return !resultProcessors.isEmpty();
  }

  private boolean isAssignableFrom(final Class<?> type, final Class<?> otherType) {
    return type.isAssignableFrom(otherType) || otherType.isAssignableFrom(type);
  }
}
