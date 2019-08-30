package cn.qinguide.f5web.mvp.view.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.ljy.devring.util.RingToast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.qinguide.f5web.app.constant.KeyConstants;
import cn.qinguide.f5web.di.component.fragment.DaggerHomeFragmentComponent;
import cn.qinguide.f5web.di.module.fragment.HomeFragmentModule;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.model.utils.UrlUtil;
import cn.qinguide.f5web.mvp.view.activity.AboutActivity;
import cn.qinguide.f5web.mvp.view.activity.EditActivity;
import cn.qinguide.f5web.mvp.view.activity.ScriptActivity;
import cn.qinguide.f5web.mvp.view.activity.WhiteListActivity;
import cn.qinguide.f5web.mvp.view.adapter.HomeAdapter;
import cn.qinguide.f5web.mvp.view.fragment.base.BaseFragment;
import cn.qinguide.f5web.mvp.view.iview.IHomeView;
import cn.qinguide.f5web.mvp.presenter.HomePresenter;
import cn.qinguide.f5web.R;
import dagger.Lazy;


public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.refresh_home)
    SwipeRefreshLayout refreshHome;
    @BindView(R.id.menu_createScript)
    FloatingActionButton menuCreateScript;
    @BindView(R.id.menu_mineScript)
    FloatingActionButton menuMineScript;
    @BindView(R.id.menu_donation)
    FloatingActionButton menuDonation;
    @BindView(R.id.menu_whiteList)
    FloatingActionButton menuWhiteList;
    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerViewHome;

    @Inject
    Lazy<MaterialDialog> materialDialog;

    HomeAdapter homeAdapter = new HomeAdapter(R.layout.item_home, new ArrayList<ScriptEntity>());

    MaterialDialog.SingleButtonCallback singleButtonCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if (materialDialog.get().isShowing())
                materialDialog.get().dismiss();
        }
    };

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        DaggerHomeFragmentComponent.builder().homeFragmentModule(new HomeFragmentModule(this, getContext())).build().inject(this);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewHome.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        homeAdapter.bindToRecyclerView(recyclerViewHome);
        homeAdapter.setEmptyView(R.layout.empty_load);
    }

    @Override
    protected void initData() {
        recyclerViewHome.setAdapter(homeAdapter);
        mPresenter.getScriptListData(KeyConstants.PAGE, KeyConstants.SIZE, KeyConstants.INIT);
        refreshHome.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorRed, R.color.colorGreen, R.color.colorBlue);
    }

    @Override
    protected void initEvent() {
        refreshHome.setOnRefreshListener(this);
        menuCreateScript.setOnClickListener(this);
        menuMineScript.setOnClickListener(this);
        menuWhiteList.setOnClickListener(this   );
        menuDonation.setOnClickListener(this);
        homeAdapter.setOnLoadMoreListener(this, recyclerViewHome);
        homeAdapter.setOnItemClickListener(this);
    }

    @Override
    public void getScriptSuccess(List<ScriptEntity> list, int type) {
        switch (type) {
            case KeyConstants.INIT:
                homeAdapter.addData(list);
                break;
            case KeyConstants.LOADMORE:
                homeAdapter.addData(list);
                homeAdapter.loadMoreComplete();
                break;
            case KeyConstants.REFRESH:
                homeAdapter.setNewData(list);
                break;
        }
        KeyConstants.PAGE++;
    }

    @Override
    public void getScriptFail(int status, String desc, int type) {
        if (homeAdapter.getData().size() == 0)
            homeAdapter.setEmptyView(R.layout.empty_error);
        else homeAdapter.loadMoreFail();
        RingToast.show(desc);
    }

    @Override
    public void hintRefresh() {
        if (refreshHome.isRefreshing())
            refreshHome.setRefreshing(false);
    }

    @Override
    public void showRefresh() {
        if (!refreshHome.isRefreshing())
            refreshHome.setRefreshing(true);
    }

    @Override
    public void loadMoreEnd() {
        homeAdapter.loadMoreEnd();
    }

    @Override
    public void showToast(int stringId) {
        RingToast.show(stringId);
    }

    @Override
    public void showToast(String msg) {
        RingToast.show(msg);
    }

    @Override
    public void startActivity(Class clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    @Override
    public void onRefresh() {
        KeyConstants.PAGE = 1;
        mPresenter.getScriptListData(KeyConstants.PAGE, KeyConstants.SIZE, KeyConstants.REFRESH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_createScript:
                startActivity(EditActivity.class);
                break;
            case R.id.menu_mineScript:
                startActivity(ScriptActivity.class);
                break;
            case R.id.menu_donation:
                startActivity(AboutActivity.class);
                break;
            case R.id.menu_whiteList:
                startActivity(WhiteListActivity.class);
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getScriptListData(KeyConstants.PAGE, KeyConstants.SIZE, KeyConstants.LOADMORE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        final ScriptEntity scriptEntity = (ScriptEntity) adapter.getData().get(position);
        scriptEntity.setEnable(false);   //默认启动脚本

        materialDialog
                .get()
                .getBuilder()
                .content(UrlUtil.FormatSupportPage(scriptEntity.getSupportPage()))
                .onNegative(singleButtonCallback)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.downloadScript(scriptEntity);
                    }
                })
                .build()
                .show();
    }
}
