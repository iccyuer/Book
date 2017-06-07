package com.example.book.books.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.CartsDao;
import com.example.book.books.db.CollectsDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Carts;
import com.example.book.books.model.Collects;
import com.example.book.books.ui.adapter.ItemVPBookDetailAdapter;
import com.example.book.books.ui.views.BezierEvalutor;

import java.util.ArrayList;
import java.util.List;

import static com.example.book.books.base.SBaseApp.onUserId;


public class BookDetailActivity extends SBaseActivity {
    private ViewPager mVpDetailBook;
    private TextView mTvNameDetailBook;
    private TextView mTvIntroDetailBook;
    private TextView mTvPriceDetailBook;
    private TextView mTvMarketDetailBook;
    private TextView mTvAuthorDetailBook;
    private TextView mTvPressDetailBook;
    private TextView mTvStockalanceDetailBook;
    private WebView mVbDetailBook;
    private TextView mTvPutcartBookDetail;
    private TextView mTvShowPointBookDetail;
    private TextView mTvAnimPointBookDetail;
    private View.OnClickListener mOnClickListener;
    private Books mBooks;
    private ImageView mImgvCollectBookDetail;
    private ImageView mImgvCartBookDetail;
    private TextView mTvDobuyBookDetail;

    //是否检查过当前用户放入过购物车
    private boolean isCheckedCarts;

    //当前书籍是否在购物车中
    private boolean isInCarts;

    //是否检查过当前购物车是否为空
    private boolean isCheckedEmpty;

    //是否是空购物车
    private boolean isEmptyCarts;

    //是否检查过当前数据收藏过
    private boolean isCheckedCollect;

    //当前书籍是否被收藏
    private boolean isCollected;

    @Override
    public int setRootView() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void initView() {
        mVpDetailBook = (ViewPager) findViewById(R.id.vp_detail_book);

        mTvNameDetailBook = (TextView) findViewById(R.id.tv_name_detail_book);
        mTvIntroDetailBook = (TextView) findViewById(R.id.tv_intro_detail_book);
        mTvPriceDetailBook = (TextView) findViewById(R.id.tv_price_detail_book);
        mTvMarketDetailBook = (TextView) findViewById(R.id.tv_market_detail_book);
        mTvAuthorDetailBook = (TextView) findViewById(R.id.tv_author_detail_book);
        mTvPressDetailBook = (TextView) findViewById(R.id.tv_press_detail_book);

        mTvDobuyBookDetail = (TextView) findViewById(R.id.tv_dobuy_book_detail);

        mTvStockalanceDetailBook = (TextView) findViewById(R.id.tv_stockalance_detail_book);

        mVbDetailBook = (WebView) findViewById(R.id.vb_detail_book);
        mTvPutcartBookDetail = (TextView) findViewById(R.id.tv_putcart_book_detail);

        mTvShowPointBookDetail = (TextView) findViewById(R.id.tv_show_point_book_detail);
        mTvAnimPointBookDetail = (TextView) findViewById(R.id.tv_anim_point_book_detail);
        mImgvCollectBookDetail = (ImageView) findViewById(R.id.imgv_collect_book_detail);
        mImgvCartBookDetail = (ImageView) findViewById(R.id.imgv_cart_book_detail);

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserId == 0) {
                    gotoActivity(LoginActivity.class);
                    return;
                }
                addCartDB(false);
            }
        };
        regesiterBTaddCartLis();

        mTvDobuyBookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onUserId == 0) {
                    gotoActivity(LoginActivity.class);
                    return;
                }
