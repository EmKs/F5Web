package cn.qinguide.f5web.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ljy.devring.util.RingToast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import cn.qinguide.f5web.app.constant.KeyConstants;
import cn.qinguide.f5web.di.component.activity.DaggerEditActivityComponent;
import cn.qinguide.f5web.di.module.activity.EditActivityModule;
import cn.qinguide.f5web.jsdroideditor.editor.CodePane;
import cn.qinguide.f5web.jsdroideditor.editor.PreformEdit;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.view.activity.base.BaseActivity;

import cn.qinguide.f5web.mvp.view.iview.IEditView;
import cn.qinguide.f5web.mvp.presenter.EditPresenter;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.xposed.utils.KeyUtil;
import dagger.Lazy;


public class EditActivity extends BaseActivity<EditPresenter> implements IEditView, CommonTitleBar.OnTitleBarListener, View.OnClickListener, PreformEdit.onEditChangedListener {

    @BindView(R.id.titleBar_edit)
    CommonTitleBar titleBarEdit;
    @BindView(R.id.layout_edit)
    RelativeLayout layoutEdit;
    @BindView(R.id.image_button_redo)
    ImageButton imageButtonRedo;
    @BindView(R.id.image_button_undo)
    ImageButton imageButtonUndo;

    private PreformEdit preformEdit;    //高亮
    private MaterialEditText editTextName;
    private MaterialEditText editTextSupportPage;
    private MaterialEditText editTextDescription;
    private String fileName = "";

    private ScriptEntity scriptEntity;

    @Named("dialog")
    @Inject
    Lazy<MaterialDialog> materialDialog;

    @Named("progress")
    @Inject
    Lazy<MaterialDialog> materialProgressDialog;

    @Named("save")
    @Inject
    Lazy<MaterialDialog> materialSaveDialog;

    @Named("cover")
    @Inject
    Lazy<MaterialDialog> materialCoverDialog;

    @Inject
    Lazy<View> dialogCustomView;

    private MaterialDialog.SingleButtonCallback saveButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (which == DialogAction.NEGATIVE)
                dialog.dismiss();
            else if (which == DialogAction.POSITIVE) {
                materialProgressDialog.get().show();
                saveFile();
            }
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initView(Bundle bundle) {
        DaggerEditActivityComponent.builder().editActivityModule(new EditActivityModule(this, this)).build().inject(this);
        materialProgressDialog.get().show();
        CodePane codePane = new CodePane(this);
        layoutEdit.addView(codePane, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        preformEdit = new PreformEdit(codePane.getCodeText());
        //设置默认不可按压
        imageButtonUndo.setEnabled(false);
        imageButtonRedo.setEnabled(false);
        editTextName = dialogCustomView.get().findViewById(R.id.editText_name);
        editTextSupportPage = dialogCustomView.get().findViewById(R.id.editText_support_page);
        editTextDescription = dialogCustomView.get().findViewById(R.id.editText_description);
    }


    @Override
    protected void initData(Bundle bundle) {
        fileName = getIntent().getStringExtra(KeyConstants.FILE_NAME);
        scriptEntity = mPresenter.getScript(fileName);
    }

    @Override
    protected void initEvent() {
        titleBarEdit.setListener(this);
        imageButtonRedo.setOnClickListener(this);
        imageButtonUndo.setOnClickListener(this);
        preformEdit.setOnEditChangedListener(this);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case CommonTitleBar.ACTION_LEFT_BUTTON:
                if (preformEdit.historyBack.size() != 0 || preformEdit.history.size() != 0)
                    materialDialog.get().show();
                else EditActivity.this.finish();
                break;
            case CommonTitleBar.ACTION_RIGHT_BUTTON:
                editTextName.setText(fileName);
                if (scriptEntity != null) {
                    editTextDescription.setText(scriptEntity.getDescription());
                    editTextSupportPage.setText(scriptEntity.getSupportPage());
                }
                materialSaveDialog.get().getBuilder()
                        .customView(dialogCustomView.get(), false).build().show();
                break;
        }
    }

    @Override
    public void checkFileExists() {
        File file = new File(KeyUtil.SCRIPT_PATH + fileName);
        if (file.exists()) {
            materialCoverDialog.get().show();
        } else {
            materialProgressDialog.get().show();
            saveFile();
        }
    }

    @Override
    public void finishActivity() {
        hideDialog();
        EditActivity.this.finish();
    }

    @Override
    public void initScript(String script) {
        preformEdit.setDefaultText(script);
    }

    @Override
    public void showToast(int stringId) {
        RingToast.show(stringId);
    }

    @Override
    public void hideDialog() {
        if (materialSaveDialog.get().isShowing())
            materialSaveDialog.get().dismiss();
        if (materialProgressDialog.get().isShowing())
            materialProgressDialog.get().dismiss();
        if (materialDialog.get().isShowing())
            materialDialog.get().dismiss();
        if (materialCoverDialog.get().isShowing())
            materialCoverDialog.get().dismiss();
    }

    @Override
    public void saveFile() {
        mPresenter.saveScript(editTextName.getText(), editTextSupportPage.getText(), editTextDescription.getEditableText(), preformEdit.editText.getText());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_redo:
                preformEdit.redo();
                if (preformEdit.historyBack.size() == 0)
                    imageButtonRedo.setEnabled(false);
                break;
            case R.id.image_button_undo:
                preformEdit.undo();
                if (preformEdit.historyBack.size() == 0)
                    imageButtonUndo.setEnabled(false);
                break;
        }
    }

    @Override
    public void onEditableChanged(Editable editable) {

    }

    @Override
    public void onTextChanged(Editable editable) {
        imageButtonUndo.setEnabled(true);
        imageButtonRedo.setEnabled(true);
    }

}
