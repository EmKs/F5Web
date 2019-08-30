package cn.qinguide.f5web.mvp.view.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.ljy.devring.util.RingToast;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import cn.qinguide.f5web.app.constant.KeyConstants;
import cn.qinguide.f5web.di.component.activity.DaggerScriptActivityComponent;
import cn.qinguide.f5web.di.module.activity.ScriptActivityModule;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.utils.UrlUtil;
import cn.qinguide.f5web.mvp.view.activity.base.BaseActivity;

import cn.qinguide.f5web.mvp.view.adapter.ScriptAdapter;
import cn.qinguide.f5web.mvp.view.adapter.viewholder.ScriptViewHolder;
import cn.qinguide.f5web.mvp.view.iview.IScriptView;
import cn.qinguide.f5web.mvp.presenter.ScriptPresenter;

import cn.qinguide.f5web.R;
import dagger.Lazy;


public class ScriptActivity extends BaseActivity<ScriptPresenter> implements IScriptView, CommonTitleBar.OnTitleBarListener, OnItemSwipeListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.titleBar_script)
    CommonTitleBar titleBarScript;
    @BindView(R.id.recycler_view_script)
    RecyclerView recyclerViewScript;
    @BindView(R.id.refresh_script)
    SwipeRefreshLayout refreshScript;

    @Named("dialog")
    @Inject
    Lazy<MaterialDialog> materialDialog;

    @Named("delete")
    @Inject
    Lazy<MaterialDialog> materialDeleteDialog;

    ScriptAdapter scriptAdapter = new ScriptAdapter(R.layout.item_script, new ArrayList<ScriptEntity>());

    @Override
    protected int getContentLayout() {
        return R.layout.activity_script;
    }

    @Override
    protected void initView(Bundle bundle) {
        DaggerScriptActivityComponent.builder().scriptActivityModule(new ScriptActivityModule(this, this)).build().inject(this);
        refreshScript.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorRed, R.color.colorGreen, R.color.colorBlue);
        recyclerViewScript.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewScript.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        scriptAdapter.bindToRecyclerView(recyclerViewScript);
        scriptAdapter.setEmptyView(R.layout.empty_load);
        scriptAdapter.enableSwipeItem();
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(scriptAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewScript); // 开启拖拽
        scriptAdapter.enableDragItem(itemTouchHelper, R.id.textView, true);
    }

    @Override
    protected void initData(Bundle bundle) {
        recyclerViewScript.setAdapter(scriptAdapter);
        mPresenter.initData();
    }

    @Override
    protected void initEvent() {
        refreshScript.setOnRefreshListener(this);
        titleBarScript.setListener(this);
        scriptAdapter.setOnItemSwipeListener(this);
        scriptAdapter.setOnItemClickListener(this);
        scriptAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case CommonTitleBar.ACTION_LEFT_BUTTON:
                ScriptActivity.this.finish();
                break;
        }
    }

    @Override
    public void setAdapter(List<ScriptEntity> data) {
        scriptAdapter.setNewData(data);
    }

    @Override
    public void showToast(String msg) {
        RingToast.show(msg);
    }

    @Override
    public void noData() {
        scriptAdapter.setEmptyView(R.layout.empty_error);
    }

    @Override
    public void hintRefresh() {
        if (refreshScript.isRefreshing())
            refreshScript.setRefreshing(false);
    }

    @Override
    public void showRefresh() {
        if (!refreshScript.isRefreshing())
            refreshScript.setRefreshing(true);
    }

    @Override
    public void refresh() {
        mPresenter.initData();
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
        //滑动开始
    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, final int pos) {
        final ScriptViewHolder scriptViewHolder = (ScriptViewHolder) viewHolder;
        //滑动结束，删除view
        materialDeleteDialog.get().getBuilder()
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.deleteFile(scriptViewHolder.getFileName(), scriptViewHolder.isEnabled());
                    }
                }).show();

    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        //滑动暂停时
    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        //滑动中
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.checkbox_isEnabled:
                ScriptEntity scriptEntity = (ScriptEntity) adapter.getData().get(position);
                scriptEntity.setEnable(!scriptEntity.isEnable());
                ((CheckBox) view).setChecked(scriptEntity.isEnable());
                mPresenter.saveSettings(scriptEntity);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ScriptEntity scriptEntity = (ScriptEntity) adapter.getData().get(position);
        materialDialog
                .get()
                .getBuilder()
                .content(UrlUtil
                        .FormatSupportPage(scriptEntity
                                .getSupportPage()))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(ScriptActivity.this, EditActivity.class).putExtra(KeyConstants.FILE_NAME, scriptEntity.getFileName()));
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onRefresh() {
        mPresenter.initData();
    }
}
