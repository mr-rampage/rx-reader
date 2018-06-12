package ca.wbac.rxreader.domain;

import io.reactivex.Observable;

interface Source {
    Observable<Feed> source$();
}
