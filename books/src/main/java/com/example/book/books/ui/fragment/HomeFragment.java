package com.example.book.books.ui.fragment;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseFragment;
import com.example.book.books.entity.TypeEntity;
import com.example.book.books.model.BooksType;
import com.example.book.books.ui.activity.BookDetailActivity;
import com.example.book.books.ui.activity.BookListActivity;
import com.example.book.books.ui.activity.SearchActivity;
import com.example.book.books.ui.adapter.ItemRVTypeAdapter;
import com.example.book.books.ui.adapter.ItemVPBannerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends SBaseFragment {
    private ViewPager mVpBannerFragHome;
    private RadioGroup mRgBannerFragHome;
    private RelativeLayout mSearchButton;
    private RecyclerView mRvFragHome;

    private List<String> mBannerPath = new ArrayList<>();
    private ItemVPBannerAdapter mItemVPBannerAdapter;
    private boolean mHasTaskCanceled;
    private Timer mTimer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mVpBannerFragHome.setCurrentItem(mVpBannerFragHome.getCurrentItem() + 1);
                    break;
            }
        }
    };

    private List<TypeEntity> mTypeEntities = new ArrayList<>();
    private ItemRVTypeAdapter mMyAdapter;





    @Override
    public int setRootView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        setTitleCenter("胜超网上书店");
        mVpBannerFragHome = (ViewPager) findViewById(R.id.vp_banner_frag_home);
        mRgBannerFragHome = (RadioGroup) findViewById(R.id.rg_banner_frag_home);
        mSearchButton = (RelativeLayout) findViewById(R.id.search_button);

        mRvFragHome = (RecyclerView) findViewById(R.id.rv_frag_home);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SearchActivity.class);
            }
        });
    }

    @Override
    public void initDatas() {
        //模拟数据
        String pathAssets0 = "file:///android_asset/zhineng_b.png";
        String pathAssets1 = "file:///android_asset/baibai_b.png";
        String pathAssets2 = "file:///android_asset/huanlesong_b.png";
        String pathAssets3 = "file:///android_asset/like_b.png";
        mBannerPath.add(pathAssets3);
        mBannerPath.add(pathAssets0);
        mBannerPath.add(pathAssets1);
        mBannerPath.add(pathAssets2);
        mBannerPath.add(pathAssets3);
        mBannerPath.add(pathAssets0);
        showVP(mBannerPath);

        String pathType0 = "file:///android_asset/new.jpg";
        String pathType1 = "file:///android_asset/hot.jpg";
        String pathType2 = "file:///android_asset/child.jpg";

        TypeEntity typeEntity0 = new TypeEntity();
        typeEntity0.setName("新书榜");
        typeEntity0.setPath(pathType0);
        TypeEntity typeEntity1 = new TypeEntity();
        typeEntity1.setName("畅销榜");
        typeEntity1.setPath(pathType1);
        TypeEntity typeEntity2 = new TypeEntity();
        typeEntity2.setName("童书榜");
        typeEntity2.setPath(pathType2);

        mTypeEntities.add(typeEntity0);
        mTypeEntities.add(typeEntity1);
        mTypeEntities.add(typeEntity2);
        mTypeEntities.add(typeEntity0);
        mTypeEntities.add(typeEntity1);
        mTypeEntities.add(typeEntity2);
        mTypeEntities.add(typeEntity0);
        mTypeEntities.add(typeEntity1);
        mTypeEntities.add(typeEntity2);
        showRV();

    }

    private void showRV() {
        mRvFragHome.setLayoutManager(new StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL));
        mMyAdapter = new ItemRVTypeAdapter(mActivitySelf, mTypeEntities);
        mRvFragHome.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(new ItemRVTypeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String name) {
                if ("新书榜".equals(name)) {
                    Toast.makeText(mActivitySelf, "新书", Toast.LENGTH_SHORT).show();
                    gotoActivity(BookListActivity.class,"booktype", BooksType.BOOKS_TYPE_NEW);
                }if ("畅销榜".equals(name)) {
                    Toast.makeText(mActivitySelf, "畅销", Toast.LENGTH_SHORT).show();
                    gotoActivity(BookListActivity.class,"booktype", BooksType.BOOKS_TYPE_HOT);
                }if("童书榜".equals(name)){
                    Toast.makeText(mActivitySelf, "童书", Toast.LENGTH_SHORT).show();
                    gotoActivity(BookListActivity.class,"booktype", BooksType.BOOKS_TYPE_CHILD);
                }
            }
        });
    }

    private void showVP(final List<String> home_banner) {
        mItemVPBannerAdapter = new ItemVPBannerAdapter(home_banner, mActivitySelf);
        mVpBannerFragHome.setAdapter(mItemVPBannerAdapter);
        final RadioButton[] radioButton = {(RadioButton) mRgBannerFragHome.getChildAt(0)};
        mVpBannerFragHome.setCurrentItem(1);
        radioButton[0].setChecked(true);
        final int[] pos = {0};
        mVpBannerFragHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                System.out.println("position = " + position);
            }

            @Override
            public void onPageSelected(int position) {
//                System.out.println("onPageSelected = " + position);
                if (position == 5) {
                    mVpBannerFragHome.setCurrentItem(1, false);//false为取消动画,但是在跳转过来还是有一点不流畅
                }
                if (position == 0) {
                    mVpBannerFragHome.setCurrentItem(4, false);
                }
                if (position == 0) {
                    pos[0] = 3;
                } else if (position == 1) {
                    pos[0] = 0;
                } else if (position == 2) {
                    pos[0] = 1;
                } else if (position == 3) {
                    pos[0] = 2;
                } else if (position == 4) {
                    pos[0] = 3;
                } else if (position == 5) {
                    pos[0] = 0;
                }
                RadioButton radioButton = (RadioButton) mRgBannerFragHome.getChildAt(pos[0]);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_IDLE == state) {
                    //从dragging态回来开启的定时执行
                    startTimer();
                } else if (ViewPager.SCROLL_STATE_DRAGGING == state) {
                    //dragging态取消定时
                    cancelTimer();
                }

            }
        });
        mItemVPBannerAdapter.setOnItemClickedListener(new ItemVPBannerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                if (position==1) {
                    gotoActivity(BookDetailActivity.class, "id", 6);
                }else if(position==2){
                    gotoActivity(BookDetailActivity.class, "id", 7);
                }else if(position==3){
                    gotoActivity(BookDetailActivity.class, "id", 8);
                } else if (position == 4) {
                    gotoActivity(BookDetailActivity.class, "id", 9);
                }
            }
        });
        //最开始的定时执行的，onPause或dragging后，就不再执行了
        mHasTaskCanceled = true;
        startTimer();
    }


    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        //ompause后取消定时
        cancelTimer();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //当前fragment hide与show时 取消和开启定时
        if (hidden) {
            cancelTimer();
        } else {
            startTimer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //从新展现界面时开启的定时执行
        startTimer();
    }

    //取消timer定时的方法
    public void cancelTimer() {
        //只有当联网成功才会展现界面数据 这个时候并没有new出timew 取消做一下判空
        if (mTimer != null) {
            mTimer.cancel();
            mHasTaskCanceled = true;
        }
    }

    //开启timer定时的方法
    public void startTimer() {
        if (mHasTaskCanceled) {
            //取消timer后，要从新new，否则Timer was canceled
            mTimer = new Timer();
            mHasTaskCanceled = false;
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                }
            }, 2000, 2000);
        }
    }
}
