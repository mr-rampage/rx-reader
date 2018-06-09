package ca.wbac.rxreader.resource.actions;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FetchRss implements Action {
    private String href;

    public FetchRss(@NonNull String href) {
        this.href = href;
    }
}
