package pro.retor.mymvp.base.presenter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pro.retor.mymvp.base.view.BaseView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by retor on 25.06.2016.
 */
public abstract class Presenter <V extends BaseView, M extends Parcelable> implements BasePresenter<V,M>{
    private CompositeSubscription subscriptions= new CompositeSubscription();
    private M model;

    protected void addSubscription(Subscription subscription) {
        if (subscription != null)
            subscriptions.add(subscription);
    }

    @Nullable
    @Override
    public M getModel() {
        return model;
    }

    @Override
    public final void setModel(@NonNull M model) {
        this.model = model;
    }

    @Override
    public void addArguments(Object arguments) {

    }

    @Override
    public void onViewDetached() {
        subscriptions.clear();
    }

    @Override
    public void reset() {
//        subscriptions.clear();
    }

    @Override
    public M saveModel() {
        return model;
    }

    @Override
    public void restoreModel(M model) {
        this.model = model;
    }
}
