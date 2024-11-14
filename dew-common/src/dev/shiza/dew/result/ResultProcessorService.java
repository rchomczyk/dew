package dev.shiza.dew.result;

import dev.shiza.dew.event.Event;
import dev.shiza.dew.result.registry.ResultProcessorRegistry;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ResultProcessorService implements ResultProcessorFacade {

  private final ResultProcessorRegistry resultProcessorRegistry;

  ResultProcessorService(final ResultProcessorRegistry resultProcessorRegistry) {
    this.resultProcessorRegistry = resultProcessorRegistry;
  }

  @Override
  public <E extends Event, T> void tryProcessing(final @NotNull E event, final @Nullable T value) {
    if (value == null) {
      return;
    }

    final Class<?> resultType = value.getClass();
    if (resultType == CompletableFuture.class) {
      processPromise(event, (CompletableFuture<?>) value);
      return;
    }

    final ResultProcessor<?, ?> resultProcessor =
        resultProcessorRegistry.getResultHandlerByClass(value.getClass());
    if (resultProcessor == null) {
      throw new ResultProcessingException(
          "Could not handle result of type %s, because of missing result handler."
              .formatted(value.getClass().getName()));
    }

    ((ResultProcessor<E, T>) resultProcessor).process(event, value);
  }

  private <E extends Event, T> void processPromise(
      final @NotNull E event, final @NotNull CompletableFuture<T> resultFuture) {
    resultFuture
        .whenComplete((result, cause) -> tryProcessing(event, result))
        .exceptionally(
            cause -> {
              throw new ResultProcessingException(
                  "Could not handle result of type %s, because of an exception."
                      .formatted(cause.getClass().getName()),
                  cause);
            });
  }
}
