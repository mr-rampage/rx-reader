package ca.wbac.rxreader.resource;

import ca.wbac.rxreader.resource.actions.Action;
import ca.wbac.rxreader.resource.actions.ActionResponse;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import org.springframework.stereotype.Component;

@Component
public final class ResourceDriver {
    private final PublishSubject<Action> source$ = PublishSubject.create();
    private final BehaviorSubject<ActionResponse> sink$ = BehaviorSubject.create();

    void publish(Action action) {
        source$.onNext(action);
    }

    Observable<ActionResponse> sink$() {
        return sink$;
    }

    public Observable<Action> source$() {
        return source$.hide();
    }

    public void publish(ActionResponse response) {
        sink$.onNext(response);
    }
}
