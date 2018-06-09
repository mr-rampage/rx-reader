package ca.wbac.rxreader.utils;


import ca.wbac.rxreader.driver.ActionResponse;
import org.springframework.http.ResponseEntity;

public final class ResponseHelpers {
    public static <T> ResponseEntity<T> respondOrBadRequest(ActionResponse<T> response) {
        return response.getResponse()
                .map(ResponseEntity::ok)
                .getOrElse(ResponseEntity.badRequest().build());
    }
}
