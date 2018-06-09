package ca.wbac.rxreader.resource.actions;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ActionResponse<T> {
    private final Action source;
    private final Try<T> response;

    public ResponseEntity<T> respondOrBadRequest() {
        return response
                .map(ResponseEntity::ok)
                .getOrElse(ResponseEntity.badRequest().build());
    }
}
