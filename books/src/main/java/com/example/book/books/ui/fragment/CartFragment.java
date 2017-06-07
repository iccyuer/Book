package com.example.book.books.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseApp;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.CartsDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Carts;
import com.example.book.books.ui.activity.LoginActivity;
import com.example.book.books.ui.activity.OrderActivity;
import com.example.book.books.ui.adapter.ItemRVCartsAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends SBaseFragment implements View.OnClickListener {
    private RecyclerView mRvCartsActivity;
    private ItemRVCartsAdapter mMyAdapter;
    private TextView mTvAllpriceCartFragment;
    private CheckBox mCbSelectallCartFragment;
    private List<Carts> mCartses;
    private TextView mTvSettlementCartFragment;
    private LinearLayout mLlBottom;
    private LinearLayout mLlNobookCartFragment;
    private Button mBtDostrollCartFragment;
    private LinearLayout mLlNologinCartFragment;
    private Button mBtDologinCartFragment;
    private LinearLayout mLlSettlementCartFragment;
    private LinearLayout mLlDeleteCartFragment;
    private TextView mTvDeleteCartFragment;


    @Override
    public int setRootView() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        setTitleCenter("购物车");
        setTitleRight("修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                String name = tv.getText().toString().trim();
                if ("修改".equals(name)) {
                    tv.setText("完成");
                    mLlSettlementCartFragment.setVisibility(View.GONE);
                    mLlDeleteCartFragment.setVisibility(View.VISIBLE);
                } else if ("完成".equals(name)) {
                    tv.setText("修改");
                    mTvAllpriceCartFragment.setText("0.0");
                    mLlSettlementCartFragment.setVisibility(View.VISIBLE);
                    mLlDeleteCartFragment.setVisibility(View.GONE);
                }
            }
        });
        mRvCartsActivity = (RecyclerView) findViewById(R.id.rv_carts_fragment);
        mTvAllpriceCartFragment = (TextView) findViewById(R.id.tv_allprice_cart_fragment);
        mCbSelectallCartFragment = (CheckBox) findViewById(R.id.cb_selectall_cart_fragment);
        mTvSettlementCartFragment = (TextView) findViewById(R.id.tv_settlement_cart_fragment);
        mLlBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        mLlNobookCartFragment = (LinearLayout) findViewById(R.id.ll_nobook_cart_fragment);
        mBtDostrollCartFragment = (Button) findViewById(R.id.bt_dostroll_cart_fragment);
        mLlNologinCartFragment = (LinearLayout) findViewById(R.id.ll_nologin_cart_fragment);
        mBtDologinCartFragment = (Button) findViewById(R.id.bt_dologin_cart_fragment);

        mLlSettlementCartFragment = (LinearLayout) findViewById(R.id.ll_settlement_cart_fragment);
        mLlDeleteCartFragment = (LinearLayout) findViewById(R.id.ll_delete_cart_fragment);
        mTvDeleteCartFragment = (TextView) findViewById(R.id.tv_delete_cart_fragment);

        mTvDeleteCartFragment.setOnClickListener(this);
        mBtDologinCartFragment.setOnClickListener(this);
        mBtDostrollCartFragment.setOnClickListener(this);

        mCbSelectallCartFragment.setChecked(false);
        mCbSelectallCartFragment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCartses == null || mCartses.size() == 0) {
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
                mTvDeleteCartFragment.setText("删除（" + i + "）");
                if (mMyAdapter != null) {
//                    mCartses=CartsDao.getCartsDao().getAllCartsByUserId(SBaseApp.onUserId);
//                    Logger.i(mCartses.toString());
//                    mMyAdapter.mEntities.clear();
//                    mMyAdapter.mEntities=CartsDao.getCartsDao().getAllCartsByUserId(SBaseApp.onUserId);
//                    Logger.i(mMyAdapter.mEntities.toString());
//                    Logger.i(mCartses.toString());
                    mMyAdapter.notifyDataSetChanged();
//                    initDatas();
                }
            }
        });

        mTvSettlementCartFragment.setOnClickListener(new View.OnClickListener() {
            ArrayList<Integer> datas = new ArrayList<Integer>();

            @Override
            public void onClick(View v) {
                if (mCartses == null || mCartses.size() == 0) {
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

                for (Integer cartsid : datas) {
                    Carts carts = CartsDao.getCartsDao().getCartsByCartId(cartsid);
                    Books books = BooksDao.getBooksDao().getBooksById(carts.getBookid());
                    if (books.getStockBalance()<carts.getAmount()) {
                        Toast.makeText(mActivitySelf, books.getName()+"  库存不足", Toast.LENGTH_SHORT).show();
                        //这里只要发现一本书库存不足就会提示，
                        return;
                    }
                }
                gotoActivity(OrderActivity.class, "cartsids", datas, "allprice", vjian);
            }
        });

    }

    @Override
    public void initDatas() {
        mCartses = CartsDao.getCartsDao().getAllCartsByUserId(SBaseApp.onUserId);
//        Logger.i(mCartses.toString());
        if (SBaseApp.onUserId == 0) {
            mLlNologinCartFragment.setVisibility(View.VISIBLE);
            mLlBottom.setVisibility(View.GONE);
            mLlNobookCartFragment.setVisibility(View.GONE);
            //不该显示rv时我是把 数据清空，刷新适配器--但是盖住了点击监听了
//            if (mMyAdapter != null) {
//                mMyAdapter.mEntities.clear();
//                mMyAdapter.notifyDataSetChanged();
//            }
            mRvCartsActivity.setVisibility(View.GONE);
            setTitleRightGone();
        } else {
            mLlNologinCartFragment.setVisibility(View.GONE);
            if (mCartses == null || mCartses.size() == 0) {
                mLlNobookCartFragment.setVisibility(View.VISIBLE);
                mLlBottom.setVisibility(View.GONE);
                //适配器总的集合并没清除，影响界面数据
//                if (mMyAdapter != null) {
//                    mMyAdapter.mEntities.clear();
//                    mMyAdapter.notifyDataSetChanged();
//                }
                mRvCartsActivity.setVisibility(View.GONE);
                setTitleRightGone();
            } else {
                setTitleRightVISIBLE();
                mLlNobookCartFragment.setVisibility(View.GONE);
                mLlBottom.setVisibility(View.VISIBLE);
                mRvCartsActivity.setVisibility(View.VISIBLE);
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
                mTvDeleteCartFragment.setText("删除（" + i + "）");
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
     * 计算当前购物车所有选中商品的总数目数量
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

    private void deleteCheckedBooks() {
        for (Carts carts : mCartses) {
            if (carts.isChecked()) {
                CartsDao.getCartsDao().deleteCarts(carts);
            }
//            initView();
            initDatas();
        }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_dologin_cart_fragment:
                gotoActivity(LoginActivity.class);
                break;
            case R.id.bt_dostroll_cart_fragment:
                Logger.i("cccccccccccccccccccccccc");
                mOncheckedListener.onChecked("home");
                break;
            case R.id.tv_delete_cart_fragment:
                deleteCheckedBooks();
                break;
        }
    }


    private OncheckedListener mOncheckedListener;

    public void setOncheckedListener(OncheckedListener oncheckedListener) {
        mOncheckedListener = oncheckedListener;
    }

    public interface OncheckedListener {
        void onChecked(String frag);
    }
}
