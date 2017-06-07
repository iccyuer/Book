package com.example.book.books.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book.books.R;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.CartsDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Carts;

import java.util.List;

/**
 * Created by Admin on 2017/5/15.
 */


public class ItemRVCartsAdapter extends RecyclerView.Adapter<CartsViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context mContext;
    public List<Carts> mEntities;


    public ItemRVCartsAdapter(Context context, List<Carts> entities) {
        mContext = context;
        mEntities = entities;
    }

    @Override
    public CartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_rv_carts, parent, false);
        System.out.println("itemView = " + itemView);
        CartsViewHolder typeViewHolder =  new CartsViewHolder(itemView);
        return typeViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartsViewHolder holder, final int position) {
        Glide.with(mContext).load(mEntities.get(position).getPic()).into(holder.mImgvBookItemRvCarts);
        Glide.with(mContext).load(mEntities.get(position).getPic()).into(holder.mImgvBookHintItemRvCarts);
        holder.mTvNameItemRvCarts.setText(mEntities.get(position).getName());
        holder.mTvPriceItemRvCarts.setText(mEntities.get(position).getPrice()+"");
        System.out.println(mEntities.get(position).getPrice());
        holder.mTvMarketpriceItemRvCarts.setText(mEntities.get(position).getMarketPrice()+"");
        holder.mTvAmountItemRvCarts.setText("x "+mEntities.get(position).getAmount());

        holder.mCbItemRvCarts.setChecked(mEntities.get(position).isChecked());

        holder.mCbItemRvCarts.setOnCheckedChangeListener(this);
        holder.mCbItemRvCarts.setTag(position);

        holder.mCbItemRvCarts.setOnClickListener(this);

        holder.mEdtAmountHintItemRvCarts.setText(mEntities.get(position).getAmount()+"");

        holder.mImgvModifyItemRvCarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mRlHintEditItemRvCarts.setVisibility(View.VISIBLE);
                holder.mRlItemRvCarts.setVisibility(View.GONE);
            }
        });

        holder.mBtConfirmHintItemRvCarts.setTag(mEntities.get(position).getCartid());
        holder.mBtConfirmHintItemRvCarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) v.getTag();
                String amount = holder.mEdtAmountHintItemRvCarts.getText().toString().trim();
                int i = Integer.parseInt(amount);
                Carts carts = CartsDao.getCartsDao().getCartsByCartId(id);
//                carts.setAmount(i);
                Books books = BooksDao.getBooksDao().getBooksById(carts.getBookid());
                if (books.getStockBalance()<i) {
                    Toast.makeText(mContext, "当前书籍库存不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.mRlItemRvCarts.setVisibility(View.VISIBLE);
                holder.mRlHintEditItemRvCarts.setVisibility(View.GONE);
                CartsDao.getCartsDao().updateCartsAmount(carts,i);
                mEntities.get(position).setAmount(i);
                holder.mTvAmountItemRvCarts.setText("x "+i);
            }
        });

        holder.mTvAddHintItemRvCarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = holder.mEdtAmountHintItemRvCarts.getText().toString().trim();
                int i = Integer.parseInt(amount);
                i++;
//                System.out.println("vv = " + vv);
                holder.mEdtAmountHintItemRvCarts.setText(i+"");
//                Toast.makeText(mContext, "add", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mTvReduceHintItemRvCarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = holder.mEdtAmountHintItemRvCarts.getText().toString().trim();
                int i = Integer.parseInt(amount);
                if (i==1) {
                    return;
                }
