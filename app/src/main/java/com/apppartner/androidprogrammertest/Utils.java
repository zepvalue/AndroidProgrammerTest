package com.apppartner.androidprogrammertest;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zepvalue on 10/1/2016.
 */

public class Utils {

    public static void setFont(TextView textView, String fontSource, Context context)
    {
        Typeface fontToolbar = Typeface.createFromAsset(context.getAssets(), fontSource);
        textView.setTypeface(fontToolbar);
    }

    public static void setFont(EditText editText, String fontSource, Context context)
    {
        Typeface fontToolbar = Typeface.createFromAsset(context.getAssets(), fontSource);
        editText.setTypeface(fontToolbar);
    }
}
