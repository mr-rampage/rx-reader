package ca.wbac.rxreader.application;

import ca.wbac.rxreader.application.intent.FetchRss;
import ca.wbac.rxreader.domain.Rss;
import ca.wbac.rxreader.driver.ActionResponse;
import ca.wbac.rxreader.driver.Driver;
import ca.wbac.rxreader.driver.Intent;
import io.reactivex.Observable;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

@Component
final class RssActions {
    final Observable<Rss> subscriptionSource$;
    private final RssFetcher rssFetcher;

    RssActions(final Driver restDriver, final RssFetcher rssFetcher) {
        this.rssFetcher = rssFetcher;

        Observable<FetchRss> intent$ = intent(restDriver);
        Observable<Try<Rss>> subscription$ = model(intent$);
        restDriver.publish(respond(intent$, subscription$));

        this.subscriptionSource$ = subscription$.filter(Try::isSuccess).map(Try::get);
    }

    private Observable<FetchRss> intent(final Driver driver) {
        return driver.source$(FetchRss.class);
    }

    private Observable<Try<Rss>> model(final Observable<FetchRss> intent$) {
        return intent$.map(fetchRss -> Try.of(() -> rssFetcher.fetch(fetchRss.getHref())));
    }

    private Observable<ActionResponse<Rss>> respond(final Observable<FetchRss> intent$, final Observable<Try<Rss>> response$) {
        return Observable.combineLatest(intent$, response$, Intent::respondWith);
    }
}

