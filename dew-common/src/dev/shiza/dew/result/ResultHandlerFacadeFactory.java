package dev.shiza.dew.result;

import java.util.HashMap;

public final class ResultHandlerFacadeFactory {

  private ResultHandlerFacadeFactory() {}

  public static ResultHandlerFacade create() {
    return new ResultHandlerService(new HashMap<>());
  }
}
