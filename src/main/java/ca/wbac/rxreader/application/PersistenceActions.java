package ca.wbac.rxreader.application;

import ca.wbac.rxreader.application.intent.ListSubscriptions;
import ca.wbac.rxreader.domain.Rss;
import ca.wbac.rxreader.domain.RssFeedRepository;
import ca.wbac.rxreader.driver.ActionResponse;
import ca.wbac.rxreader.driver.Driver;
import ca.wbac.rxreader.driver.Intent;
import io.reactivex.Observable;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
final class PersistenceActions {
    private final RssFeedRepository rssFeedRepository;

    PersistenceActions(final Driver restDriver, final RssActions rssActions, final RssFeedRepository rssFeedRepository) {
        this.rssFeedRepository = rssFeedRepository;

        Observable<ListSubscriptions> intent$ = intent(restDriver);
        restDriver.publish(respond(intent$, model(intent$)));

        rssActions.subscriptionSource$.subscribe(rssFeedRepository::save);
    }

    private Observable<ListSubscriptions> intent(final Driver driver) {
        return driver.source$(ListSubscriptions.class);
    }

    private Observable<Try<List<Rss>>> model(final Observable<ListSubscriptions> source$) {
        return source$.map(action -> Try.of(rssFeedRepository::findAll));
    }

    private Observable<ActionResponse<List<Rss>>> respond(final Observable<ListSubscriptions> intent$, final Observable<Try<List<Rss>>> response$) {
        return Observable.combineLatest(intent$, response$, Intent::respondWith);
    }
}
