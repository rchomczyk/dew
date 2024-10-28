package dev.shiza.dew.result;

import dev.shiza.dew.event.Event;
import java.util.Map;
import java.util.Map.Entry;

final class ResultHandlerService implements ResultHandlerFacade {

  private final Map<Class<?>, ResultHandler<?, ?>> handlers;

  ResultHandlerService(final Map<Class<?>, ResultHandler<?, ?>> handlers) {
    this.handlers = handlers;
  }

  @Override
  public <E extends Event, T> void register(
      final Class<T> resultType, final ResultHandler<E, T> resultHandler) {
    handlers.put(resultType, resultHandler);
  }

  @Override
  public <E extends Event, T> void process(final E event, final T value) {
    if (value == null) {
      return;
    }

    final ResultHandler<?, ?> resultHandler = getResultHandler(value.getClass());
    if (resultHandler == null) {
      throw new ResultHandlingException(
          "Could not handle result of type %s, because of missing result handler."
              .formatted(value.getClass().getName()));
    }

    ((ResultHandler<E, T>) resultHandler).handle(event, value);
  }

  private ResultHandler<?, ?> getResultHandler(final Class<?> clazz) {
    final ResultHandler<?, ?> resultHandler = handlers.get(clazz);
    if (resultHandler != null) {
      return resultHandler;
    }

    for (final Entry<Class<?>, ResultHandler<?, ?>> entry : handlers.entrySet()) {
      if (entry.getKey().isAssignableFrom(clazz) || clazz.isAssignableFrom(entry.getKey())) {
        return entry.getValue();
      }
    }

    return null;
  }
}
