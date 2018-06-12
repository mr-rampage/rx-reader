package ca.wbac.rxreader.domain.intent;

import ca.wbac.rxreader.driver.Intent;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class FetchRss implements Intent {
    private String href;

    public FetchRss(@NonNull String href) {
        this.href = href;
    }
}
