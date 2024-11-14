package dev.shiza.dew.event;

import dev.shiza.dew.result.ResultProcessor;
import dev.shiza.dew.result.ResultProcessorFacade;
import dev.shiza.dew.result.ResultProcessorFacadeFactory;
import dev.shiza.dew.result.registry.ResultProcessorRegistry;
import dev.shiza.dew.result.registry.ResultProcessorRegistryFactory;
import dev.shiza.dew.subscription.SubscriptionFacadeFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class EventBusConfigurator {

  private final EventExecuteConfig eventExecuteConfig = new EventExecuteConfig();
  private final ResultProcessingConfig resultProcessingConfig = new ResultProcessingConfig();

  private EventBusConfigurator() {}

  public static EventBus configure(final Consumer<EventBusConfigurator> consumer) {
    final EventBusConfigurator eventBusConfigurator = new EventBusConfigurator();
    consumer.accept(eventBusConfigurator);

    final EventExecuteConfig eventExecuteConfig = eventBusConfigurator.eventExecuteConfig;
    final EventExecutor eventExecutor = eventExecuteConfig.eventExecutor;

    final ResultProcessingConfig resultProcessingConfig =
        eventBusConfigurator.resultProcessingConfig;
    final ResultProcessorRegistry resultProcessorRegistry =
        ResultProcessorRegistryFactory.createMapBasedRegistry(
            resultProcessingConfig.resultProcessors);
    final ResultProcessorFacade resultProcessorFacade =
        ResultProcessorFacadeFactory.create(resultProcessorRegistry);

    return new EventBusImpl(
        SubscriptionFacadeFactory.create(),
        resultProcessorFacade,
        resultProcessorRegistry,
        eventExecutor);
  }

  public void execute(final Consumer<EventExecuteConfig> consumer) {
    consumer.accept(eventExecuteConfig);
  }

  public void process(final Consumer<ResultProcessingConfig> consumer) {
    consumer.accept(resultProcessingConfig);
  }

  public static final class EventExecuteConfig {

    private EventExecutor eventExecutor = Runnable::run;

    private EventExecuteConfig() {}

    public void executor(final EventExecutor eventExecutor) {
      this.eventExecutor = eventExecutor;
    }
  }

  public static final class ResultProcessingConfig {

    private final Map<Class<?>, ResultProcessor<? extends Event, ?>> resultProcessors;

    private ResultProcessingConfig() {
      this.resultProcessors = new HashMap<>();
    }

    public <E extends Event, T> void processor(
        final Class<T> resultType, final ResultProcessor<E, T> resultProcessor) {
      resultProcessors.put(resultType, resultProcessor);
    }
  }
}
