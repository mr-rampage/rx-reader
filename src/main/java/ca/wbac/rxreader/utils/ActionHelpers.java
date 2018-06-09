package ca.wbac.rxreader.utils;


import ca.wbac.rxreader.driver.Intention;
import ca.wbac.rxreader.driver.ActionResponse;
import io.reactivex.functions.Predicate;
import org.springframework.http.ResponseEntity;

public final class ActionHelpers {

    public static Predicate<ActionResponse> isResponseFor(final Intention intention) {
        return actionResponse -> actionResponse.getSource().equals(intention);
    }

    public static <T> ResponseEntity<T> respondOrBadRequest(ActionResponse<T> response) {
        return response.getResponse()
                .map(ResponseEntity::ok)
                .getOrElse(ResponseEntity.badRequest().build());
    }
}
