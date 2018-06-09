package ca.wbac.rxreader.utils;

import java.util.Arrays;
import java.util.function.Consumer;

final public class FunctionalHelpers {
    @SafeVarargs
    public static <T> Consumer<T> applySideEffects(final Consumer<T>... sideEffects) {
        return value -> Arrays.stream(sideEffects)
                .forEach(sideEffects1 -> sideEffects1.accept(value));
    }
}
