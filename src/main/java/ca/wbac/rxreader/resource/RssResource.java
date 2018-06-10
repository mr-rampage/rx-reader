package ca.wbac.rxreader.resource;

import ca.wbac.rxreader.application.intent.FetchRss;
import ca.wbac.rxreader.application.intent.ListSubscriptions;
import ca.wbac.rxreader.driver.Driver;
import ca.wbac.rxreader.utils.ResponseHelpers;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rss")
@RequiredArgsConstructor
final class RssResource {
    private final Driver restDriver;

    @PostMapping("/subscribe")
    Observable subscribeRss(@RequestBody final String href) {
        return restDriver.publish(new FetchRss(href))
                .map(ResponseHelpers::respondOrBadRequest);
    }

    @GetMapping("/subscriptions")
    Observable subscribedFeeds() {
        return restDriver.publish(new ListSubscriptions())
                .map(ResponseHelpers::respondOrBadRequest);
    }

}
