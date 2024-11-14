package dev.shiza.dew.result;

public final class ResultHandlingException extends IllegalStateException {

  public ResultHandlingException(final String message) {
    super(message);
  }

  public ResultHandlingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
