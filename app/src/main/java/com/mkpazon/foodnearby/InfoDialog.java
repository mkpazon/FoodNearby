package com.mkpazon.foodnearby;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * Created by mkpazon on 7/7/15.
 * -=Bitbitbitbit=-
 */
public class InfoDialog {

    public static final int BUTTON_POSITIVE = DialogInterface.BUTTON_POSITIVE;

    private InfoDialog() {
    }

    public static Dialog newInstance(Context context, String title,
                                     String message,
                                     final InfoDialogOnClickListener infoDialogOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message);

        OnClickListener onClickListener = new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (infoDialogOnClickListener != null) {
                    infoDialogOnClickListener.onClick();
                }
            }
        };

        builder.setPositiveButton(context.getString(R.string.ok), onClickListener);
        return builder.create();
    }

    public interface InfoDialogOnClickListener {
        void onClick();
    }
}
