package ca.wbac.rxreader.driver;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static ca.wbac.rxreader.utils.ActionHelpers.isResponseFor;

@Component
public final class RestDriver {
    private final PublishSubject<Intent> source$ = PublishSubject.create();
    private final BehaviorSubject<ActionResponse> sink$ = BehaviorSubject.create();

    public Observable<ActionResponse> publish(@NonNull final Intent intent) {
        source$.onNext(intent);
        return sink$.filter(isResponseFor(intent)).take(1).hide();
    }

    public <T extends Intent> Observable<T> source$(Class<T> clazz) {
        return source$.hide()
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    public Disposable publish(@NonNull final Observable<ActionResponse> sink) {
        return sink.subscribe(sink$::onNext);
    }
}
