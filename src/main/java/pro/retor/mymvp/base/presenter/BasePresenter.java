package pro.retor.mymvp.base.presenter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pro.retor.mymvp.base.view.BaseView;

/**
 * Created by retor on 23.06.2016.
 */
public interface BasePresenter<V extends BaseView, M extends Parcelable> {

    @Nullable
    M getModel();

    void setModel(@NonNull M model);

    void addArguments(Object arguments);

    void onViewAttached(@NonNull V view);

    void onViewDetached();

    M saveModel();

    void restoreModel(M model);

    void reset();
}
