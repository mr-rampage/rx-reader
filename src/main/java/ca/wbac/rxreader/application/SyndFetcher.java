package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Feed;
import ca.wbac.rxreader.domain.RssFetcher;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component("rssFetcher")
@RequiredArgsConstructor
public class SyndFetcher implements RssFetcher {
    private final SyndMapper syndMapper;

    public Feed fetch(String href) throws FeedException, IOException {
        SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(new URL(href)));
        return syndMapper.syndFeedToRss(syndFeed);
    }
}

@Mapper(componentModel = "spring")
interface SyndMapper {
    Feed syndFeedToRss(SyndFeed syndFeed);
}
