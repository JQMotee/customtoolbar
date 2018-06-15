package com.clz.view.demo;

import android.os.Bundle;
import android.view.View;

import com.clz.view.customtoolbar.CustomToolBar;

public class TransparentTitleActivity extends BaseActivity {

    private CustomToolBar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_title);
        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);

        mToolbar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
