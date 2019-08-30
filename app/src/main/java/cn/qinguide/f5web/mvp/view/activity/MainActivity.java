package cn.qinguide.f5web.mvp.view.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ljy.devring.DevRing;
import com.ljy.devring.util.RingToast;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import cn.qinguide.f5web.di.component.activity.DaggerMainActivityComponent;
import cn.qinguide.f5web.di.module.activity.MainActivityModule;
import cn.qinguide.f5web.mvp.view.activity.base.BaseActivity;

import cn.qinguide.f5web.mvp.view.fragment.HomeFragment;
import cn.qinguide.f5web.mvp.view.fragment.base.BaseFragment;
import cn.qinguide.f5web.mvp.view.iview.IMainView;
import cn.qinguide.f5web.mvp.presenter.MainPresenter;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.xposed.utils.ActiveUtil;
import dagger.Lazy;


public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {


    @BindView(R.id.frame_main)
    FrameLayout frameMain;
    @BindView(R.id.titleBar_main)
    CommonTitleBar titleBarMain;

    @Inject
    @Named("home")
    Lazy<HomeFragment> homeFragment;

    private int currentIndex;
    private BaseFragment currentFragment;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle bundle) {
        DaggerMainActivityComponent.builder().mainActivityModule(new MainActivityModule(this)).build().inject(this);
        checkXposed();
    }

    private void checkXposed() {
        if (ActiveUtil.isModuleActive())
            return;
        titleBarMain.getCenterSubTextView().setText(R.string.xposed_disabled);
        titleBarMain.getCenterSubTextView().setTextColor(getResources().getColor(R.color.colorRed));
    }

    @Override
    protected void initData(Bundle bundle) {
        isInitFragment(bundle);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonStickyEvent(String msg) {
    }

    @Override
    public void exitApp() {
        MainActivity.this.finish();
        DevRing.activityListManager().exitApp();
    }

    @Override
    public void onBackPressed() {
        mPresenter.pressedBack();
    }

    @Override
    public void showExitToast() {
        RingToast.show(getString(R.string.exit_app));
    }

    private void isInitFragment(Bundle bundle) {
        if (bundle != null) {    //如果Activity被重构
            currentIndex = bundle.getInt("index");  //获取已保存的fragment
            switch (currentIndex) {    //判断重构前用户使用的fragment
                case 0:
                    HomeFragment oldHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
                    if (oldHomeFragment != null) {   //旧Fragment存在，不重新生成
                        addOrShowFragment(oldHomeFragment, "home");
                    } else { //如果被销毁，重新加载
                        addOrShowFragment(homeFragment.get(), "home");
                    }
                    break;
            }
        } else {  //创建HomeFragment并设置Index
            currentIndex = 0;
            addOrShowFragment(homeFragment.get(), "home");
        }
    }

    //添加或展示
    private void addOrShowFragment(BaseFragment fragment, String tag) {
        if (currentFragment == fragment) return;    //当同一个Fragment时返回
        if (!fragment.isAdded()) {   //当页面未添加时
            if (currentFragment == null)
                getSupportFragmentManager().beginTransaction().add(R.id.frame_main, fragment, tag).commit();
            else
                getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.frame_main, fragment, tag).commit();
        } else {  //页面已存在时
            if (currentFragment == null)
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
            else
                getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fragment).commit();
        }

        if (currentFragment != null)
            currentFragment.setUserVisibleHint(false);
        currentFragment = fragment;
        currentFragment.setUserVisibleHint(true);
    }

    //保存Fragment Index
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", currentIndex);
    }

}
