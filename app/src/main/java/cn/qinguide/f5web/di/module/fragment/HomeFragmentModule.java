package cn.qinguide.f5web.di.module.fragment;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.di.scope.FragmentScope;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.presenter.HomePresenter;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.view.iview.IHomeView;
import cn.qinguide.f5web.mvp.model.imodel.IHomeModel;
import cn.qinguide.f5web.mvp.model.HomeModel;


@Module
public class HomeFragmentModule {

    private IHomeView mIView;

    private Context context;

    public HomeFragmentModule(IHomeView iView, Context context) {
        mIView = iView;
        this.context = context;
    }

    @FragmentScope
    @Provides
    IHomeView iHomeView() {
        return mIView;
    }

    @FragmentScope
    @Provides
    IHomeModel iHomeModel() {
        return new HomeModel();
    }

    @FragmentScope
    @Provides
    HomePresenter provideHomePresenter(IHomeView iView, IHomeModel iModel) {
        return new HomePresenter(iView, iModel);
    }

    @Provides
    @FragmentScope
    MaterialDialog MaterialDialog() {
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.tips_support))
                .positiveText(context.getString(R.string.download))
                .negativeText(context.getString(R.string.cancel))
                .cancelable(false)
                .positiveColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .negativeColor(context.getResources().getColor(R.color.colorSubText))
                .build();
    }
}