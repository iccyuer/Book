package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.db.CartsDao;
import com.example.book.books.model.Carts;
import com.example.book.books.ui.activity.OrderActivity;
import com.example.book.books.ui.adapter.ItemRVCartsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends SBaseFragment {
    private RecyclerView mRvCartsActivity;
    private ItemRVCartsAdapter mMyAdapter;
    private TextView mTvNobookCartFragment;
    private TextView mTvNologinCartFragment;
    private TextView mTvAllpriceCartFragment;
    private CheckBox mCbSelectallCartFragment;
    private List<Carts> mCartses;
    private TextView mTvSettlementCartFragment;


    //是否为全部选中
    public static boolean isTouchAllChecked;

    public static boolean isAllChecked;

    @Override
    public int setRootView() {
        return R.layout.fragment_cart;
    }


    @Override
    public void initView() {
        mTvNobookCartFragment = (TextView) findViewById(R.id.tv_nobook_cart_fragment);
        mTvNologinCartFragment = (TextView) findViewById(R.id.tv_nologin_cart_fragment);
        mRvCartsActivity = (RecyclerView) findViewById(R.id.rv_carts_fragment);
        mTvAllpriceCartFragment = (TextView) findViewById(R.id.tv_allprice_cart_fragment);
        mCbSelectallCartFragment = (CheckBox) findViewById(R.id.cb_selectall_cart_fragment);
        mTvSettlementCartFragment = (TextView) findViewById(R.id.tv_settlement_cart_fragment);

        mCbSelectallCartFragment.setChecked(false);
        mCbSelectallCartFragment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCartses==null) {
                    Toast.makeText(mActivitySelf, "请先添加购物车再进行操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isChecked) {
                    for (Carts cartse : mCartses) {
                        cartse.setChecked(true);
                    }
                } else {
                    for (Carts cartse : mCartses) {
                        cartse.setChecked(false);
                    }
                }
                float v = countPrice();
                float vjian = (float) (Math.round((v) * 100)) / 100;
                mTvAllpriceCartFragment.setText((vjian) + "");
                int i = countAmount();
                mTvSettlementCartFragment.setText("结算(" + i + ")");
                if (mMyAdapter != null) {
                    mMyAdapter.notifyDataSetChanged();
                }
            }
        });

        mTvSettlementCartFragment.setOnClickListener(new View.OnClickListener() {
            ArrayList<Integer> datas = new ArrayList<Integer>();

            @Override
            public void onClick(View v) {
                if (mCartses==null) {
                    Toast.makeText(mActivitySelf, "请先添加购物车再进行操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                datas.clear();
                for (Carts cartse : mCartses) {
                    if (cartse.isChecked()) {
                        int cartid = cartse.getCartid();
                        datas.add(cartid);
                        System.out.println("cartid = " + cartid);
                    }
                }
                if (datas == null || datas.size() == 0) {
                    Toast.makeText(mActivitySelf, "请选择至少一本图书", Toast.LENGTH_SHORT).show();
                    return;
                }
                float vv = countPrice();
                float vjian = (float) (Math.round((vv) * 100)) / 100;
                gotoActivity(OrderActivity.class, "cartsids", datas, "allprice", vjian);
            }
        });

    }

    @Override
    public void initDatas() {

        mCartses = CartsDao.getCartsDao().getAllCartsByUserId(SBaseApp.onUserId);

        if (SBaseApp.onUserId == 0) {
            mTvNologinCartFragment.setVisibility(View.VISIBLE);
        } else {
            mTvNologinCartFragment.setVisibility(View.INVISIBLE);
            if (mCartses == null || mCartses.size() == 0) {
                mTvNobookCartFragment.setVisibility(View.VISIBLE);
                //适配器总的集合并没清除，影响界面数据
                if (mMyAdapter!=null) {
                    mMyAdapter.mEntities.clear();
                    mMyAdapter.notifyDataSetChanged();
                }
            } else {
                mTvNobookCartFragment.setVisibility(View.INVISIBLE);
                showRV(mCartses);
            }
        }
    }

    private void showRV(List<Carts> cartses) {
//        Logger.i(cartses.toString());
        mRvCartsActivity.setLayoutManager(new LinearLayoutManager(mActivitySelf, LinearLayout.VERTICAL, false));
        mMyAdapter = new ItemRVCartsAdapter(mActivitySelf, mCartses);
//        mRvCartsActivity.setItemViewCacheSize(5+0);
        mRvCartsActivity.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemCheckedChangedListener(new ItemRVCartsAdapter.OnItemCheckedChangedListener() {
            @Override
            public void onCheckedChanged(boolean isChecked, float aprice) {
//                String text = (String) mTvAllpriceCartFragment.getText();
//                float v = Float.parseFloat(text);
//                if (isChecked) {
//                    float vjia = (float)(Math.round((v+aprice)*100))/100;
//                    mTvAllpriceCartFragment.setText((vjia)+"");
//                }else{
//                    float vjian = (float)(Math.round((v-aprice)*100))/100;
//                    mTvAllpriceCartFragment.setText((vjian)+"");
//                }
//                System.out.println("now  "+mTvAllpriceCartFragment.getText());
            }

            @Override
            public void onCheckedChanged1(boolean isChecked, int pos) {
                float v = countPrice();
                float vjian = (float) (Math.round((v) * 100)) / 100;
                mTvAllpriceCartFragment.setText((vjian) + "");

                int i = countAmount();
                mTvSettlementCartFragment.setText("结算(" + i + ")");
            }
        });

    }

    /**
     * 计算当前购物车所有选中商品的总价格
     *
     * @return
     */
    private float countPrice() {
        float v = 0;
        for (Carts carts : mCartses) {
            if (carts.isChecked()) {
                v += carts.getPrice() * carts.getAmount();
            }
        }
        return v;
    }

    /**
     * 计算当前购物车所有选中商品的总数量
     *
     * @return
     */
    private int countAmount() {
        int v = 0;
        for (Carts carts : mCartses) {
            if (carts.isChecked()) {
                v++;
            }
        }
        return v;
    }


    @Override
    public boolean isNeedTitle() {
        return true;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            System.out.println("CartFragment.onHiddenChanged");
            initView();
            initDatas();
        } else {
            System.out.println("cart隐藏了");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("CartFragment.onResume");
        initView();
        initDatas();
    }
}
