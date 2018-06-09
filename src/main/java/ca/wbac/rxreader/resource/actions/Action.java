package ca.wbac.rxreader.resource.actions;

import io.vavr.control.Try;

public interface Action {

    default <T> ActionResponse<T> respondWith(Try<T> response){
        return new ActionResponse<>(this, response);
    }
}
