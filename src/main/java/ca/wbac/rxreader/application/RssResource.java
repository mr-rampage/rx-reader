package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Rss;
import io.reactivex.Observable;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rss")
@AllArgsConstructor
final class RssResource {

    private final RssEffects rssEffects;

    @PostMapping("/subscribe")
    public Observable<ResponseEntity<Rss>> subscribeRss(@RequestBody final String href) {
        return rssEffects.request(href)
                .map(ResponseEntity::ok)
                .onErrorReturn(throwable -> ResponseEntity.badRequest().build());
    }

}
