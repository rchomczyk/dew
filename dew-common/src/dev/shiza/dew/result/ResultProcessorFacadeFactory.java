package dev.shiza.dew.result;

import dev.shiza.dew.result.registry.ResultProcessorRegistry;

public final class ResultProcessorFacadeFactory {

  private ResultProcessorFacadeFactory() {}

  public static ResultProcessorFacade create(final ResultProcessorRegistry resultProcessorRegistry) {
    return new ResultProcessorService(resultProcessorRegistry);
  }
}
