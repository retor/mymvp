package pro.retor.mymvp.application.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pro.retor.mymvp.application.LiveDelegate;
import pro.retor.mymvp.base.presenter.BasePresenter;
import pro.retor.mymvp.base.view.BaseView;

import rx.Observable;


/**
 * Created by retor on 12.04.2016.
 */
public abstract class BaseActivity<M extends Parcelable, V extends BaseView, P extends BasePresenter<V, M>>
        extends AppCompatActivity implements BaseView {

    private LiveDelegate<M, V, P> delegate;
    private boolean needUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        delegate = new LiveDelegate<>();
        setContentView(createContent(savedInstanceState));
//        ButterKnife.bind(this);
        if (savedInstanceState != null)
            needUpdate = false;
        else
            needUpdate = true;
        delegate.setPresenter(getPresenter());
        delegate.onCreate((V) this, savedInstanceState, getIntent().getExtras());
    }

    protected abstract P getPresenter();

    protected abstract View createContent(Bundle savedInstanceState);

    @Override
    protected void onStart() {
        super.onStart();
        delegate.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        delegate.onSavedState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        delegate.onStop();
    }

    @Override
    protected void onDestroy() {
//        ButterKnife.unbind(this);
        super.onDestroy();
        delegate.onDestroy();
    }

    @Override
    public Observable<Boolean> needUpdate() {
        return Observable.just(needUpdate);
    }

    protected abstract void inject();
}
