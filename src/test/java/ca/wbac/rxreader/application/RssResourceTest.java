package ca.wbac.rxreader.application;

import io.reactivex.Observable;
import io.reactivex.marble.junit.MarbleRule;
import org.junit.Rule;
import org.junit.Test;

import static io.reactivex.marble.junit.MarbleRule.expectObservable;
import static io.reactivex.marble.junit.MarbleRule.hot;

public class RssResourceTest {

    @Rule
    public MarbleRule marble = new MarbleRule();

    @Test
    public void shouldFail() {
        // given
        Observable<String> input = hot("a-b--c---d");
        // when
        Observable<String> output = input.map(String::toUpperCase);
        // then
        expectObservable(output).toBe( "A-B--C---D");
    }
}
