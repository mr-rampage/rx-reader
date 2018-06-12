package ca.wbac.rxreader.driver;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component("rssDriver")
public final class RssDriver implements Driver {
    private final PublishSubject<Intent> source$ = PublishSubject.create();
    private final BehaviorSubject<ActionResponse> sink$ = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public Observable<ActionResponse> publish(@NonNull final Intent intent) {
        source$.onNext(intent);
        return sink$.hide().filter(isResponseFor(intent)).take(1);
    }

    @Override
    public <T extends Intent> Observable<T> source$(Class<T> clazz) {
        return source$.hide()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .observeOn(Schedulers.newThread());
    }

    @Override
    public <T> void publish(@NonNull final Observable<ActionResponse<T>> sink) {
        disposables.add(sink.subscribe(sink$::onNext));
    }

    @PreDestroy
    private void onDestroy() {
        disposables.clear();
        disposables.dispose();
    }

    private Predicate<ActionResponse> isResponseFor(final Intent intent) {
        return actionResponse -> actionResponse.getSource().equals(intent);
    }
}
