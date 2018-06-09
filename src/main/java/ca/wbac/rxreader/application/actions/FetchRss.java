package ca.wbac.rxreader.application.actions;

import ca.wbac.rxreader.driver.Intention;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class FetchRss implements Intention {
    private String href;

    public FetchRss(@NonNull String href) {
        this.href = href;
    }
}
