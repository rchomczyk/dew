package dev.shiza.dew.event;

import dev.shiza.dew.subscription.Subscriber;
import dev.shiza.dew.subscription.SubscribingException;
import dev.shiza.dew.subscription.SubscriptionFacade;

public sealed interface EventBus permits EventBusImpl {

  static EventBus getEventBus(final SubscriptionFacade subscriptionFacade) {
    return new EventBusImpl(subscriptionFacade);
  }

  void subscribe(final Subscriber subscriber) throws SubscribingException;

  void publish(final Event event) throws EventPublishingException;
}
