package pro.retor.mymvp.application;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import pro.retor.mymvp.base.presenter.BasePresenter;
import pro.retor.mymvp.base.view.BaseView;


/**
 * Created by retor on 25.06.2016.
 */

public class LiveDelegate<M extends Parcelable, V extends BaseView, P extends BasePresenter<V, M>> {
    private WeakReference<P> presenter;
    private String SAVE_TAG;
    private WeakReference<V> view;

    public void onCreate(@NonNull V view, Bundle savedState, Bundle arg) {
        checkPresenter();
        this.view = new WeakReference<>(view);
        SAVE_TAG = presenter.getClass().getName();
        if (arg != null)
            presenter.get().addArguments(arg);
        if (savedState != null)
            presenter.get().restoreModel((M) savedState.getParcelable(SAVE_TAG));
    }

    public void onStart() {
        checkPresenter();
        presenter.get().onViewAttached(view.get());
    }

    public void onSavedState(Bundle outState) {
        outState.putParcelable(SAVE_TAG, presenter.get().saveModel());
    }

    public void onStop() {
        checkPresenter();
        presenter.get().onViewDetached();
    }

    private void checkPresenter() {
        if (presenter == null)
            throw new IllegalArgumentException("Presenter must be set first");
    }

    public void setPresenter(P presenter) {
        this.presenter = new WeakReference<P>(presenter);
    }

    public void onDestroy() {
        if (presenter != null) {
            presenter.get().reset();
            presenter.clear();
        }
        if (view != null)
            view.clear();
    }
}
