package dev.shiza.dew.event;

import dev.shiza.dew.result.ResultProcessorFacade;
import dev.shiza.dew.result.registry.ResultProcessorRegistry;
import dev.shiza.dew.subscription.Subscriber;
import dev.shiza.dew.subscription.Subscription;
import dev.shiza.dew.subscription.SubscriptionFacade;
import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.Set;

final class EventBusImpl implements EventBus {

  private final SubscriptionFacade subscriptionFacade;
  private final ResultProcessorFacade resultProcessorFacade;
  private final ResultProcessorRegistry resultProcessorRegistry;
  private final EventExecutor eventExecutor;

  EventBusImpl(
      final SubscriptionFacade subscriptionFacade,
      final ResultProcessorFacade resultProcessorFacade,
      final ResultProcessorRegistry resultProcessorRegistry,
      final EventExecutor eventExecutor) {
    this.subscriptionFacade = subscriptionFacade;
    this.resultProcessorFacade = resultProcessorFacade;
    this.resultProcessorRegistry = resultProcessorRegistry;
    this.eventExecutor = eventExecutor;
  }

  @Override
  public void subscribe(final Subscriber subscriber) {
    subscriptionFacade.subscribe(subscriber);
  }

  @Override
  public void publish(final Event event, final String... targets) {
    final Set<Subscription> subscriptions =
        subscriptionFacade.getSubscriptionsByEventType(event.getClass());
    for (final Subscription subscription : subscriptions) {
      notifySubscription(subscription, event, targets);
    }
  }

  private void notifySubscription(
      final Subscription subscription, final Event event, final String[] targets) {
    final Subscriber subscriber = subscription.subscriber();
    if (hasSpecifiedTarget(targets) && isExcludedSubscription(subscriber, targets)) {
      return;
    }

    for (final MethodHandle invocation : subscription.invocations()) {
      eventExecutor.execute(() -> notifySubscribedMethods(invocation, subscriber, event));
    }
  }

  private void notifySubscribedMethods(
      final MethodHandle invocation, final Subscriber subscriber, final Event event) {
    try {
      final Object returnedValue = invocation.invoke(subscriber, event);
      if (returnedValue != null && resultProcessorRegistry.isProcessingRequired()) {
        resultProcessorFacade.tryProcessing(event, returnedValue);
      }
    } catch (final Throwable exception) {
      throw new EventPublishingException(
          "Could not publish event, because of unexpected exception during method invocation.",
          exception);
    }
  }

  private boolean hasSpecifiedTarget(final String[] targets) {
    return targets.length > 0;
  }

  private boolean isExcludedSubscription(final Subscriber subscriber, final String[] targets) {
    return Arrays.stream(targets).noneMatch(identity -> subscriber.identity().equals(identity));
  }
}
