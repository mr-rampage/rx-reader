package ca.wbac.rxreader.domain;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.reactivex.Observable;

import java.net.URL;

public class Rss {

    public static Observable<SyndFeed> fetch(Observable<String> source) {
        return source.map(URL::new)
                .map(XmlReader::new)
                .map(xmlReader -> new SyndFeedInput().build(xmlReader));
    }
}