//                String vv =(i-1)+"";
                i--;
                holder.mEdtAmountHintItemRvCarts.setText(i+"");
            }
        });

        //去焦点
        holder.mImgvBookHintItemRvCarts.setFocusable(true);
        holder.mImgvBookHintItemRvCarts.setFocusableInTouchMode(true);
        holder.mImgvBookHintItemRvCarts.requestFocus();

    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int id);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.cb_item_rv_carts:
                int pos= (int) v.getTag();
                CheckBox cb= (CheckBox) v;
                Carts carts = mEntities.get(pos);
                boolean ischecked=cb.isChecked();
                carts.setChecked(ischecked);
                mOnItemCheckedChangedListener.onCheckedChanged1(ischecked,pos);
                break;
        }
    }

    private OnItemCheckedChangedListener mOnItemCheckedChangedListener;

    public void setOnItemCheckedChangedListener(OnItemCheckedChangedListener onItemCheckedChangedListener) {
        mOnItemCheckedChangedListener = onItemCheckedChangedListener;
    }

    public interface OnItemCheckedChangedListener{
        void onCheckedChanged(boolean isChecked,float aprice);
        void onCheckedChanged1(boolean isChecked,int pos);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int pos= (int) buttonView.getTag();
            Carts carts = mEntities.get(pos);
            float v = carts.getPrice() * carts.getAmount();
            mOnItemCheckedChangedListener.onCheckedChanged(isChecked, v);
//            mOnItemCheckedChangedListener.onCheckedChanged1(isChecked,pos);
    }

}

class CartsViewHolder extends RecyclerView.ViewHolder {
    public  CheckBox mCbItemRvCarts;
    public RelativeLayout mRlItemRvCarts;
    public  ImageView mImgvBookItemRvCarts;
    public  TextView mTvNameItemRvCarts;
    public  TextView mTvPriceItemRvCarts;
    public  TextView mTvMarketpriceItemRvCarts;
    public  TextView mTvAmountItemRvCarts;
    public  ImageView mImgvModifyItemRvCarts;

    public RelativeLayout mRlHintEditItemRvCarts;
    public ImageView mImgvBookHintItemRvCarts;
    public TextView mTvReduceHintItemRvCarts;
    public EditText mEdtAmountHintItemRvCarts;
    public TextView mTvAddHintItemRvCarts;
    public Button mBtConfirmHintItemRvCarts;


    public CartsViewHolder(View itemView) {
        super(itemView);
        mCbItemRvCarts = (CheckBox) itemView.findViewById(R.id.cb_item_rv_carts);
        mRlItemRvCarts = (RelativeLayout) itemView.findViewById(R.id.rl_item_rv_carts);
        mImgvBookItemRvCarts = (ImageView) itemView.findViewById(R.id.imgv_book_item_rv_carts);
        mTvNameItemRvCarts = (TextView) itemView.findViewById(R.id.tv_name_item_rv_carts);
        mTvPriceItemRvCarts = (TextView) itemView.findViewById(R.id.tv_price_item_rv_carts);
        mTvMarketpriceItemRvCarts = (TextView) itemView.findViewById(R.id.tv_marketprice_item_rv_carts);
        mTvAmountItemRvCarts = (TextView) itemView.findViewById(R.id.tv_amount_item_rv_carts);
        mImgvModifyItemRvCarts = (ImageView) itemView.findViewById(R.id.imgv_modify_item_rv_carts);

        mRlHintEditItemRvCarts = (RelativeLayout) itemView.findViewById(R.id.rl_hint_edit_item_rv_carts);
        mImgvBookHintItemRvCarts = (ImageView) itemView.findViewById(R.id.imgv_book_hint_item_rv_carts);
        mTvReduceHintItemRvCarts = (TextView) itemView.findViewById(R.id.tv_reduce_hint_item_rv_carts);
        mEdtAmountHintItemRvCarts = (EditText) itemView.findViewById(R.id.edt_amount_hint_item_rv_carts);
        mTvAddHintItemRvCarts = (TextView) itemView.findViewById(R.id.tv_add_hint_item_rv_carts);
        mBtConfirmHintItemRvCarts = (Button) itemView.findViewById(R.id.bt_confirm_hint_item_rv_carts);

    }
}