//                addCartDB(true);
                int bookid = mBooks.getBookid();
                String name = mBooks.getName();
                float price = mBooks.getPrice();
                float marketPrice = mBooks.getMarketPrice();
                String pic = mBooks.getPic();

                if (isInCarts) {
                    Toast.makeText(mActivitySelf, "购物车已经存在当前书籍，请选择购买", Toast.LENGTH_SHORT).show();
                    gotoActivity(MainActivity.class,"fragment","carts");
                }else{
                    isInCarts=true;
                    //初始添加之前判断库存是不是最少为1
                    if (mBooks.getStockBalance()<1) {
                        Toast.makeText(mActivitySelf, "当前书籍库存不足", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Carts carts1 = new Carts();
                    carts1.setUserid(onUserId);
                    carts1.setBookid(bookid);
                    carts1.setName(name);
                    carts1.setPrice(price);
                    carts1.setPic(pic);
                    carts1.setMarketPrice(marketPrice);
                    carts1.setAmount(1);
                    CartsDao.getCartsDao().addCarts(carts1);
                    ArrayList<Integer> datas = new ArrayList<Integer>();
                    datas.add(carts1.getCartid());
                    gotoActivity(OrderActivity.class, "cartsids", datas, "allprice", price);
                }
            }
        });

    }

    public void gotoCart(View view){
        gotoActivity(MainActivity.class,"fragment","carts");
    }

    public void collect(View view) {
        if (onUserId == 0) {
            gotoActivity(LoginActivity.class);
            return;
        }
        int bookid = mBooks.getBookid();
        if (!isCollected) {
            isCollected=true;
            Collects collects=new Collects() ;
            collects.setUserId(onUserId);
            collects.setBookId(bookid);
            CollectsDao.getCollectsDao().addCollects(collects);
            Glide.with(this).load(R.mipmap.book_list_details_collected).into(mImgvCollectBookDetail);
            Toast.makeText(mActivitySelf, "收藏成功", Toast.LENGTH_SHORT).show();
        }else{
            isCollected=false;
            CollectsDao.getCollectsDao().deleteCollectByBookId(bookid);
            Glide.with(this).load(R.mipmap.book_list_details_collect).into(mImgvCollectBookDetail);
            Toast.makeText(mActivitySelf, "取消收藏成功", Toast.LENGTH_SHORT).show();
        }

    }

    private void addCartDB(boolean isbuy) {
        int bookid = mBooks.getBookid();
        String name = mBooks.getName();
        float price = mBooks.getPrice();
        float marketPrice = mBooks.getMarketPrice();
        String pic = mBooks.getPic();

        if (!isInCarts) {
//            Logger.i("当前用户没有添加过，添加");
            isInCarts=true;
            //初始添加之前判断库存是不是最少为1
            if (mBooks.getStockBalance()<1) {
                Toast.makeText(mActivitySelf, "当前书籍库存不足", Toast.LENGTH_SHORT).show();
                return;
            }
            Carts carts1 = new Carts();
            carts1.setUserid(onUserId);
            carts1.setBookid(bookid);
            carts1.setName(name);
            carts1.setPrice(price);
            carts1.setPic(pic);
            carts1.setMarketPrice(marketPrice);
            carts1.setAmount(1);
            CartsDao.getCartsDao().addCarts(carts1);
        } else {
            Carts carts = CartsDao.getCartsDao().getCartsByBookIdAndUserId(bookid, onUserId);
            int amount = carts.getAmount();
            amount++;
            //增加数量是判断
            if (amount>mBooks.getStockBalance()) {
                Toast.makeText(mActivitySelf, "当前书籍库存不足", Toast.LENGTH_SHORT).show();
                return;
            }
            CartsDao.getCartsDao().updateCartsAmount(carts, amount);
        }
        Toast.makeText(mActivitySelf, "加入购物车成功", Toast.LENGTH_SHORT).show();
        if (!isbuy) {
            addCartAnim();
        }
        updateStockBalance();
    }

    private void updateStockBalance() {
        //添加成功后从界面上更新库存量（不是真实减少库存--并不影响其他用户加入购物车）
        int bookid = mBooks.getBookid();
        Carts carts = CartsDao.getCartsDao().getCartsByBookIdAndUserId(bookid, onUserId);
        if (carts!=null) {
            int amount = carts.getAmount();
            int stockBalance=mBooks.getStockBalance();
            mTvStockalanceDetailBook.setText("库存数量："+(stockBalance-amount));
        }else{
            int stockBalance=mBooks.getStockBalance();
            mTvStockalanceDetailBook.setText("库存数量："+(stockBalance));
        }
    }

    private void addCartAnim() {
        unRegesiterBTaddCartLis();

        final float startX = mTvAnimPointBookDetail.getX();
        final float startY = mTvAnimPointBookDetail.getY();

//        System.out.println("startX = " + startX);
//        System.out.println("startY = " + startY);

        PointF pointF0 = new PointF(startX, startY);

        float endX = startX / 3.3f;
        float endY = startY + 30;

//        System.out.println("endX = " + endX);
//        System.out.println("endY = " + endY);

        PointF pointF1 = new PointF((startX + endX) / 2, startY - 250);
        PointF pointF2 = new PointF(endX, endY);

        BezierEvalutor mBezierEvalutor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mBezierEvalutor = new BezierEvalutor(pointF1);
        }

        final ValueAnimator animatordyd = ValueAnimator.ofFloat(1.0f, 2.0f, 1.0f);
        animatordyd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float scale = (Float) animation.getAnimatedValue();
                mTvShowPointBookDetail.setScaleX(scale);
                mTvShowPointBookDetail.setScaleY(scale);
            }
        });
        animatordyd.setDuration(400);

        ValueAnimator animator = ValueAnimator.ofObject(mBezierEvalutor, pointF0, pointF2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();    //通过addUpdateListener监听事件实时获得从mBezierEvalutor估值器对象evalute方法实时计算出最新点的坐标  。
//                System.out.println("pointF = " + pointF.x);
                mTvAnimPointBookDetail.setX(pointF.x);//然后去更新该ImageView的X,Y坐标
                mTvAnimPointBookDetail.setY(pointF.y);
//                mTvAnimPointBookDetail.setAlpha(1-animation.getAnimatedFraction());//getAnimatedFraction()就是mBezierEvalutor估值器对象中evaluate方法t即时间因子,从0~1变化,所以爱心透明度应该是从1~0变化正好到了顶部，t变为1，透明度变为0，即爱心消失
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTvAnimPointBookDetail.setX(startX);//然后去更新该ImageView的X,Y坐标
                mTvAnimPointBookDetail.setY(startY);
                regesiterBTaddCartLis();
                mTvAnimPointBookDetail.setVisibility(View.INVISIBLE);
                animatordyd.start();
                showCarts();
            }
        });
        animator.setTarget(mTvShowPointBookDetail);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.start();
        mTvAnimPointBookDetail.setVisibility(View.VISIBLE);

    }

    private void unRegesiterBTaddCartLis() {
        mTvPutcartBookDetail.setOnClickListener(null);//解注册
    }

    private void regesiterBTaddCartLis() {
        mTvPutcartBookDetail.setOnClickListener(mOnClickListener);//注册上
    }


    @Override
    public void initDatas() {
        int id = getIntent().getIntExtra("id", 0);
        mBooks = BooksDao.getBooksDao().getBooksById(id);

        setTitleCenter(mBooks.getName());

        showDetail(mBooks);
        showVP(mBooks);
        showWV();

        showCollect();
        showCarts();

    }

    private void showCarts() {
        int bookid = mBooks.getBookid();
        if (!isCheckedCarts) {
            isCheckedCarts = true;
            List<Integer> usersCartsBookIds = CartsDao.getCartsDao().getUsersCartsBookIds(onUserId);
            if (usersCartsBookIds != null) {
                for (Integer usersCartsBookId : usersCartsBookIds) {
                    if (usersCartsBookId == bookid) {
                        //当前用户添加过这本书到购物车
                        isInCarts = true;
                        break;
                    }
                }
            }else{
                isInCarts=false;
            }
        }
        Integer amount = CartsDao.getCartsDao().getCartsAllAmounts(onUserId);
        if (amount!=0) {
            if (amount>99) {
                mTvShowPointBookDetail.setVisibility(View.VISIBLE);
                mTvShowPointBookDetail.setText("99+");
            }else{
                mTvShowPointBookDetail.setVisibility(View.VISIBLE);
                mTvShowPointBookDetail.setText(amount+"");
//                mTvShowPointBookDetail.setText("99+");
            }
        }else{
            isEmptyCarts=true;
        }

//        if (!isEmptyCarts) {
////            Carts carts = CartsDao.getCartsDao().getCartsByBookIdAndUserId(bookid, onUserId);
////            int amount = carts.getAmount();
//            Integer amount = CartsDao.getCartsDao().getCartsAllAmounts(onUserId);
//            mTvShowPointBookDetail.setVisibility(View.VISIBLE);
//            mTvShowPointBookDetail.setText(amount+"");
//        }
    }

    private void showCollect() {
        int bookid = mBooks.getBookid();
        if (!isCheckedCollect) {
            isCheckedCollect=true;
            List<Integer> usersCollectBookIds = CollectsDao.getCollectsDao().getCollectsBookIds(onUserId);
            if (usersCollectBookIds != null) {
                for (Integer usersCollectBookId : usersCollectBookIds) {
                    System.out.println("usersCollectBookId = " + usersCollectBookId);
                    if (bookid == usersCollectBookId) {
                        isCollected=true;
                        Glide.with(this).load(R.mipmap.book_list_details_collected).into(mImgvCollectBookDetail);
                        break;
                    }
                }
            }else{
                isCollected=false;
            }
        }
    }

    private void showWV() {
        String loadUrl2 = "file:///android_asset/milu.html";
        mVbDetailBook.loadUrl(loadUrl2);
        mVbDetailBook.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                return true;
            }
        });

        mVbDetailBook.getSettings().setJavaScriptEnabled(true);
