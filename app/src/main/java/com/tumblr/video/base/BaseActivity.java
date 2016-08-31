package com.tumblr.video.base;

import com.tumblr.video.R;
import com.tumblr.video.util.PageLoadingHelper;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseActivity extends AppCompatActivity {
    protected PageLoadingHelper mPageLoadingHelper;
    protected boolean mIsInitRefresh = true;
    protected Toolbar mToolbar;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_activity_layout, null);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        view.addView(getContentView(savedInstanceState), 2);

        setContentView(view);
        mContext = this;

        setSupportActionBar(mToolbar);
        if(isNavigationButtonEnable()) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(isNavigationButtonEnable()) {
                        onBackPressed();
                    }
                }
            });
        } else {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        mPageLoadingHelper = new PageLoadingHelper(view);
        mPageLoadingHelper.setOnLoadingClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickToRefresh();
            }
        });

        if(mIsInitRefresh) {
            clickToRefresh();
        }
    }

    public boolean isNavigationButtonEnable() {

        return true;
    }

    /**
     * Fragment数据视图
     * @param savedInstanceState
     * @return
     */
    public abstract View getContentView(@Nullable Bundle savedInstanceState);

    /**
     * 点击刷新加载
     */
    private void clickToRefresh() {

        startRefresh();
        refreshData();
    }

    /**
     * 初始化数据
     */
    public abstract void refreshData();

    /**
     * 开始加载数据
     */
    private void startRefresh() {

        mPageLoadingHelper.startRefresh();
    }

    /**
     * 加载失败
     */
    public void refreshFailed() {

        mPageLoadingHelper.refreshFailed();
    }

    /**
     * 加载成功
     */
    public void refreshSuccess() {

        mPageLoadingHelper.refreshSuccess();
    }
}
