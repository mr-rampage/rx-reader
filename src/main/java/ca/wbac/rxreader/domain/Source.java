package ca.wbac.rxreader.domain;

import io.reactivex.Observable;

interface Source<T> {
    Observable<T> source$();
}
