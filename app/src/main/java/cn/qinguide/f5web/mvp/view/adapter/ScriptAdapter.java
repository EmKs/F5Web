package cn.qinguide.f5web.mvp.view.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;

import java.util.List;

import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;
import cn.qinguide.f5web.mvp.view.adapter.viewholder.ScriptViewHolder;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/8
 */
public class ScriptAdapter extends BaseItemDraggableAdapter<ScriptEntity, ScriptViewHolder> {

    public ScriptAdapter(int layoutResId, List<ScriptEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ScriptViewHolder helper, ScriptEntity item) {
        helper.setItem(item);
        helper.addOnClickListener(R.id.checkbox_isEnabled);
    }
}
