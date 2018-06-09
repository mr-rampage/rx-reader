package ca.wbac.rxreader.resource;

import ca.wbac.rxreader.resource.actions.ActionResponse;
import ca.wbac.rxreader.resource.actions.FetchRss;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rss")
@RequiredArgsConstructor
final class RssResource {
    private final ResourceDriver resourceDriver;

    @PostMapping("/subscribe")
    Observable<ResponseEntity> subscribeRss(@RequestBody final String href) {
        FetchRss action = new FetchRss(href);
        resourceDriver.publish(action);

        return resourceDriver.sink$()
                .filter(response -> response.getSource().equals(action))
                .take(1)
                .map(ActionResponse::respondOrBadRequest);
    }

}
