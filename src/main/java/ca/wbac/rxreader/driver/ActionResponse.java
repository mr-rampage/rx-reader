package ca.wbac.rxreader.driver;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActionResponse<T> {
    private final Intent source;
    private final Try<T> response;
}
