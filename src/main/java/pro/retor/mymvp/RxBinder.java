package pro.retor.mymvp;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by retor on 23.06.2016.
 */

public class RxBinder {

    /**
     * Wraps passed UI action into function that binds {@link Observable} to UI action on Main Thread.
     *
     * @param uiAction action that performs some UI interaction on Main Thread, like setting text to {@link android.widget.TextView} and so on.
     * @param <T>      type of {@link Observable} emission.
     * @return {@link Func1} that can be used to {@link #bind(Observable, Func1)} {@link Observable} to some UI action.
     */
    public static <T> Func1<Observable<T>, Subscription> ui(final Action1<T> uiAction, final Action1<Throwable> errorAction) {
        return new Func1<Observable<T>, Subscription>() {
            @Override
            public Subscription call(Observable<T> observable) {
                return observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uiAction, errorAction);
            }
        };
    }


    /**
     * Binds some UI function to {@link Observable}. Usually used in Presenter/ViewModel/etc classes.
     *
     * @param observable not-null source {@link Observable}.
     * @param uiFunc     not-null function that actually performs binding of the {@link Observable} to something, for example UI.
     * @param <T>        type of {@link Observable} emission.
     * @return {@link Subscription} that can be used to unsubscribe and stop bound action.
     */
    public static <T> Subscription bind(Observable<T> observable, Func1<Observable<T>, Subscription> uiFunc) {
        return uiFunc.call(observable);
    }
}

