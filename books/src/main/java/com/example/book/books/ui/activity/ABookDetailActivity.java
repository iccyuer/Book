package com.example.book.books.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.PurchaseDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Purchase;
import com.example.book.books.ui.adapter.ItemVPBookDetailAdapter;
import com.example.book.books.ui.dialog.AddDialog;
import com.example.book.books.ui.dialog.ReduceDialog;
import com.example.book.books.ui.dialog.UpdateDialog;

import java.util.ArrayList;
import java.util.List;

public class ABookDetailActivity extends SBaseActivity implements View.OnClickListener {
    private ViewPager mVpDetailAbook;
    private TextView mTvNameDetailAbook;
    private TextView mTvIntroDetailAbook;
    private TextView mTvPriceDetailAbook;
    private TextView mTvMarketDetailAbook;
    private TextView mTvAuthorDetailAbook;
    private TextView mTvPressDetailAbook;
    private TextView mTvStockalanceDetailAbook;
    private WebView mVbDetailAbook;
    private TextView mTvAddAbookDetail;
    private TextView mTvReduceAbookDetail;
    private Books mBooks;
    private AddDialog mAddDialog;
    private ReduceDialog mReduceDialog;
    private UpdateDialog mUpdateDialog;


    @Override
    public int setRootView() {
        return R.layout.activity_abook_detail;
    }

    @Override
    public void initView() {
        mVpDetailAbook = (ViewPager) findViewById(R.id.vp_detail_abook);
        mTvNameDetailAbook = (TextView) findViewById(R.id.tv_name_detail_abook);
        mTvIntroDetailAbook = (TextView) findViewById(R.id.tv_intro_detail_abook);
        mTvPriceDetailAbook = (TextView) findViewById(R.id.tv_price_detail_abook);
        mTvMarketDetailAbook = (TextView) findViewById(R.id.tv_market_detail_abook);
        mTvAuthorDetailAbook = (TextView) findViewById(R.id.tv_author_detail_abook);
        mTvPressDetailAbook = (TextView) findViewById(R.id.tv_press_detail_abook);
        mTvStockalanceDetailAbook = (TextView) findViewById(R.id.tv_stockalance_detail_abook);
        mVbDetailAbook = (WebView) findViewById(R.id.vb_detail_abook);
        mTvAddAbookDetail = (TextView) findViewById(R.id.tv_add_abook_detail);
        mTvReduceAbookDetail = (TextView) findViewById(R.id.tv_reduce_abook_detail);

        mTvAddAbookDetail.setOnClickListener(this);
        mTvReduceAbookDetail.setOnClickListener(this);

        mAddDialog = new AddDialog(this);
        mAddDialog.setOnConfirmListener(new AddDialog.OnConfirmListener() {
            @Override
            public void onConfrim(int i) {
                //更新库存数量
                BooksDao.getBooksDao().updateBooksStockBalance(mBooks,i);
                //更新记录添加到进货订单里面
                Purchase purchase=new Purchase() ;
                purchase.setBookid(mBooks.getBookid());
                purchase.setPurchaseAmount(i);
                purchase.setPurchaseTime(System.currentTimeMillis());
                purchase.setBookPic(mBooks.getPic());
                purchase.setBookName(mBooks.getName());
                purchase.setStockNow(mBooks.getStockBalance());
                PurchaseDao.getPurchaseDao().addPurchase(purchase);
                Toast.makeText(mActivitySelf, "添加库存成功", Toast.LENGTH_SHORT).show();
                mTvStockalanceDetailAbook.setText("库存数量："+mBooks.getStockBalance());
                mAddDialog.dismiss();
            }
        });
        mReduceDialog = new ReduceDialog(this);
        mReduceDialog.setOnConfirmListener(new ReduceDialog.OnConfirmListener() {
            @Override
            public void onConfrim(int i) {
                BooksDao.getBooksDao().updateBooksStockBalance(mBooks,-i);
                Purchase purchase=new Purchase() ;
                purchase.setBookid(mBooks.getBookid());
                purchase.setPurchaseAmount(-i);
                purchase.setPurchaseTime(System.currentTimeMillis());
                purchase.setBookPic(mBooks.getPic());
                purchase.setBookName(mBooks.getName());
                purchase.setStockNow(mBooks.getStockBalance());
                PurchaseDao.getPurchaseDao().addPurchase(purchase);
                Toast.makeText(mActivitySelf, "减少库存成功", Toast.LENGTH_SHORT).show();
                mTvStockalanceDetailAbook.setText("库存数量："+mBooks.getStockBalance());
                mReduceDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_add_abook_detail:
                mAddDialog.show();
                break;
            case R.id.tv_reduce_abook_detail:
                mReduceDialog.show();
                break;
        }
    }

    @Override
    public void initDatas() {
        int id = getIntent().getIntExtra("id", 0);
        mBooks = BooksDao.getBooksDao().getBooksById(id);

        setTitleCenter(mBooks.getName());
        setTitleRight("修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivitySelf, "hehe", Toast.LENGTH_SHORT).show();
                mUpdateDialog.show();
            }
        });
        initDialog();
        showDetail(mBooks);
        showVP(mBooks);
        showWV();
    }

