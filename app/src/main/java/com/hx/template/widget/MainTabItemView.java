package com.hx.template.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hx.template.R;

/**
 * Created by huangxiang on 16/8/13.
 */
public class MainTabItemView extends RelativeLayout {
    private ImageView image;
    private TextView title;
    private TextView count;
    private boolean selected;

    private int imageResNormal;
    private int imageResSelected;

    private int colorNormal;
    private int colorSelected;

    public MainTabItemView(Context context) {
        this(context, null);
    }

    public MainTabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.main_tab_item, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        count = (TextView) findViewById(R.id.count);
        refreshViews();
    }

    private void refreshViews() {
        if (selected) {
            if (imageResSelected > 0 && colorSelected > 0) {
                image.setImageDrawable(tintDrawable(imageResSelected, colorSelected));
                title.setTextColor(getResources().getColorStateList(colorSelected));
            }
        } else {
            if (imageResNormal > 0 && colorNormal > 0) {
                image.setImageDrawable(tintDrawable(imageResNormal, colorNormal));
                title.setTextColor(getResources().getColorStateList(colorNormal));
            }
        }
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getCount() {
        return count;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        refreshViews();
    }

    public void setImageResNormal(int imageResNormal) {
        this.imageResNormal = imageResNormal;
    }

    public void setImageResSelected(int imageResSelected) {
        this.imageResSelected = imageResSelected;
    }

    public void setColorNormal(int colorNormal) {
        this.colorNormal = colorNormal;
    }

    public void setColorSelected(int colorSelected) {
        this.colorSelected = colorSelected;
    }

    private Drawable tintDrawable(int drawableId, int colorId) {
        Drawable icon = getResources().getDrawable(drawableId);
        Drawable tintIcon = DrawableCompat.wrap(icon);
        DrawableCompat.setTintList(tintIcon, getResources().getColorStateList(colorId));
        return tintIcon;
    }
}
