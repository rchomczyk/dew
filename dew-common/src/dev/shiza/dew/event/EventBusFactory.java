package dev.shiza.dew.event;

import dev.shiza.dew.result.ResultHandlerFacade;
import dev.shiza.dew.result.ResultHandlerFacadeFactory;
import dev.shiza.dew.subscription.SubscriptionFacade;
import dev.shiza.dew.subscription.SubscriptionFacadeFactory;

public final class EventBusFactory {

  private EventBusFactory() {}

  public static EventBus create(
      final SubscriptionFacade subscriptionFacade, final ResultHandlerFacade resultHandlerFacade) {
    return new EventBusImpl(subscriptionFacade, resultHandlerFacade);
  }

  public static EventBus create() {
    return create(SubscriptionFacadeFactory.create(), ResultHandlerFacadeFactory.create());
  }
}
