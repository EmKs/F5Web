package cn.qinguide.f5web.di.module.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.di.scope.ActivityScope;

import javax.inject.Named;

import cn.qinguide.f5web.R;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.presenter.ScriptPresenter;
import cn.qinguide.f5web.mvp.view.iview.IScriptView;
import cn.qinguide.f5web.mvp.model.imodel.IScriptModel;
import cn.qinguide.f5web.mvp.model.ScriptModel;


@Module
public class ScriptActivityModule {
    private IScriptView mIView;
    private Context context;

    public ScriptActivityModule(IScriptView iView, Context context) {
        mIView = iView;
        this.context = context;
    }

    @ActivityScope
    @Provides
    IScriptView iScriptView() {
        return mIView;
    }

    @ActivityScope
    @Provides
    IScriptModel iScriptModel() {
        return new ScriptModel();
    }

    @ActivityScope
    @Provides
    ScriptPresenter provideScriptPresenter(IScriptView iView, IScriptModel iModel) {
        return new ScriptPresenter(iView, iModel);
    }

    @Named("dialog")
    @Provides
    @ActivityScope
    MaterialDialog materialDialog() {
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.tips_support)).positiveText(context.getString(R.string.edit))
                .positiveColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .negativeText(context.getString(R.string.cancel))
                .cancelable(false)
                .negativeColor(context.getResources().getColor(R.color.colorSubText))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();
    }

    @Named("delete")
    @Provides
    @ActivityScope
    MaterialDialog materialDeleteDialog() {
        return new MaterialDialog.Builder(context)
                .content(context.getString(R.string.is_delete))
                .title(context.getString(R.string.tips_support)).positiveText(context.getString(R.string.edit))
                .positiveText(R.string.delete)
                .positiveColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .negativeText(context.getString(R.string.cancel))
                .cancelable(false)
                .negativeColor(context.getResources().getColor(R.color.colorSubText))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mIView.refresh();
                    }
                }).build();
    }
}