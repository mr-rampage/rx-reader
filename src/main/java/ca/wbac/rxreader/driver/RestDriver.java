package ca.wbac.rxreader.driver;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class RestDriver {
    private final PublishSubject<Intent> source$ = PublishSubject.create();
    private final BehaviorSubject<ActionResponse> sink$ = BehaviorSubject.create();

    public Observable<ActionResponse> publish(@NonNull final Intent intent) {
        source$.onNext(intent);
        return sink$.hide().filter(isResponseFor(intent)).take(1);
    }

    public <T extends Intent> Observable<T> source$(Class<T> clazz) {
        return source$.hide()
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    public Disposable publish(@NonNull final Observable<ActionResponse> sink) {
        return sink.subscribe(sink$::onNext);
    }

    private Predicate<ActionResponse> isResponseFor(final Intent intent) {
        return actionResponse -> actionResponse.getSource().equals(intent);
    }
}
