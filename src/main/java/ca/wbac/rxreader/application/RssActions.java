package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static ca.wbac.rxreader.utils.FunctionalHelpers.applySideEffects;
import static io.reactivex.Observable.just;

@Component
@RequiredArgsConstructor
final class RssActions {
    private final RssFetcher rssFetcher;
    private final RssFeedRepository rssFeedRepository;

    Observable<Rss> addFeed(Observable<String> source$) {
        return source$
                .map(rssFetcher::fetch)
                .doOnNext(applySideEffects(rssFeedRepository::save));
    }

    Observable<List<Rss>> listFeeds() {
        return just(rssFeedRepository.findAll());
    }
}

