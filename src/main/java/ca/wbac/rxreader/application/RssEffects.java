package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Mapper(componentModel = "spring")
interface RssMapper {
    Rss syndFeedToRss(SyndFeed syndFeed);
}

@Component
@AllArgsConstructor
final class RssEffects {

    private final RssMapper rssMapper;
    private final SyndFeedInput syndFeedInput;

    Observable<Rss> request(String href) {
        return Observable.just(href)
                .map(this::fetchFeed)
                .map(rssMapper::syndFeedToRss);
    }

    private SyndFeed fetchFeed(String href) throws IOException, FeedException {
        return this.syndFeedInput.build(new XmlReader(new URL(href)));
    }
}

