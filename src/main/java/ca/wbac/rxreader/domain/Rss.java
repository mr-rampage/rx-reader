package ca.wbac.rxreader.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rss {
    private String link;

    private String title;
    private String description;
}
