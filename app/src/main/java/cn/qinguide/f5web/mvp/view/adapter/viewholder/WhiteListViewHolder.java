package cn.qinguide.f5web.mvp.view.adapter.viewholder;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljy.devring.DevRing;
import com.ljy.devring.image.support.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qinguide.f5web.R;
import cn.qinguide.f5web.xposed.entity.AppInfo;

public class WhiteListViewHolder extends BaseViewHolder {

    @BindView(R.id.text_name_item)
    TextView textNameItem;
    @BindView(R.id.checkbox_isEnabled)
    CheckBox checkBoxIsEnabled;
    @BindView(R.id.image_icon_item)
    ImageView imageIconItem;

    public WhiteListViewHolder(View view) {
        super(view);
    }

    public void setItem(AppInfo appInfo) {
        ButterKnife.bind(this, itemView);
        textNameItem.setText(appInfo.getAppName());
        checkBoxIsEnabled.setChecked(appInfo.getAppPlan() != -1);
        try {
           Drawable drawable = imageIconItem.getContext().getPackageManager().getApplicationIcon(appInfo.getPackageName());
            GlideApp.with(imageIconItem).load(drawable).into(imageIconItem);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
