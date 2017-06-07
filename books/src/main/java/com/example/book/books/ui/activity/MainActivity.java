package com.example.book.books.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.ui.fragment.CartFragment;
import com.example.book.books.ui.fragment.HomeFragment;
import com.example.book.books.ui.fragment.MyStoreFragment;
import com.example.book.books.ui.fragment.WelcomeFragment;

public class MainActivity extends SBaseActivity implements View.OnClickListener {
    private FrameLayout mLayoutChangeFragMainActivity;
    private LinearLayout mNaviMainActivity;
    private RelativeLayout mHomeMainActivity;
    private ImageView mImgvHome;
    private RelativeLayout mTypeMainActivity;
    private ImageView mImgvType;
    private RelativeLayout mCartMainActivity;
    private ImageView mImgvCart;
    private RelativeLayout mMystoreMainActivity;
    private ImageView mImgvMystore;


    private FrameLayout mLayoutFragWelcome;

    private TextView mTvHome;
    private TextView mTvType;
    private TextView mTvCart;
    private TextView mTvMystore;

    private HomeFragment mHomeFragment = new HomeFragment();
//    private TypeFragment mTypeFragment = new TypeFragment();
    private CartFragment mCartFragment = new CartFragment();
    private MyStoreFragment mMyStoreFragment = new MyStoreFragment();
    private SBaseFragment[] mShopBaseFragments = {mHomeFragment, mCartFragment, mMyStoreFragment};


    private WelcomeFragment mWelcomeFragment=new WelcomeFragment() ;
    private static final int REMOVE_WELCOME = 110;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REMOVE_WELCOME:
//                    removeFrag(mWelcomeFragment);
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//撤销全屏显示
                    //为了欢迎页不受影响，把FrameLayout父布局设置为白背景，欢迎页消失时，把布局隐藏了
//                    mLayoutFragWelcome.setVisibility(View.GONE);
                    break;
                case 1:
                    break;
            }

        }
    };


    @Override
    public int setRootView() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏显示
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        mLayoutChangeFragMainActivity = (FrameLayout) findViewById(R.id.layout_change_frag_main_activity);
        mNaviMainActivity = (LinearLayout) findViewById(R.id.navi_main_activity);
        mHomeMainActivity = (RelativeLayout) findViewById(R.id.home_main_activity);
        mImgvHome = (ImageView) findViewById(R.id.imgv_home);
//        mTypeMainActivity = (RelativeLayout) findViewById(R.id.type_main_activity);
//        mImgvType = (ImageView) findViewById(R.id.imgv_type);
        mCartMainActivity = (RelativeLayout) findViewById(R.id.cart_main_activity);
        mImgvCart = (ImageView) findViewById(R.id.imgv_cart);
        mMystoreMainActivity = (RelativeLayout) findViewById(R.id.mystore_main_activity);
        mImgvMystore = (ImageView) findViewById(R.id.imgv_mystore);
        mLayoutFragWelcome = (FrameLayout) findViewById(R.id.layout_frag_welcome);


//        mTvHome = (TextView) findViewById(R.id.tv_home);
//        mTvType = (TextView) findViewById(R.id.tv_type);
//        mTvCart = (TextView) findViewById(R.id.tv_cart);
//        mTvMystore = (TextView) findViewById(R.id.tv_mystore);

        //调到homefrag
        mCartFragment.setOncheckedListener(new CartFragment.OncheckedListener() {
            @Override
            public void onChecked(String frag) {
                if ("home".equals(frag)) {
                    mHomeMainActivity.performClick();
                }
            }
        });

        mHomeMainActivity.setOnClickListener(this);
//        mHomeMainActivity.setOnTouchListener(this);
//        mImgvHome.setOnClickListener(this);
//        mTypeMainActivity.setOnClickListener(this);
//        mTypeMainActivity.setOnTouchListener(this);
//        mImgvType.setOnClickListener(this);
        mCartMainActivity.setOnClickListener(this);
//        mCartMainActivity.setOnTouchListener(this);
//        mImgvCart.setOnClickListener(this);
        mMystoreMainActivity.setOnClickListener(this);
//        mMystoreMainActivity.setOnTouchListener(this);
//        mImgvMystore.setOnClickListener(this);

