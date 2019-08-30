package cn.qinguide.f5web.di.module.activity;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.di.scope.ActivityScope;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.xposed.share.Share;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.presenter.WhiteListPresenter;
import cn.qinguide.f5web.mvp.view.iview.IWhiteListView;
import cn.qinguide.f5web.mvp.model.imodel.IWhiteListModel;
import cn.qinguide.f5web.mvp.model.WhiteListModel;


@Module
public class WhiteListActivityModule {

    private IWhiteListView mIView;

    public WhiteListActivityModule(IWhiteListView iView) {
        mIView = iView;
    }

    @ActivityScope
    @Provides
    IWhiteListView iWhiteListView() {
        return mIView;
    }

    @ActivityScope
    @Provides
    IWhiteListModel iWhiteListModel() {
        return new WhiteListModel();
    }

    @ActivityScope
    @Provides
    WhiteListPresenter provideWhiteListPresenter(IWhiteListView iView, IWhiteListModel iModel) {
        return new WhiteListPresenter(iView, iModel);
    }

    @ActivityScope
    @Provides
    MaterialDialog.ListCallbackSingleChoice listCallbackSingleChoice() {
        return new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                mIView.setPlan(which, mIView.getSelectApp());
                return false;
            }
        };
    }

}