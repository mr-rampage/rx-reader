package ca.wbac.rxreader.driver;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import lombok.NonNull;

public interface Driver {
    Observable<ActionResponse> publish(@NonNull Intent intent);

    <T extends Intent> Observable<T> source$(Class<T> clazz);

    <T> void publish(@NonNull Observable<ActionResponse<T>> sink);
}
