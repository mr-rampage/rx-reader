package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;

import static ca.wbac.rxreader.utils.FunctionalHelpers.applySideEffects;

@Component
final class RssEffects {
    static final PublishSubject<Rss> rssFeed$ = PublishSubject.create();

    RssEffects(RssMapper rssMapper, RssFeedRepository rssFeedRepository) {
        Observable<Rss> feedSource$ = fetchFeeds(rssMapper);
        applySideEffects(feedSource$, rssFeed$::onNext, rssFeedRepository::save);
    }

    private Observable<Rss> fetchFeeds(RssMapper rssMapper) {
        return RssResource.rssRequest$
                .map(this::fetchFeed)
                .map(rssMapper::syndFeedToRss);
    }

    private SyndFeed fetchFeed(String href) throws IOException, FeedException {
        return new SyndFeedInput().build(new XmlReader(new URL(href)));
    }
}

@Mapper(componentModel = "spring")
interface RssMapper {
    Rss syndFeedToRss(SyndFeed syndFeed);
}

@Transactional
@Repository
interface RssFeedRepository extends JpaRepository<Rss, String> {}