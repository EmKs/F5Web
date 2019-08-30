package cn.qinguide.f5web.mvp.view.adapter.viewholder;

import android.view.View;
import android.widget.CheckBox;
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
 * @date 2018/10/8
 */
public class ScriptViewHolder extends BaseViewHolder {


    @BindView(R.id.image_icon_item)
    ImageView imageIconItem;
    @BindView(R.id.text_name_item)
    TextView textNameItem;
    @BindView(R.id.expanded_text_item)
    ExpandableTextView expandableTextItem;
    @BindView(R.id.checkbox_isEnabled)
    CheckBox checkBoxIsEnabled;

    String fileName;

    public ScriptViewHolder(View view) {
        super(view);
    }

    public void setItem(ScriptEntity scriptEntity){
        ButterKnife.bind(this, itemView);
        if (scriptEntity.getIconUrl() == null )
            DevRing.imageManager().loadRes(R.drawable.ic_launcher, imageIconItem, new LoadOption().setRoundRadius(100));
        else DevRing.imageManager().loadNet(scriptEntity.getIconUrl(), imageIconItem, new LoadOption().setRoundRadius(100));
        textNameItem.setText(scriptEntity.getName());
        expandableTextItem.setContent(scriptEntity.getDescription());
        checkBoxIsEnabled.setChecked(scriptEntity.isEnable());
        fileName = scriptEntity.getFileName();
    }

    public String getFileName(){
        return fileName;
    }

    public boolean isEnabled(){
        return checkBoxIsEnabled.isChecked();
    }

}
