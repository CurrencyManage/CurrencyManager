package com.hb.currencymanage.customview;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.currencymanage.R;

/**
 * Created by 汪彬 on 2018/5/10.
 */
public class CustomDialog extends Dialog
{
    
    private CustomDialog(Context context)
    {
        super(context);
    }
    
    public static class Builder
    {
        private View mRootView;
        
        private String mContentFirst;
        
        private String mContentSecond;
        
        private String mTitle;
        
        private boolean mContentFirstVisibility;
        
        private boolean mContentSecondVisibility;
        
        private View.OnClickListener mConfirmClickListener;
        
        private View.OnClickListener mCancelClickListener;
        
        private CustomDialog mDialog;
        
        public Builder(Context context)
        {
            mDialog = new CustomDialog(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mRootView = inflater.inflate(R.layout.dialog_deal_confirm, null);
            mDialog.addContentView(mRootView,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        
        public Builder setContentFirstVisibility(boolean visibility)
        {
            this.mContentFirstVisibility = visibility;
            return this;
        }
        
        public Builder setContentSecondVisibility(boolean visibility)
        {
            this.mContentSecondVisibility = visibility;
            return this;
        }
        
        public Builder setTitle(String title)
        {
            this.mTitle = title;
            return this;
        }
        
        public Builder setContentFirst(String contentFirst)
        {
            this.mContentFirst = contentFirst;
            return this;
        }
        
        public Builder setContentSecond(String contentSecond)
        {
            this.mContentSecond = contentSecond;
            return this;
        }
        
        public Builder setCancelListener(View.OnClickListener cancelListener)
        {
            this.mCancelClickListener = cancelListener;
            return this;
        }
        
        public Builder setConfirmListener(View.OnClickListener confirmListener)
        {
            this.mConfirmClickListener = confirmListener;
            return this;
        }
        
        public CustomDialog build()
        {
            TextView tvTitle = mRootView.findViewById(R.id.tv_title);
            TextView tvFirst = mRootView.findViewById(R.id.tv_first);
            TextView tvSecond = mRootView.findViewById(R.id.tv_second);
            TextView tvCancel = mRootView.findViewById(R.id.tv_cancel);
            TextView tvConfirm = mRootView.findViewById(R.id.tv_confirm);
            tvTitle.setText(!TextUtils.isEmpty(mTitle) ? mTitle : "");
            tvFirst.setVisibility(
                    mContentFirstVisibility ? View.VISIBLE : View.GONE);
            tvFirst.setText(
                    !TextUtils.isEmpty(mContentFirst) ? mContentFirst : "");
            tvSecond.setVisibility(
                    mContentSecondVisibility ? View.VISIBLE : View.GONE);
            tvSecond.setText(
                    !TextUtils.isEmpty(mContentSecond) ? mContentSecond : "");
            tvCancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (null != mCancelClickListener)
                    {
                        mCancelClickListener.onClick(v);
                        mDialog.dismiss();
                        return;
                    }
                    mDialog.dismiss();
                }
            });
            tvConfirm.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (null != mConfirmClickListener)
                    {
                        mConfirmClickListener.onClick(v);
                        mDialog.dismiss();
                        return;
                    }
                    mDialog.dismiss();
                }
            });
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
            return mDialog;
        }
    }
}