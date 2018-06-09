package ca.wbac.rxreader.driver;

import io.vavr.control.Try;

public interface Intent {

    default <T> ActionResponse<T> respondWith(Try<T> response){
        return new ActionResponse<>(this, response);
    }
}
