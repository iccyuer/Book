package com.example.book.books.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.book.books.R;

/**
 * Created by Admin on 2017/5/21.
 */

public class CheckDialog extends Dialog implements View.OnClickListener {
    private EditText mEdtAdminidDialog;
    private EditText mEdtAdminpwdDialog;
    private Button mBtConfirmDialog;
    private Button mBtCancelDialog;

    private OnConfirmListener mOnConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    public CheckDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_check);
        mEdtAdminidDialog = (EditText) findViewById(R.id.edt_adminid_dialog);
        mEdtAdminpwdDialog = (EditText) findViewById(R.id.edt_adminpwd_dialog);
        mBtConfirmDialog = (Button) findViewById(R.id.bt_confirm_check_dialog);
        mBtCancelDialog = (Button) findViewById(R.id.bt_cancel_check_dialog);
        mBtConfirmDialog.setOnClickListener(this);
        mBtCancelDialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bt_confirm_check_dialog:
                String adminid = mEdtAdminidDialog.getText().toString().trim();
                String adminpwd = mEdtAdminpwdDialog.getText().toString().trim();
                mOnConfirmListener.onConfrim(adminid,adminpwd);
                break;
            case R.id.bt_cancel_check_dialog:
                dismiss();
                break;
        }

    }

    public interface  OnConfirmListener{
        void onConfrim(String adminid,String adminpwd);
    }
}