//        addFrag(R.id.layout_frag_welcome, mWelcomeFragment);
//        mHandler.sendEmptyMessageDelayed(REMOVE_WELCOME, 500);
        addFrag(R.id.layout_change_frag_main_activity, mHomeFragment);
    }

    @Override
    public void initDatas() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        System.out.println("MainActivity.onResume");
        super.onResume();
        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");
        System.out.println("fragment = " + fragment);
        if ("carts".equals(fragment)) {
            changeHideShow(mCartFragment, 1);
        }
//        if ("home".equals(fragment)) {
//            changeHideShow(mHomeFragment, 0);
//        }
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }


    @Override
    public void onClick(View v) {
        SBaseFragment sBaseFragmentNow = null;
        int posNow = 0;
        int id = v.getId();
        switch (id) {
            case R.id.home_main_activity:
                sBaseFragmentNow = mHomeFragment;
                posNow = 0;
                break;

//            case R.id.type_main_activity:
//                sBaseFragmentNow = mTypeFragment;
//                posNow = 1;
//                break;

            case R.id.cart_main_activity:
                sBaseFragmentNow = mCartFragment;
                posNow = 1;
                break;

            case R.id.mystore_main_activity:
                sBaseFragmentNow = mMyStoreFragment;
                posNow = 2;
                break;
        }
        changeHideShow(sBaseFragmentNow, posNow);

    }

    //添加和隐藏fragment
    private void changeHideShow(SBaseFragment sBaseFragmentNow, int pos) {
        if (!sBaseFragmentNow.isAdded()) {
            addFrag(R.id.layout_change_frag_main_activity, sBaseFragmentNow);
        }
        //初始默认加载home_fragment   其他都置为hide了，一定要show
        showFrag(sBaseFragmentNow);
        for (SBaseFragment shopBaseFragment : mShopBaseFragments) {
            if (shopBaseFragment != sBaseFragmentNow) {
                hideFrag(shopBaseFragment);
            }
        }

        for (int i = 0; i < 3; i++) {
            int colorNormal = getResources().getColor(R.color.colorNormal);
            int colorPress = getResources().getColor(R.color.colorPressed);

            if (i == 0) {
                ImageView imgv = (ImageView) mHomeMainActivity.getChildAt(0);
                TextView tv = (TextView) mHomeMainActivity.getChildAt(1);
                if (i == pos) {
                    imgv.setImageResource(R.drawable.navigation_homebutton_pressed);
//                    Drawable drawable = getResources().getDrawable(R.drawable.navigation_homebutton_pressed);
//                    imgv.setBackground(drawable);
                    tv.setTextColor(colorPress);
                } else {
                    imgv.setImageResource(R.drawable.navigation_homebutton_normal);
//                    Drawable drawable = getResources().getDrawable(R.drawable.navigation_homebutton_normal);
//                    imgv.setBackground(drawable);
                    tv.setTextColor(colorNormal);
                }
            }
//            if (i == 1) {
//                ImageView imgv = (ImageView) mTypeMainActivity.getChildAt(0);
//                TextView tv = (TextView) mTypeMainActivity.getChildAt(1);
//                if (i == pos) {
//                    imgv.setImageResource(R.drawable.navigation_typebutton_pressed);
//                    tv.setTextColor(colorPress);
//                } else {
//                    imgv.setImageResource(R.drawable.navigation_typebutton_normal);
//                    tv.setTextColor(colorNormal);
//                }
//            }
            if (i == 1) {
                ImageView imgv = (ImageView) mCartMainActivity.getChildAt(0);
                TextView tv = (TextView) mCartMainActivity.getChildAt(1);
                if (i == pos) {
                    imgv.setImageResource(R.drawable.navigation_cartbutton_pressed);
                    tv.setTextColor(colorPress);
                } else {
                    imgv.setImageResource(R.drawable.navigation_cartbutton_normal);
                    tv.setTextColor(colorNormal);
                }
            }
            if (i == 2) {
                ImageView imgv = (ImageView) mMystoreMainActivity.getChildAt(0);
                TextView tv = (TextView) mMystoreMainActivity.getChildAt(1);
                if (i == pos) {
                    imgv.setImageResource(R.drawable.navigation_mystorebutton_pressed);
                    tv.setTextColor(colorPress);
                } else {
                    imgv.setImageResource(R.drawable.navigation_mystorebutton_normal);
                    tv.setTextColor(colorNormal);
                }
            }
        }
    }
}
