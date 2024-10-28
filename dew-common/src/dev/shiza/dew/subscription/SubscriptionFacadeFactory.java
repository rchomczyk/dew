package dev.shiza.dew.subscription;

import java.util.HashMap;

public final class SubscriptionFacadeFactory {

  private SubscriptionFacadeFactory() {}

  public static SubscriptionFacade create() {
    return new SubscriptionService(new HashMap<>());
  }
}
