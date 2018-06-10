package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Feed;
import io.reactivex.Observable;

public interface Source {
    Observable<Feed> source$();
}
