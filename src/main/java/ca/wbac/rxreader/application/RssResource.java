package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.AbstractMap;

@RestController
@RequestMapping("/api/rss")
public class RssResource {

    private BehaviorSubject<String> rssFetch$ = BehaviorSubject.create();

    private BehaviorSubject<DeferredResult<ResponseEntity>> deferred$ = BehaviorSubject.create();

    private Observable<ResponseEntity> rss$ = Rss.fetch(rssFetch$)
            .map(feed -> ResponseEntity.ok(feed));

    private Disposable responseHandler = deferred$
            .zipWith(rss$, (deferred, response) -> new AbstractMap.SimpleEntry<>(deferred, response))
            .subscribe(
                    deferredResponse -> deferredResponse.getKey().setResult(deferredResponse.getValue())
            );

    @PostMapping("/subscribe")
    public DeferredResult<ResponseEntity> subscribeRss(@RequestBody String href) {
        rssFetch$.onNext(href);
        return defer();
    }

    private DeferredResult<ResponseEntity> defer() {
        DeferredResult<ResponseEntity> deferred = new DeferredResult<>();
        deferred$.onNext(deferred);
        return deferred;
    }
}
