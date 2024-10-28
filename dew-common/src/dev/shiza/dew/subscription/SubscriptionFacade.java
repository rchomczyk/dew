package dev.shiza.dew.subscription;

import dev.shiza.dew.event.Event;
import java.util.Set;

public sealed interface SubscriptionFacade permits SubscriptionService {

  void subscribe(final Subscriber subscriber) throws SubscribingException;

  Set<Subscription> getSubscriptionsByEventType(final Class<? extends Event> eventType);
}
