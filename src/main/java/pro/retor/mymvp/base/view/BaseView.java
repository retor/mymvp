package pro.retor.mymvp.base.view;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by retor on 23.06.2016.
 */

public interface BaseView {
    Action0 showProgress(final boolean show);

    Action1<Throwable> showError();

    Observable<Boolean> needUpdate();
}
