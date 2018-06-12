package ca.wbac.rxreader.domain;

import ca.wbac.rxreader.domain.intent.FetchRss;
import ca.wbac.rxreader.driver.ActionResponse;
import ca.wbac.rxreader.driver.Driver;
import io.reactivex.Observable;
import io.reactivex.marble.junit.MarbleRule;
import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import static io.reactivex.marble.MapHelper.of;
import static io.reactivex.marble.junit.MarbleRule.expectObservable;
import static io.reactivex.marble.junit.MarbleRule.hot;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionTest {
    private final String FEED_URL = "http://feed.com/rss";
    private final Feed FEED = new Feed();
    private final FetchRss ACTION = new FetchRss(FEED_URL);

    @Rule
    public MarbleRule marble = new MarbleRule();
    @Captor
    private ArgumentCaptor<Observable<ActionResponse<Feed>>> observableCaptor;

    private Driver driver;
    private Subscription fixture;

    @Before
    public void setup() throws Exception {
        RssFetcher fetcher = mock(RssFetcher.class);
        given(fetcher.fetch(eq(FEED_URL))).willReturn(FEED);

        driver = mock(Driver.class);
        Observable<FetchRss> action$ = hot("--a--", of("a", ACTION));
        given(driver.source$(eq(FetchRss.class))).willReturn(action$);

        fixture = new Subscription(driver, fetcher);
    }

    @Test
    public void shouldEmitFeed_onFetchRssAction() {
        expectObservable(fixture.source$()).toBe("--a--", of("a", FEED));
    }

    @Test
    public void shouldPublishFeedsToDriver_onFetchRssAction() {
        then(driver).should().publish(observableCaptor.capture());
        verifyActionResponse(observableCaptor.getValue());
    }

    private void verifyActionResponse(Observable<ActionResponse<Feed>> published$) {
        expectObservable(published$.map(ActionResponse::getSource)).toBe("--a--", of("a", ACTION));
        expectObservable(published$.map(ActionResponse::getResponse).map(Try::get)).toBe("--a--", of("a", FEED));
    }
}
