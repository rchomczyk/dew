package dev.shiza.dew.result;

public final class ResultProcessingException extends IllegalStateException {

  public ResultProcessingException(final String message) {
    super(message);
  }

  public ResultProcessingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
