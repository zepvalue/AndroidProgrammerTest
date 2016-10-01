package com.apppartner.androidprogrammertest;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zepvalue on 9/30/2016.
 */

public class CustomTextView extends TextView {


    public CustomTextView(Context context) {
        super(context);
    }

    public void setViewCustomFont( TextView customTextView, String fontSource, Context context) {

//        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
//        titleTextView = (TextView) toolbarTop.findViewById(R.id.textViewTitle);
        Typeface fontToolbar = Typeface.createFromAsset(context.getAssets(), fontSource);
        customTextView.setTypeface(fontToolbar);
    }

    public void setEditTextViewCustomFont(EditText customEditView, String fontSource, Context context) {

//        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
//        titleTextView = (TextView) toolbarTop.findViewById(R.id.textViewTitle);
        Typeface fontToolbar = Typeface.createFromAsset(context.getAssets(), fontSource);
        customEditView.setTypeface(fontToolbar);
    }


}
