package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.model.Users;

import java.util.List;

/**
 * Created by Admin on 2017/5/28.
 */

public class ItemRVUserlistAdapter extends RecyclerView.Adapter<UserlistViewHolder> implements View.OnClickListener {
    private Context mContext;
    List<Users> mEntities;

    public ItemRVUserlistAdapter(Context context, List<Users> entities) {
        mContext = context;
        mEntities = entities;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    @Override
    public UserlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_userlist, parent, false);
        UserlistViewHolder typeViewHolder = new UserlistViewHolder(itemView);

        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(UserlistViewHolder holder, int position) {
        holder.mTvUsernameItemRvUserlist.setText("会员名："+mEntities.get(position).getUserName());
        holder.mTvUseraddressItemRvUserlist.setText("地址："+mEntities.get(position).getUserAddress());
        holder.mRlItemRvUserlist.setTag(mEntities.get(position).getUserid());
        holder.mRlItemRvUserlist.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_item_rv_userlist:
                mOnItemClickListener.OnItemClick((Integer) v.getTag());
                break;
        }
    }

}

class UserlistViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout mRlItemRvUserlist;
    public TextView mTvUsernameItemRvUserlist;
    public TextView mTvUseraddressItemRvUserlist;

    public UserlistViewHolder(View itemView) {
        super(itemView);
        mRlItemRvUserlist = (RelativeLayout) itemView.findViewById(R.id.rl_item_rv_userlist);
        mTvUsernameItemRvUserlist = (TextView) itemView.findViewById(R.id.tv_username_item_rv_userlist);
        mTvUseraddressItemRvUserlist = (TextView) itemView.findViewById(R.id.tv_useraddress_item_rv_userlist);


    }
}

