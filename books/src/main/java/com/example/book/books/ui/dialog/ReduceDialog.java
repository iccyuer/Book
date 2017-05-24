package com.example.book.books.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book.books.R;

/**
 * Created by Admin on 2017/5/21.
 */


public class ReduceDialog extends Dialog implements View.OnClickListener {
    private EditText mEdtReducenumDialog;
    private Button mBtConfirmReduceDialog;
    private Button mBtCancelReduceDialog;


    private Context mContext;


    private OnConfirmListener mOnConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    public ReduceDialog(Context context) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_reduce);
        mEdtReducenumDialog = (EditText) findViewById(R.id.edt_reducenum_dialog);
        mBtConfirmReduceDialog = (Button) findViewById(R.id.bt_confirm_reduce_dialog);
        mBtCancelReduceDialog = (Button) findViewById(R.id.bt_cancel_reduce_dialog);
        mBtConfirmReduceDialog.setOnClickListener(this);
        mBtCancelReduceDialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_confirm_reduce_dialog:
                String addnum = mEdtReducenumDialog.getText().toString().trim();
                if (addnum==null||"".equals(addnum)) {
                    Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                int i = Integer.parseInt(addnum);
                if (i == 0) {
                    Toast.makeText(mContext, "数量不能为0", Toast.LENGTH_SHORT).show();
                    return;
                }
                mOnConfirmListener.onConfrim(i);
                break;
            case R.id.bt_cancel_reduce_dialog:
                dismiss();
                break;
        }

    }

    public interface OnConfirmListener {
        void onConfrim(int i);
    }
}
