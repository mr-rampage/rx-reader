package ca.wbac.rxreader.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rss_feeds")
@Data
@NoArgsConstructor
public class Rss {
    @Id
    private String link;

    private String title;
    private String description;
}
