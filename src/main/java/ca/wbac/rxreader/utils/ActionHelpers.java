package ca.wbac.rxreader.utils;


import ca.wbac.rxreader.driver.Intent;
import ca.wbac.rxreader.driver.ActionResponse;
import io.reactivex.functions.Predicate;
import org.springframework.http.ResponseEntity;

public final class ActionHelpers {

    public static Predicate<ActionResponse> isResponseFor(final Intent intent) {
        return actionResponse -> actionResponse.getSource().equals(intent);
    }

    public static <T> ResponseEntity<T> respondOrBadRequest(ActionResponse<T> response) {
        return response.getResponse()
                .map(ResponseEntity::ok)
                .getOrElse(ResponseEntity.badRequest().build());
    }
}
