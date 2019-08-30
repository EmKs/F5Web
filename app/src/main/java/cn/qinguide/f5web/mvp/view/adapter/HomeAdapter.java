package cn.qinguide.f5web.mvp.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.view.adapter.viewholder.HomeViewHolder;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/4
 */
public class HomeAdapter extends BaseQuickAdapter<ScriptEntity, HomeViewHolder> {


    public HomeAdapter(int layoutResId, @Nullable List<ScriptEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(HomeViewHolder helper, ScriptEntity item) {
        helper.setItem(item);
    }
}
