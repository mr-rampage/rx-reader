package ca.wbac.rxreader.resource;

import ca.wbac.rxreader.domain.intent.FetchRss;
import ca.wbac.rxreader.domain.intent.ListSubscriptions;
import ca.wbac.rxreader.driver.Driver;
import ca.wbac.rxreader.utils.ResponseHelpers;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rss")
@RequiredArgsConstructor
final class RssResource {
    private final Driver rssDriver;

    @PostMapping("/subscribe")
    Observable subscribeRss(@RequestBody final String href) {
        return rssDriver.publish(new FetchRss(href))
                .map(ResponseHelpers::respondOrBadRequest);
    }

    @GetMapping("/subscriptions")
    Observable subscribedFeeds() {
        return rssDriver.publish(new ListSubscriptions())
                .map(ResponseHelpers::respondOrBadRequest);
    }

}
