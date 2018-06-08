package ca.wbac.rxreader.utils;

import io.reactivex.functions.Consumer;

import java.util.Arrays;

final public class FunctionalHelpers {
    @SafeVarargs
    public static <T> Consumer<T> applySideEffects(final java.util.function.Consumer<T>... sideEffects) {
        return value -> Arrays.stream(sideEffects)
                .forEach(sideEffects1 -> sideEffects1.accept(value));
    }
}
