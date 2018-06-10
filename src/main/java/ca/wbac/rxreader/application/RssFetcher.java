package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Feed;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

interface RssFetcher {
    Feed fetch(String href) throws Exception;
}

@Component
@RequiredArgsConstructor
final class RssFetcherImpl implements RssFetcher {
    private final RssMapper rssMapper;

    public Feed fetch(String href) throws FeedException, IOException {
        SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(new URL(href)));
        return rssMapper.syndFeedToRss(syndFeed);
    }
}


@Mapper(componentModel = "spring")
interface RssMapper {
    Feed syndFeedToRss(SyndFeed syndFeed);
}
