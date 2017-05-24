package com.example.book.books.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.base.SBaseActivity;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.PurchaseDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Purchase;
import com.example.book.books.ui.adapter.ItemVPPreAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddBooksActivity extends SBaseActivity implements View.OnClickListener {
    private ViewPager mVpAddbooksActivity;
    private List<String> mPics;
    private EditText mEdtNameAddbooksActivity;
    private EditText mEdtIntroAddbooksActivity;
    private EditText mEdtPriceAddbooksActivity;
    private EditText mEdtMarketpriceAddbooksActivity;
    private EditText mEdtPressAddbooksActivity;
    private EditText mEdtStockAddbooksActivity;
    private TextView mTvAddAddbooksActivity;
    private TextView mTvResetAddbooksActivity;
    private EditText mEdtAuthorAddbooksActivity;

    private int mNowPic=0;
    private int mBooktype;

    @Override
    public int setRootView() {
        return R.layout.activity_add_books;
    }

    @Override
    public void initView() {
        setTitleCenter("请输入商品的信息");
        mVpAddbooksActivity = (ViewPager) findViewById(R.id.vp_addbooks_activity);
        mEdtNameAddbooksActivity = (EditText) findViewById(R.id.edt_name_addbooks_activity);
        mEdtIntroAddbooksActivity = (EditText) findViewById(R.id.edt_intro_addbooks_activity);
        mEdtPriceAddbooksActivity = (EditText) findViewById(R.id.edt_price_addbooks_activity);
        mEdtMarketpriceAddbooksActivity = (EditText) findViewById(R.id.edt_marketprice_addbooks_activity);
        mEdtPressAddbooksActivity = (EditText) findViewById(R.id.edt_press_addbooks_activity);
        mEdtStockAddbooksActivity = (EditText) findViewById(R.id.edt_stock_addbooks_activity);
        mTvAddAddbooksActivity = (TextView) findViewById(R.id.tv_add_addbooks_activity);
        mTvResetAddbooksActivity = (TextView) findViewById(R.id.tv_reset_addbooks_activity);
        mEdtAuthorAddbooksActivity = (EditText) findViewById(R.id.edt_author_addbooks_activity);

        mTvAddAddbooksActivity.setOnClickListener(this);
        mTvResetAddbooksActivity.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        mBooktype = intent.getIntExtra("booktype", 0);

        String pathType0 = "file:///android_asset/pre1.jpg";
        String pathType1 = "file:///android_asset/pre2.jpg";
        String pathType2 = "file:///android_asset/pre3.jpg";
        String pathType3 = "file:///android_asset/pre4.jpg";
        mPics = new ArrayList<>();
        mPics.add(pathType0);
        mPics.add(pathType1);
        mPics.add(pathType2);
        mPics.add(pathType3);
        showVP();
    }

    private void showVP() {
        ItemVPPreAdapter itemVPBookDetailAdapter = new ItemVPPreAdapter(mPics, this);
        mVpAddbooksActivity.setAdapter(itemVPBookDetailAdapter);
        mVpAddbooksActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("position = [" + position + "]");
                mNowPic=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_add_addbooks_activity:
                String name = mEdtNameAddbooksActivity.getText().toString().trim();
                String intro = mEdtIntroAddbooksActivity.getText().toString().trim();
                String price = mEdtPriceAddbooksActivity.getText().toString().trim();
                String marketprice = mEdtMarketpriceAddbooksActivity.getText().toString().trim();
                String press = mEdtPressAddbooksActivity.getText().toString().trim();
                String stock = mEdtStockAddbooksActivity.getText().toString().trim();
                String author = mEdtAuthorAddbooksActivity.getText().toString().trim();

                if (name==null||"".equals(name)||intro==null||"".equals(intro)||price==null||"".equals(price)
                        ||marketprice==null||"".equals(marketprice)||press==null||"".equals(press)
                        ||stock==null||"".equals(stock)||author==null||"".equals(author)) {
                    Toast.makeText(mActivitySelf, "不可以输入空数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                Books books=new Books() ;
                books.setName(name);
                books.setAuthor(author);
                books.setIntro(intro);
                float v1 = Float.parseFloat(price);
                books.setPrice(v1);
                float v2 = Float.parseFloat(marketprice);
                books.setMarketPrice(v2);
                books.setPress(press);
                int i = Integer.parseInt(stock);
                books.setStockBalance(i);
                books.setType(mBooktype);
                books.setPic(mPics.get(mNowPic));//-----------------------------
                //添加书籍
                BooksDao.getBooksDao().addBook(books);
                int bookid = books.getBookid();
                //添加入库记录
                Purchase purchase=new Purchase() ;
                purchase.setBookid(bookid);
                purchase.setBookName(name);
                purchase.setBookPic(mPics.get(mNowPic));
                purchase.setNew(true);
                purchase.setPurchaseStock(i);
                purchase.setPurchaseTime(System.currentTimeMillis());
                purchase.setStockNow(i);
                PurchaseDao.getPurchaseDao().addPurchase(purchase);

                Toast.makeText(mActivitySelf, "添加书籍成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_reset_addbooks_activity:
                mEdtNameAddbooksActivity.setText("");
                mEdtIntroAddbooksActivity.setText("");
                mEdtPriceAddbooksActivity.setText("");
                mEdtMarketpriceAddbooksActivity.setText("");
                mEdtPressAddbooksActivity.setText("");
                mEdtStockAddbooksActivity.setText("");
                mEdtAuthorAddbooksActivity.setText("");
                Toast.makeText(mActivitySelf, "重置数据成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
