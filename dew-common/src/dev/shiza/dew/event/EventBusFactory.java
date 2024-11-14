package dev.shiza.dew.event;

import dev.shiza.dew.result.ResultProcessorFacade;
import dev.shiza.dew.result.ResultProcessorFacadeFactory;
import dev.shiza.dew.result.registry.ResultProcessorRegistry;
import dev.shiza.dew.result.registry.ResultProcessorRegistryFactory;
import dev.shiza.dew.subscription.SubscriptionFacade;
import dev.shiza.dew.subscription.SubscriptionFacadeFactory;

public final class EventBusFactory {

  private EventBusFactory() {}

  public static EventBus create(
      final SubscriptionFacade subscriptionFacade,
      final ResultProcessorFacade resultProcessorFacade,
      final ResultProcessorRegistry resultProcessorRegistry) {
    return new EventBusImpl(subscriptionFacade, resultProcessorFacade, resultProcessorRegistry);
  }

  public static EventBus create() {
    final ResultProcessorRegistry resultProcessorRegistry =
        ResultProcessorRegistryFactory.createMapBasedRegistry();
    return create(
        SubscriptionFacadeFactory.create(),
        ResultProcessorFacadeFactory.create(resultProcessorRegistry),
        resultProcessorRegistry);
  }
}
