package com.sky.beautiful.Rrefreshlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @Time : 2017/12/21 no 下午3:04
 * @USER : vvguoliang
 * @File : BGARecyclerViewHolder.java
 * @Software: Android Studio
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ***┏┓   ┏ ┓
 * **┏┛┻━━━┛ ┻┓
 * **┃   ☃   ┃
 * **┃ ┳┛  ┗┳ ┃
 * **┃    ┻   ┃
 * **┗━┓    ┏━┛
 * ****┃    ┗━━━┓
 * ****┃ 神兽保佑 ┣┓
 * ****┃ 永无BUG！┏┛
 * ****┗┓┓┏━┳┓┏┛┏┛
 * ******┃┫┫  ┃┫┫
 * ******┗┻┛  ┗┻┛
 */

public class BGARecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

    protected Context mContext;
    protected BGAOnRVItemClickListener mOnRVItemClickListener;
    protected BGAOnRVItemLongClickListener mOnRVItemLongClickListener;
    protected BGAViewHolderHelper mViewHolderHelper;
    protected RecyclerView mRecyclerView;
    protected BGARecyclerViewAdapter mRecyclerViewAdapter;

    public BGARecyclerViewHolder(BGARecyclerViewAdapter recyclerViewAdapter, RecyclerView recyclerView, View itemView, BGAOnRVItemClickListener onRVItemClickListener, BGAOnRVItemLongClickListener onRVItemLongClickListener) {
        super(itemView);
        mRecyclerViewAdapter = recyclerViewAdapter;
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mOnRVItemClickListener = onRVItemClickListener;
        mOnRVItemLongClickListener = onRVItemLongClickListener;
        itemView.setOnClickListener(new BGAOnNoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (v.getId() == BGARecyclerViewHolder.this.itemView.getId() && null != mOnRVItemClickListener) {
                    mOnRVItemClickListener.onRVItemClick(mRecyclerView, v, getAdapterPositionWrapper());
                }
            }
        });
        itemView.setOnLongClickListener(this);

        mViewHolderHelper = new BGAViewHolderHelper(mRecyclerView, this);
    }

    public BGAViewHolderHelper getViewHolderHelper() {
        return mViewHolderHelper;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == this.itemView.getId() && null != mOnRVItemLongClickListener) {
            return mOnRVItemLongClickListener.onRVItemLongClick(mRecyclerView, v, getAdapterPositionWrapper());
        }
        return false;
    }

    public int getAdapterPositionWrapper() {
        if (mRecyclerViewAdapter.getHeadersCount() > 0) {
            return getAdapterPosition() - mRecyclerViewAdapter.getHeadersCount();
        } else {
            return getAdapterPosition();
        }
    }
}
