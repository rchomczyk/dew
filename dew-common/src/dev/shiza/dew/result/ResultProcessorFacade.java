package dev.shiza.dew.result;

import dev.shiza.dew.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public sealed interface ResultProcessorFacade permits ResultProcessorService {

  <E extends Event, T> void tryProcessing(final @NotNull E event, final @Nullable T value);
}
