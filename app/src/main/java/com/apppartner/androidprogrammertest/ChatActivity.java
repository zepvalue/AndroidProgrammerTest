package com.apppartner.androidprogrammertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.adapters.ChatsArrayAdapter;
import com.apppartner.androidprogrammertest.models.ChatData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "ActionBarActivity";
    private ArrayList<ChatData> chatDataArrayList;
    private ChatsArrayAdapter chatsArrayAdapter;
    private ListView listView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        listView = (ListView) findViewById(R.id.listView);
        chatDataArrayList = new ArrayList<>();

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titleTextView = (TextView) toolbarTop.findViewById(R.id.textViewTitle);
        titleTextView.setText("Chat");

        CustomTextView customTextView = new CustomTextView(this);
        customTextView.setViewCustomFont(titleTextView, "fonts/Jelloween - Machinato ExtraLight.ttf", this);

        Typeface usernameFont = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato.ttf");
        Typeface passFont = Typeface.createFromAsset(getAssets(), "fonts/Jelloween - Machinato Light.ttf");

        Intent intentFromMain = getIntent();
        String response = intentFromMain.getStringExtra("extraResponse");

        if (response.equals("Not Logged"))
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(ChatActivity.this);
            alert.setTitle("Error");
            alert.setMessage("Please login first...");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }
            });
            alert.show();
        }
        else
        {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            mainIntent.putExtra("extraResponse", "Logged");
        }

            try {
                String chatFileData = loadChatFile();
                JSONObject jsonData = new JSONObject(chatFileData);
                JSONArray jsonArray = jsonData.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ChatData chatData = new ChatData(jsonObject);
                    chatDataArrayList.add(chatData);
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, e);
            }

        chatsArrayAdapter = new ChatsArrayAdapter(this, chatDataArrayList, usernameFont , passFont);
        listView.setAdapter(chatsArrayAdapter);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String loadChatFile() throws IOException
    {
        InputStream inputStream = getResources().openRawResource(R.raw.chat_data);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String receiveString;
        StringBuilder stringBuilder = new StringBuilder();

        while ((receiveString = bufferedReader.readLine()) != null )
        {
            stringBuilder.append(receiveString);
            stringBuilder.append("\n");
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();


        return stringBuilder.toString();
    }

}
