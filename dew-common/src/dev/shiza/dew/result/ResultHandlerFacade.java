package dev.shiza.dew.result;

public interface ResultHandlerFacade {

  <T> void register(final Class<T> resultType, final ResultHandler<T> resultHandler);

  <T> void process(final T value);
}