    private void initDialog() {
        mUpdateDialog = new UpdateDialog(this, mBooks);
        mUpdateDialog.setOnConfirmListener(new UpdateDialog.OnConfirmListener() {
            @Override
            public void onConfrim() {
                Toast.makeText(mActivitySelf, "修改成功", Toast.LENGTH_SHORT).show();
                initDatas();
            }
        });
    }


    private void showDetail(Books books) {
        String name = books.getName();
        String intro = books.getIntro();
        float price = books.getPrice();
        float marketPrice = books.getMarketPrice();
        String author = books.getAuthor();
        String press = books.getPress();
        int stockBalance = books.getStockBalance();
        mTvNameDetailAbook.setText(name);
        mTvIntroDetailAbook.setText(intro);
        mTvPriceDetailAbook.setText(price + "");
        mTvMarketDetailAbook.setText(marketPrice + "");
        mTvAuthorDetailAbook.setText(author);
        mTvPressDetailAbook.setText(press);
        mTvStockalanceDetailAbook.setText("库存数量："+stockBalance);

    }

    private void showWV() {
        String loadUrl2 = "file:///android_asset/milu.html";
        mVbDetailAbook.loadUrl(loadUrl2);
        mVbDetailAbook.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                return true;
            }
        });

        mVbDetailAbook.getSettings().setJavaScriptEnabled(true);
//        mVbDetailBook.addJavascriptInterface(new CallJS(this),"Androiddashi");
        //是否支持缓存
        mVbDetailAbook.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        mWv.getSettings().setSupportZoom(true);
    }

    private void showVP(Books books) {
        final List<String> mPics = new ArrayList<>();
        mPics.add(books.getPic());
        mPics.add(books.getPic());
        mPics.add(books.getPic());
        mPics.add(books.getPic());
        final ItemVPBookDetailAdapter itemVPBookDetailAdapter = new ItemVPBookDetailAdapter(mPics, this);
        mVpDetailAbook.setAdapter(itemVPBookDetailAdapter);

        mVpDetailAbook.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currPosition = 0; // 当前滑动到了哪一页
            boolean canJump = false;
            boolean canLeft = true;

            boolean isObjAnmatitor = true;
            boolean isObjAnmatitor2 = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == (mPics.size() - 1)) {
                    if (positionOffset > 0.35) {
                        canJump = true;
                        if (itemVPBookDetailAdapter.mArrowImage != null && itemVPBookDetailAdapter.mSlideText != null) {
                            if (isObjAnmatitor) {
                                isObjAnmatitor = false;
                                ObjectAnimator animator = ObjectAnimator.ofFloat(itemVPBookDetailAdapter.mArrowImage, "rotation", 0f, 180f);
                                animator.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        itemVPBookDetailAdapter.mSlideText.setText("松开跳到详情");
                                        isObjAnmatitor2 = true;
                                    }
                                });
                                animator.setDuration(500).start();
                            }
                        }
                    } else if (positionOffset <= 0.35 && positionOffset > 0) {
                        canJump = false;
                        if (itemVPBookDetailAdapter.mArrowImage != null && itemVPBookDetailAdapter.mSlideText != null) {
                            if (isObjAnmatitor2) {
                                isObjAnmatitor2 = false;
                                ObjectAnimator animator = ObjectAnimator.ofFloat(itemVPBookDetailAdapter.mArrowImage, "rotation", 180f, 360f);
                                animator.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        itemVPBookDetailAdapter.mSlideText.setText("继续滑动跳到详情");
                                        isObjAnmatitor = true;
                                    }
                                });
                                animator.setDuration(500).start();
                            }
                        }
                    }
                    canLeft = false;
                } else {
                    canLeft = true;
                }
            }

            @Override
            public void onPageSelected(int position) {
                currPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (currPosition == (mPics.size() - 1) && !canLeft) {
                    if (state == ViewPager.SCROLL_STATE_SETTLING) {

                        if (canJump) {
                            Toast.makeText(mActivitySelf, "没有更多了", Toast.LENGTH_SHORT).show();
                        }

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                // 在handler里调用setCurrentItem才有效
                                mVpDetailAbook.setCurrentItem(mPics.size() - 1);
                            }
                        });

                    }
                }
            }
        });
    }

}
