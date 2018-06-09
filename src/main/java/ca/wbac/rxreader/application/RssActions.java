package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import ca.wbac.rxreader.domain.RssFeedRepository;
import ca.wbac.rxreader.driver.RestDriver;
import ca.wbac.rxreader.driver.ActionResponse;
import ca.wbac.rxreader.application.actions.FetchRss;
import ca.wbac.rxreader.application.actions.ListSubscriptions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.util.List;

import static ca.wbac.rxreader.utils.FunctionalHelpers.applySideEffects;

@Component
final class RssActions {
    RssActions(RestDriver restDriver, RssFetcher rssFetcher, RssFeedRepository rssFeedRepository) {
        Consumer<ActionResponse<Rss>> persistRss = actionResponse ->
                actionResponse.getResponse().onSuccess(applySideEffects(rssFeedRepository::save));

        Observable<ActionResponse<Rss>> newFeed$ = restDriver.source$(FetchRss.class)
                .map(fetchRss -> fetchRss.respondWith(Try.of(() -> rssFetcher.fetch(fetchRss.getHref()))))
                .doOnNext(persistRss);

        Observable<ActionResponse<List<Rss>>> subscriptionList$ = restDriver.source$(ListSubscriptions.class)
                .map(action -> action.respondWith(Try.of(rssFeedRepository::findAll)));


        restDriver.publish(Observable.merge(newFeed$, subscriptionList$));
    }
}

