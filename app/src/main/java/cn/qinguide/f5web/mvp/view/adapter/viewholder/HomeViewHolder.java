package cn.qinguide.f5web.mvp.view.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.ljy.devring.DevRing;
import com.ljy.devring.image.support.LoadOption;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qinguide.f5web.R;
import cn.qinguide.f5web.mvp.model.entity.ScriptEntity;

/**
 * @author MHyun(叫我阿喵)
 * @date 2018/10/4
 */
public class HomeViewHolder extends BaseViewHolder {

    @BindView(R.id.image_icon_item)
    ImageView imageIconItem;
    @BindView(R.id.text_name_item)
    TextView textNameItem;
    @BindView(R.id.expanded_text_item)
    ExpandableTextView expandableTextItem;

    public HomeViewHolder(View view) {
        super(view);
    }

    public void setItem(ScriptEntity scriptEntity){
        ButterKnife.bind(this, itemView);
        DevRing.imageManager().loadNet(scriptEntity.getIconUrl(), imageIconItem, new LoadOption().setRoundRadius(100));
        textNameItem.setText(scriptEntity.getName());
        expandableTextItem.setContent(scriptEntity.getDescription()
                + "\n\n作者 : " + scriptEntity.getAuthor());
    }

}
