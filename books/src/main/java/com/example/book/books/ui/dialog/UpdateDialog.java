package com.example.book.books.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book.books.R;
import com.example.book.books.db.BooksDao;
import com.example.book.books.db.PurchaseDao;
import com.example.book.books.model.Books;
import com.example.book.books.model.Purchase;

/**
 * Created by Admin on 2017/5/22.
 */


public class UpdateDialog extends Dialog implements View.OnClickListener {
    private EditText mEdtNameUpdateDialog;
    private EditText mEdtIntroUpdateDialog;
    private EditText mEdtPriceUpdateDialog;
    private EditText mEdtMarketpriceUpdateDialog;
    private EditText mEdtPressUpdateDialog;
    private EditText mEdtStockbalanceUpdateDialog;
    private Button mBtConfirmUpdateDialog;
    private Button mBtCancelUpdateDialog;
    private EditText mEdtAuthorUpdateDialog;

    private Context mContext;
    private Books mBooks;

    private OnConfirmListener mOnConfirmListener;
    private final int mStockBalance;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    public UpdateDialog(Context context, Books books) {
        super(context);
        mContext = context;
        mBooks = books;
        mStockBalance = mBooks.getStockBalance();
        setContentView(R.layout.dialog_update);

        mEdtAuthorUpdateDialog = (EditText) findViewById(R.id.edt_author_update_dialog);

        mEdtNameUpdateDialog = (EditText) findViewById(R.id.edt_name_update_dialog);
        mEdtIntroUpdateDialog = (EditText) findViewById(R.id.edt_intro_update_dialog);
        mEdtPriceUpdateDialog = (EditText) findViewById(R.id.edt_price_update_dialog);
        mEdtMarketpriceUpdateDialog = (EditText) findViewById(R.id.edt_marketprice_update_dialog);
        mEdtPressUpdateDialog = (EditText) findViewById(R.id.edt_press_update_dialog);
        mEdtStockbalanceUpdateDialog = (EditText) findViewById(R.id.edt_stockbalance_update_dialog);
        mBtConfirmUpdateDialog = (Button) findViewById(R.id.bt_confirm_update_dialog);
        mBtCancelUpdateDialog = (Button) findViewById(R.id.bt_cancel_update_dialog);

        mEdtAuthorUpdateDialog.setText(mBooks.getAuthor());
        mEdtNameUpdateDialog.setText(mBooks.getName());
        mEdtIntroUpdateDialog.setText(mBooks.getIntro());
        mEdtPriceUpdateDialog.setText(mBooks.getPrice() + "");
        mEdtMarketpriceUpdateDialog.setText(mBooks.getMarketPrice() + "");
        mEdtPressUpdateDialog.setText(mBooks.getPress());
        mEdtStockbalanceUpdateDialog.setText(mStockBalance + "");


        mBtConfirmUpdateDialog.setOnClickListener(this);
        mBtCancelUpdateDialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_confirm_update_dialog:
                String name = mEdtNameUpdateDialog.getText().toString().trim();
                String intro = mEdtIntroUpdateDialog.getText().toString().trim();
                String price = mEdtPriceUpdateDialog.getText().toString().trim();
                String marketprice = mEdtMarketpriceUpdateDialog.getText().toString().trim();
                String press = mEdtPressUpdateDialog.getText().toString().trim();
                String stockbalance = mEdtStockbalanceUpdateDialog.getText().toString().trim();
                String author = mEdtAuthorUpdateDialog.getText().toString().trim();

                if (name==null||"".equals(name)||intro==null||"".equals(intro)||price==null||"".equals(price)
                        ||marketprice==null||"".equals(marketprice)||press==null||"".equals(press)
                        ||stockbalance==null||"".equals(stockbalance)||author==null||"".equals(author)) {
                    Toast.makeText(mContext, "不可以输入空数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                mBooks.setName(name);
                mBooks.setIntro(intro);
                float v1 = Float.parseFloat(price);
                mBooks.setPrice(v1);
                float v2 = Float.parseFloat(marketprice);
                mBooks.setMarketPrice(v2);
                mBooks.setPress(press);
                int i = Integer.parseInt(stockbalance);
                mBooks.setStockBalance(i);

                BooksDao.getBooksDao().updateBooksInfo(mBooks);

                //架设调整过库存量，则添加到进货记录里面
                if (i!=mStockBalance) {
                    Purchase purchase=new Purchase() ;
                    purchase.setBookid(mBooks.getBookid());
                    purchase.setBookName(name);
                    purchase.setBookPic(mBooks.getPic());
//                    purchase.setNew(true);
                    purchase.setPurchaseStock(i);
                    purchase.setPurchaseTime(System.currentTimeMillis());
                    purchase.setStockNow(i);
                    PurchaseDao.getPurchaseDao().addPurchase(purchase);
                }

                mOnConfirmListener.onConfrim();
                dismiss();
                break;
            case R.id.bt_cancel_update_dialog:
                dismiss();
                break;
        }

    }

    public interface OnConfirmListener {
        void onConfrim();
    }
}
