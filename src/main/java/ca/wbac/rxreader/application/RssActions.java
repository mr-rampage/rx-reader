package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import ca.wbac.rxreader.resource.ResourceDriver;
import ca.wbac.rxreader.resource.actions.FetchRss;
import io.reactivex.Observable;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.reactivex.Observable.just;

@Component
final class RssActions {
    private final RssFeedRepository rssFeedRepository;

    RssActions(ResourceDriver resourceDriver, RssFetcher rssFetcher, RssFeedRepository rssFeedRepository) {
        this.rssFeedRepository = rssFeedRepository;

        resourceDriver.source$()
                .filter(FetchRss.class::isInstance)
                .map(FetchRss.class::cast)
                .map(fetchRss -> fetchRss.respondWith(Try.of(() -> rssFetcher.fetch(fetchRss.getHref()))))
                .subscribe(resourceDriver::publish);
    }

    Observable<List<Rss>> listFeeds() {
        return just(rssFeedRepository.findAll());
    }
}

