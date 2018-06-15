package com.clz.view.customtoolbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;


/**
 * 作    者：clz
 * 创作时间：2018/4/25
 * 描    述：
 */
public class CustomToolBar extends Toolbar {



    private LayoutInflater mInflater;

    private View mView;
    private TextView mTextTitle;
    private TextView mTextLeft;
    private TextView mTextRight;

    private TextView mTextBack;
    private TextView mTextBackWhite;
    private ImageView mImageLeft;
    private ImageView mImageRight;
    private RelativeLayout mRlLeft;
    private RelativeLayout mRlRight;
    private int mBgColor;
    private View line;
    private int mShadowColor;
    private boolean isShowShadow;


    public CustomToolBar(Context context) {
        this(context,null);
    }


    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);



        initView();
        //setContentInsetsRelative(10,10);左右间隔




        if(attrs !=null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar, defStyleAttr, 0);

            boolean isFitStatusBar = a.getBoolean(R.styleable.CustomToolBar_isFitStatusBar,true);
            if (isFitStatusBar){
                FrameLayout fl_status = mView.findViewById(R.id.fl_status);
                View statusBar = new View(getContext());
                LayoutParams flp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
                flp.height = getStatusBarHeight(getContext());
                fl_status.addView(statusBar,flp);
            }




            Drawable rightIcon = a.getDrawable(R.styleable.CustomToolBar_rightIcon);
            Drawable leftIcon = a.getDrawable(R.styleable.CustomToolBar_leftIcon);
            int bgColor = a.getColor(R.styleable.CustomToolBar_bgColor,-1);
            setBgColor(bgColor);
            mBgColor = bgColor;

            if (!isWhiteBg()){//如果不是白色背景自动设置文字为白色
                setTextColor(Color.parseColor("#ffffff"));
                final int rightTextColor = a.getColor(R.styleable.CustomToolBar_rightTextColor,getContext().getResources().getColor(R.color.white));
                setRightTextColor(rightTextColor);
                boolean isShowLine = a.getBoolean(R.styleable.CustomToolBar_isShowBottomLine,false);
                if (isShowLine){
                    line.setVisibility(VISIBLE);
                }else {
                    line.setVisibility(GONE);
                }
                mTextBack.setVisibility(GONE);
                mTextBackWhite.setVisibility(VISIBLE);

//                mImageLeft.setColorFilter(Color.parseColor("#ffffff"));
                if (rightIcon != null){
                    Drawable wrappedDrawable = DrawableCompat.wrap(rightIcon);
                    DrawableCompat.setTintList(wrappedDrawable,
                            ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    rightIcon = wrappedDrawable;
                }

                if (leftIcon != null){
                    Drawable wrappedDrawable = DrawableCompat.wrap(leftIcon);
                    DrawableCompat.setTintList(wrappedDrawable,
                            ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    leftIcon = wrappedDrawable;
                }

                if (!isTransparentBg()){//不是透明背景色默认加阴影
                    boolean isShowShadow = a.getBoolean(R.styleable.CustomToolBar_isShowShadow,true);
                    if (isShowShadow){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            setElevation(dp2px(5));
                        }
                    }

                }else {
                    boolean isShowShadow = a.getBoolean(R.styleable.CustomToolBar_isShowShadow,false);
                    if (isShowShadow){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            setElevation(dp2px(5));
                        }
                    }

                }


            }else {
                final int textColor = a.getColor(R.styleable.CustomToolBar_textColor,Color.parseColor("#333333"));
                setTextColor(textColor);

                final int rightTextColor = a.getColor(R.styleable.CustomToolBar_rightTextColor,getContext().getResources().getColor(R.color.gray_333));
                boolean isShowLine = a.getBoolean(R.styleable.CustomToolBar_isShowBottomLine,true);
                setRightTextColor(rightTextColor);
                if (isShowLine){
                    line.setVisibility(VISIBLE);
                }else {
                    line.setVisibility(GONE);
                }

                mTextBack.setVisibility(VISIBLE);
                mTextBackWhite.setVisibility(GONE);


                
                boolean isShowShadow = a.getBoolean(R.styleable.CustomToolBar_isShowShadow,true);
                if (isShowShadow){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        setElevation(dp2px(5));
                    }
                }

            }



            boolean isShowLeft = a.getBoolean(R.styleable.CustomToolBar_isShowLeft,true);

            if(isShowLeft){

                showLeft();

            }else {
                hideLeft();
            }



            if (rightIcon != null) {
                setRightIcon(rightIcon);
            }




            if (leftIcon != null) {

                setLeftIcon(leftIcon);
            }



            CharSequence rightText = a.getText(R.styleable.CustomToolBar_rightText);
            if(rightText !=null){
                setRightText(rightText);
            }


            CharSequence leftText = a.getText(R.styleable.CustomToolBar_leftText);
            if(leftText !=null){
                setLeftText(leftText);
            }
            CharSequence titleText = a.getText(R.styleable.CustomToolBar_titleText);
            if(titleText !=null){
                setTitle(titleText);
            }



            mShadowColor = a.getColor(R.styleable.CustomToolBar_shadowColor, getResources().getColor(R.color.shadow_color));

            float titleTextSize = a.getDimensionPixelOffset(R.styleable.CustomToolBar_titleTextSize,
                    getContext().getResources()
                            .getDimensionPixelOffset(R.dimen.font_size_18));

            mTextTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTextSize);
            float leftTextSize = a.getDimensionPixelOffset(R.styleable.CustomToolBar_leftTextSize,
                    getContext().getResources()
                            .getDimensionPixelOffset(R.dimen.font_size_14));

            mTextLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX,leftTextSize);
            float rightTextSize = a.getDimensionPixelOffset(R.styleable.CustomToolBar_rightTextSize,
                    getContext().getResources()
                            .getDimensionPixelOffset(R.dimen.font_size_14));

            mTextRight.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightTextSize);
            a.recycle();
        }

    }

    /**
     * 设置所有textview颜色
     * @param textColor
     */
    private void setTextColor(int textColor) {
        if (mTextTitle != null) {
            mTextTitle.setTextColor(textColor);
        }
        if (mTextLeft != null) {
            mTextLeft.setTextColor(textColor);
        }
        if (mTextRight != null) {
            mTextRight.setTextColor(textColor);
        }

        if (mTextBack != null) {
            mTextBack.setTextColor(textColor);
        }

        if (mTextBackWhite != null) {
            mTextBackWhite.setTextColor(textColor);
        }

    }

    /**
     * 设置右部分tv颜色
     * @param textColor
     */
    public void setRightTextColor(int textColor) {
        if (mTextRight != null) {
            mTextRight.setTextColor(textColor);
        }


    }

    private void setBgColor(int bgColor) {
        setBackgroundColor(bgColor);
        if (mView != null){
            mView.setBackgroundColor(bgColor);
//            LogUtil.e(getContext().getResources().getColor(bgColor)+"");
        }
    }




    private void initView() {



        if(mView == null) {

            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.layout_toolbar, null);


            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mTextLeft = (TextView) mView.findViewById(R.id.tv_left);
            mTextRight = (TextView) mView.findViewById(R.id.tv_right);
            mTextBack = (TextView) mView.findViewById(R.id.tv_back);
            mTextBackWhite = (TextView) mView.findViewById(R.id.tv_back_white);
            mImageLeft = (ImageView) mView.findViewById(R.id.iv_left);
            mImageRight = (ImageView) mView.findViewById(R.id.iv_right);
            mRlLeft = (RelativeLayout) mView.findViewById(R.id.left_rl);
            mRlRight = (RelativeLayout) mView.findViewById(R.id.right_rl);
            line = (View) mView.findViewById(R.id.line);


            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
//            lp.height = getContext().getResources().getDimensionPixelSize(R.dimen.h_88px);
//            lp.setMargins(0,0,0,20);
            addView(mView, lp);


        }



    }


    /**
     * 设置左侧图片
     * @param icon drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void  setLeftIcon(Drawable icon){

        if(mImageLeft !=null){
            mImageLeft.setBackground(icon);
            mImageLeft.setVisibility(VISIBLE);
        }
        if (mRlLeft != null){
            mRlLeft.setVisibility(VISIBLE);
        }
        if (mTextLeft != null) {
            mTextLeft.setVisibility(GONE);
        }
        if (mTextBack != null) {
            mTextBack.setVisibility(GONE);
        }
        if (mTextBackWhite != null) {
            mTextBackWhite.setVisibility(GONE);
        }

    }

    /**
     * 设置左侧图片
     * @param icon 资源id
     */
    public void  setLeftIcon(int icon){

        setLeftIcon(getResources().getDrawable(icon));
    }

    /**
     * 设置右侧图片
     * @param icon drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void  setRightIcon(Drawable icon){

        if(mImageRight !=null){
            mImageRight.setBackground(icon);
            mImageRight.setVisibility(VISIBLE);
        }
        if (mRlRight != null){
            mRlRight.setVisibility(VISIBLE);
        }
        if (mTextRight != null) {
            mTextRight.setVisibility(GONE);
        }

    }

    /**
     * 设置右侧图片
     * @param icon 资源id
     */
    public void  setRightIcon(int icon){

        setRightIcon(getResources().getDrawable(icon));
    }

    /**
     * 设置左侧文字
     * @param text 字符串
     */
    public void setLeftText(CharSequence text){
        if (mTextLeft != null){
            mTextLeft.setText(text);
            mTextLeft.setVisibility(VISIBLE);
        }
        if (mImageLeft != null){
            mImageLeft.setVisibility(GONE);
        }
        if (mRlLeft != null){
            mRlLeft.setVisibility(VISIBLE);
        }
        if (mTextBack != null){
            mTextBack.setVisibility(GONE);
        }
        if (mTextBackWhite != null){
            mTextBackWhite.setVisibility(GONE);
        }
    }

    /**
     * 设置左侧文字
     * @param id 资源id
     */
    public void setLeftText(int id){
        setLeftText(getResources().getString(id));
    }


    /**
     * 设置右侧文字
     * @param text 字符串
     */
    public void setRightText(CharSequence text){
        if (mTextRight != null){
            mTextRight.setText(text);
            mTextRight.setVisibility(VISIBLE);
        }
        if (mImageRight != null){
            mImageRight.setVisibility(GONE);
        }

        if (mRlRight != null){
            mRlRight.setVisibility(VISIBLE);
        }

    }


    /**
     * 设置左侧待返回图标的文字
     * @param text 字符串
     */
    public void setLeftBackText(CharSequence text){
        if (mTextLeft != null){
            mTextLeft.setVisibility(GONE);
        }
        if (mImageLeft != null){
            mImageLeft.setVisibility(GONE);
        }
        if (mRlLeft != null){
            mRlLeft.setVisibility(VISIBLE);
        }
        if (isWhiteBg()){
            if (mTextBack != null){
                mTextBack.setVisibility(VISIBLE);
                mTextBack.setText(text);
            }
        }else {
            if (mTextBackWhite != null){
                mTextBackWhite.setVisibility(VISIBLE);
                mTextBackWhite.setText(text);
            }
        }


    }


    /**
     * 隐藏右侧
     */
    public void hideRight(){

        if (mRlRight != null){
            mRlRight.setVisibility(GONE);
        }
    }

    /**
     * 显示右侧
     */
    public void showRight(){

        if (mRlRight != null){
            mRlRight.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置右侧文字
     * @param id 资源id
     */
    public void setRightText(int id){
        setRightText(getResources().getString(id));
    }


    /**
     * 设置标题内容
     * @param resId 资源id
     */

    public void setTitle(int resId) {

        setTitle(getContext().getText(resId));
    }


    /**
     * 设置标题内容
     * @param title 字符串
     */
    @Override
    public void setTitle(CharSequence title) {

        if(mTextTitle !=null) {
            mTextTitle.setText(title);
            showTitleView();
        }

    }


    /**
     * 展示左侧返回
     */
    public  void showLeft(){

        if(mRlLeft !=null){
            mRlLeft.setVisibility(VISIBLE);
        }
        if (isWhiteBg()){
            if (mTextBack != null){
                mTextBack.setVisibility(VISIBLE);
            }
        }else {
            if (mTextBackWhite != null){
                mTextBackWhite.setVisibility(VISIBLE);
            }
        }

        if (mTextLeft != null){
            mTextLeft.setVisibility(GONE);
        }
        if (mImageLeft != null){
            mImageLeft.setVisibility(GONE);
        }


    }

    /**
     * 隐藏左侧内容
     */
    private void hideLeft() {
        if(mRlLeft !=null){
            mRlLeft.setVisibility(GONE);
        }
        if (mTextBack != null){
            mTextBack.setVisibility(GONE);
        }
        if (mTextBackWhite != null){
            mTextBackWhite.setVisibility(GONE);
        }
        if (mTextLeft != null){
            mTextLeft.setVisibility(GONE);
        }
        if (mImageLeft != null){
            mImageLeft.setVisibility(GONE);
        }

    }

    /**
     * 展示标题
     */
    private void showTitleView(){
        if(mTextTitle !=null){
            mTextTitle.setVisibility(VISIBLE);
        }

    }


    /**
     * 隐藏标题
     */
    private void hideTitleView() {
        if (mTextTitle != null){
            mTextTitle.setVisibility(GONE);
        }
    }


    /**
     * 添加右侧点击事件
     * @param listener
     */
    public  void setRightOnClickListener(OnClickListener listener){
        if (mRlRight != null){
            mRlRight.setOnClickListener(listener);
        }
    }

    /**添加左侧点击事件
     * @param listener
     */
    public  void setLeftOnClickListener(OnClickListener listener){

        if (mRlLeft != null){

            mRlLeft.setOnClickListener(listener);
        }

    }


    public TextView getTextRight(){
        return mTextRight;
    }
    public RelativeLayout getRightView(){
        return mRlRight;
    }

    public TextView getTitleView(){
        return mTextTitle;
    }

    public boolean isWhiteBg(){

        return mBgColor==-1;

    }

    public boolean isTransparentBg(){

        return mBgColor==0;

    }

    private   int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}

