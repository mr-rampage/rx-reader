package ca.wbac.rxreader.application;

import ca.wbac.rxreader.application.intent.FetchRss;
import ca.wbac.rxreader.domain.Feed;
import ca.wbac.rxreader.driver.ActionResponse;
import ca.wbac.rxreader.driver.Driver;
import ca.wbac.rxreader.driver.Intent;
import io.reactivex.Observable;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

@Component("subscription")
final class Subscription implements Source {
    private final Observable<Feed> source$;
    private final RssFetcher rssFetcher;

    Subscription(final Driver restDriver, final RssFetcher rssFetcher) {
        this.rssFetcher = rssFetcher;

        Observable<FetchRss> action$ = intent(restDriver);
        Observable<Try<Feed>> subscription$ = model(action$);
        restDriver.publish(respond(action$, subscription$));

        this.source$ = subscription$.filter(Try::isSuccess).map(Try::get);
    }

    @Override
    public Observable<Feed> source$() {
        return source$;
    }

    private Observable<FetchRss> intent(final Driver driver) {
        return driver.source$(FetchRss.class);
    }

    private Observable<Try<Feed>> model(final Observable<FetchRss> intent$) {
        return intent$.map(fetchRss -> Try.of(() -> rssFetcher.fetch(fetchRss.getHref())));
    }

    private Observable<ActionResponse<Feed>> respond(final Observable<FetchRss> intent$, final Observable<Try<Feed>> response$) {
        return Observable.combineLatest(intent$, response$, Intent::respondWith);
    }
}

