package ca.wbac.rxreader.driver;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionResponse<T> {
    private final Intention source;
    private final Try<T> response;
}
