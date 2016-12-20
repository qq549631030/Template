package com.hx.template.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.Holder;
import com.hx.imageloader.ImageLoaderManager;
import com.hx.template.R;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        imageView.setImageResource(R.drawable.default_image);
        ImageLoaderManager.getImageLoader(context).displayImage(data, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
                Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
