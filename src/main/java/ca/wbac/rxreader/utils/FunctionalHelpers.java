package ca.wbac.rxreader.utils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;
import java.util.function.Consumer;

final public class FunctionalHelpers {
    @SafeVarargs
    public static <T> Disposable applySideEffects(final Observable<T> source$, Consumer<T>... sideEffects) {
        return source$.subscribe(value -> Arrays.stream(sideEffects)
                .forEach(sideEffect -> sideEffect.accept(value)));
    }
}
