package cn.qinguide.f5web.di.module.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.di.scope.ActivityScope;

import javax.inject.Named;

import cn.qinguide.f5web.R;
import dagger.Module;
import dagger.Provides;

import cn.qinguide.f5web.mvp.presenter.EditPresenter;
import cn.qinguide.f5web.mvp.view.iview.IEditView;
import cn.qinguide.f5web.mvp.model.imodel.IEditModel;
import cn.qinguide.f5web.mvp.model.EditModel;


@Module
public class EditActivityModule {
    private IEditView mIView;
    private Context context;

    public EditActivityModule(IEditView iView, Context context) {
        mIView = iView;
        this.context = context;
    }

    private MaterialDialog.SingleButtonCallback singleButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (which == DialogAction.NEGATIVE)
                dialog.dismiss();
            else if (which == DialogAction.POSITIVE)
                mIView.finishActivity();
        }
    };

    private MaterialDialog.SingleButtonCallback saveButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (which == DialogAction.NEGATIVE)
                dialog.dismiss();
            else if (which == DialogAction.POSITIVE) {
                mIView.checkFileExists();
            }
        }
    };

    private MaterialDialog.SingleButtonCallback coverButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (which == DialogAction.NEGATIVE)
                dialog.dismiss();
            else if (which == DialogAction.POSITIVE) {
                mIView.saveFile();
            }
        }
    };

    @ActivityScope
    @Provides
    IEditView iEditView() {
        return mIView;
    }

    @ActivityScope
    @Provides
    IEditModel iEditModel() {
        return new EditModel();
    }

    @ActivityScope
    @Provides
    EditPresenter provideEditPresenter(IEditView iView, IEditModel iModel) {
        return new EditPresenter(iView, iModel);
    }

    @Named("dialog")
    @Provides
    @ActivityScope
    MaterialDialog materialDialog() {
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.tips))
                .cancelable(false)
                .content(R.string.tips_discard_changes)
                .positiveText(context.getString(R.string.agree))
                .negativeText(R.string.cancel)
                .onPositive(singleButtonCallback)
                .onNegative(singleButtonCallback)
                .positiveColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .negativeColor(context.getResources().getColor(R.color.colorSubText))
                .build();
    }

    @Named("progress")
    @Provides
    @ActivityScope
    MaterialDialog materialProgressDialog() {
        return new MaterialDialog.Builder(context)
                .title(R.string.tips)
                .content(R.string.handle_file)
                .progress(true, 0)
                .build();
    }


    @Named("save")
    @Provides
    @ActivityScope
    MaterialDialog materialSaveDialog() {
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.tips))
                .cancelable(false)
                .positiveText(context.getString(R.string.save))
                .negativeText(R.string.cancel)
                .onNegative(saveButtonCallback)
                .onPositive(saveButtonCallback)
                .positiveColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .negativeColor(context.getResources().getColor(R.color.colorSubText))
                .build();
    }

    @Named("cover")
    @Provides
    @ActivityScope
    MaterialDialog materialCoverDialog() {
        return new MaterialDialog.Builder(context)
                .title(context.getString(R.string.tips))
                .cancelable(false)
                .content(R.string.file_cover)
                .positiveText(context.getString(R.string.agree))
                .negativeText(R.string.cancel)
                .onPositive(coverButtonCallback)
                .onNegative(coverButtonCallback)
                .positiveColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .negativeColor(context.getResources().getColor(R.color.colorSubText))
                .build();
    }

    @Provides
    @ActivityScope
    View dialogCustomView() {
        return LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
    }

}