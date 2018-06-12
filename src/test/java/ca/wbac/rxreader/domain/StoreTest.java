package ca.wbac.rxreader.domain;

import ca.wbac.rxreader.application.SubscriptionRepository;
import ca.wbac.rxreader.domain.intent.ListSubscriptions;
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

import java.util.Collections;
import java.util.List;

import static io.reactivex.marble.MapHelper.of;
import static io.reactivex.marble.junit.MarbleRule.expectObservable;
import static io.reactivex.marble.junit.MarbleRule.hot;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class StoreTest {
    private final ListSubscriptions ACTION = new ListSubscriptions();
    private final Feed FEED = new Feed();
    private final List<Feed> SUBSCRIPTIONS = Collections.singletonList(FEED);

    @Rule
    public MarbleRule marble = new MarbleRule();
    @Captor
    private ArgumentCaptor<Observable<ActionResponse<Feed>>> observableCaptor;

    private Driver driver;
    private SubscriptionRepository subscriptionRepository;
    private Source<Feed> subscription;

    @Before
    public void setup() {
        driver = mock(Driver.class);
        Observable<ListSubscriptions> action$ = hot("--a--", of("a", ACTION));
        given(driver.source$(eq(ListSubscriptions.class))).willReturn(action$);

        subscriptionRepository = mock(SubscriptionRepository.class);
        given(subscriptionRepository.findAll()).willReturn(SUBSCRIPTIONS);

        subscription = mock(Source.class);
        Observable<Feed> feedSource$ = hot("---a--", of("a", FEED));
        given(subscription.source$()).willReturn(feedSource$);

        new Store(driver, subscription, subscriptionRepository);
    }

    @Test
    public void shouldPublishSubscriptionsToDriver_onFetchRssAction() {
        then(driver).should().publish(observableCaptor.capture());
        verifyActionResponse(observableCaptor.getValue());
    }

    private void verifyActionResponse(Observable<ActionResponse<Feed>> published$) {
        expectObservable(published$.map(ActionResponse::getSource)).toBe("--a--", of("a", ACTION));
        expectObservable(published$.map(ActionResponse::getResponse).map(Try::get)).toBe("--a--", of("a", SUBSCRIPTIONS));
    }
}
