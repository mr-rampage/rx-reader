package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import io.reactivex.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.reactivex.Observable.just;

@RestController
@RequestMapping("/api/rss")
@RequiredArgsConstructor
final class RssResource {
    private final RssActions rssActions;

    @PostMapping("/subscribe")
    Observable<ResponseEntity<Rss>> subscribeRss(@RequestBody final String href) {
        return rssActions.addFeed(just(href))
                .map(ResponseEntity::ok)
                .onErrorReturn(error -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    Observable<ResponseEntity<List<Rss>>> subscriptions() {
        return rssActions.listFeeds()
                .map(ResponseEntity::ok)
                .onErrorReturn(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
