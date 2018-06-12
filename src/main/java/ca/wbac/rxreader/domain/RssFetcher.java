package ca.wbac.rxreader.domain;

public interface RssFetcher {
    Feed fetch(String href) throws Exception;
}
