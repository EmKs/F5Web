package cn.qinguide.f5web.mvp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.view.adapter.viewholder.WhiteListViewHolder;
import cn.qinguide.f5web.xposed.entity.AppInfo;

public class WhiteListAdapter extends BaseQuickAdapter<AppInfo, WhiteListViewHolder> {

    public WhiteListAdapter(int layoutResId, @Nullable List<AppInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(WhiteListViewHolder helper, AppInfo item) {
        helper.setItem(item);
        helper.addOnClickListener(R.id.checkbox_isEnabled);
    }

}
