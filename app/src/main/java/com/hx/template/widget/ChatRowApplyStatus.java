package com.hx.template.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hx.template.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/23 9:37
 * 邮箱：huangx@pycredit.cn
 */

public class ChatRowApplyStatus extends EaseChatRow {

    private TextView applyStatus;

    public ChatRowApplyStatus(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }


    @Override
    protected void onInflateView() {
        inflater.inflate(R.layout.row_apply_status, this);
    }

    /**
     * find view by id
     */
    @Override
    protected void onFindViewById() {
        applyStatus = (TextView) findViewById(R.id.tv_status_msg);
    }

    /**
     * refresh list view when message status change
     */
    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUpView(EMMessage message, int position, EaseChatMessageList.MessageListItemClickListener itemClickListener) {
        if (adapter instanceof EaseMessageAdapter) {
            ((EaseMessageAdapter) adapter).setShowAvatar(false);
            ((EaseMessageAdapter) adapter).setShowUserNick(false);
        }
        super.setUpView(message, position, itemClickListener);
    }

    /**
     * setup view
     */
    @Override
    protected void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        String txt = txtBody.getMessage();
        int indexStart = -1;
        int indexEnd = -1;
        indexStart = txt.indexOf("#", 0);
        if (indexStart >= 0) {
            indexEnd = txt.indexOf("#", indexStart + 1);
        }
        if (indexEnd >= 0 && indexStart >= 0) {
            SpannableStringBuilder builder = new SpannableStringBuilder(txt);
            builder.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View view) {
                    // TODO: 2016/12/23
                    Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getContext().getResources().getColor(R.color.base_color_c1));
                    ds.setUnderlineText(false);
                }
            }, indexStart, indexEnd + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            applyStatus.setText(builder);
            applyStatus.setMovementMethod(LinkMovementMethod.getInstance());//必须设置点击事件才有效
            applyStatus.setHighlightColor(getResources().getColor(android.R.color.transparent));//点击后背景透明
        } else {
            Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
            applyStatus.setText(span, TextView.BufferType.SPANNABLE);
        }
    }

    /**
     * on bubble clicked
     */
    @Override
    protected void onBubbleClick() {

    }
}
