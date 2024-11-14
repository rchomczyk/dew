package dev.shiza.dew.result.registry;

public final class ResultProcessorRegistryFactory {

  private ResultProcessorRegistryFactory() {}

  public static ResultProcessorRegistry createMapBasedRegistry() {
    return new MapBasedResultProcessorRegistry();
  }
}
