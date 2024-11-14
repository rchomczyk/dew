package dev.shiza.dew.event;

import dev.shiza.dew.subscription.Subscriber;
import dev.shiza.dew.subscription.SubscribingException;

public sealed interface EventBus permits EventBusImpl {

  void subscribe(final Subscriber subscriber) throws SubscribingException;

  void publish(final Event event, final String... targets) throws EventPublishingException;
}