//        mVbDetailBook.addJavascriptInterface(new CallJS(this),"Androiddashi");
        //是否支持缓存
        mVbDetailBook.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        mWv.getSettings().setSupportZoom(true);
    }

    private void showDetail(Books books) {
        String name = books.getName();
        String intro = books.getIntro();
        float price = books.getPrice();
        float marketPrice = books.getMarketPrice();
        String author = books.getAuthor();
        String press = books.getPress();
        int stockBalance = books.getStockBalance();
        mTvNameDetailBook.setText(name);
        mTvIntroDetailBook.setText(intro);
        mTvPriceDetailBook.setText(price + "");
        mTvMarketDetailBook.setText(marketPrice + "");
        mTvAuthorDetailBook.setText(author);
        mTvPressDetailBook.setText(press);
//        mTvStockalanceDetailBook.setText("库存数量："+stockBalance);
        updateStockBalance();
    }

    private void showVP(Books books) {
        final List<String> mPics = new ArrayList<>();
        mPics.add(books.getPic());
        mPics.add(books.getPic());
        mPics.add(books.getPic());
        mPics.add(books.getPic());
        final ItemVPBookDetailAdapter itemVPBookDetailAdapter = new ItemVPBookDetailAdapter(mPics, this);
        mVpDetailBook.setAdapter(itemVPBookDetailAdapter);

        mVpDetailBook.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
                                mVpDetailBook.setCurrentItem(mPics.size() - 1);
                            }
                        });

                    }
                }
            }
        });
    }
}
