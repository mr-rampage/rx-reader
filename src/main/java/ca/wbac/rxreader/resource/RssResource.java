package ca.wbac.rxreader.resource;

import ca.wbac.rxreader.driver.RestDriver;
import ca.wbac.rxreader.utils.ActionHelpers;
import ca.wbac.rxreader.application.actions.FetchRss;
import ca.wbac.rxreader.application.actions.ListSubscriptions;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rss")
@RequiredArgsConstructor
final class RssResource {
    private final RestDriver restDriver;

    @PostMapping("/subscribe")
    Observable subscribeRss(@RequestBody final String href) {
        return restDriver.publish(new FetchRss(href))
                .map(ActionHelpers::respondOrBadRequest);
    }

    @GetMapping("/subscribed")
    Observable subscribedFeeds() {
        return restDriver.publish(new ListSubscriptions())
                .map(ActionHelpers::respondOrBadRequest);
    }

}
