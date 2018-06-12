package ca.wbac.rxreader.domain;

import ca.wbac.rxreader.application.SubscriptionRepository;
import ca.wbac.rxreader.domain.intent.ListSubscriptions;
import ca.wbac.rxreader.driver.ActionResponse;
import ca.wbac.rxreader.driver.Driver;
import ca.wbac.rxreader.driver.Intent;
import io.reactivex.Observable;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
final class Store {
    private final SubscriptionRepository subscriptionRepository;

    Store(final Driver rssDriver, final Source subscription, final SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;

        Observable<ListSubscriptions> action$ = intent(rssDriver);
        Observable<Try<List<Feed>>> subscription$ = model(action$);
        rssDriver.publish(respond(action$, subscription$));

        subscription.source$().subscribe(subscriptionRepository::save);
    }

    private Observable<ListSubscriptions> intent(final Driver driver) {
        return driver.source$(ListSubscriptions.class);
    }

    private Observable<Try<List<Feed>>> model(final Observable<ListSubscriptions> source$) {
        return source$.map(action -> Try.of(subscriptionRepository::findAll));
    }

    private Observable<ActionResponse<List<Feed>>> respond(final Observable<ListSubscriptions> intent$, final Observable<Try<List<Feed>>> response$) {
        return Observable.combineLatest(intent$, response$, Intent::respondWith);
    }
}
