package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import io.reactivex.subjects.PublishSubject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import static ca.wbac.rxreader.application.RssEffects.rssFeed$;
import static ca.wbac.rxreader.utils.FunctionalHelpers.applySideEffects;

@RestController
@RequestMapping("/api/rss")
final class RssResource {

    static final PublishSubject<String> rssRequest$ = PublishSubject.create();

    @PostMapping("/subscribe")
    DeferredResult<Rss> subscribeRss(@RequestBody final String href) {
        DeferredResult<Rss> result = new DeferredResult<>();
        applySideEffects(rssFeed$.take(1), result::setResult);
        rssRequest$.onNext(href);
        return result;
    }

}
