package com.apppartner.androidprogrammertest.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData>
{
    private Typeface tfUserName;
    private Typeface tfMessage;

    public ChatsArrayAdapter(Context context, List<ChatData> objects,  Typeface tfUserName, Typeface tfMessage )
    {
        super(context, 0, objects);
        this.tfUserName = tfUserName;
        this.tfMessage = tfMessage;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        ChatCell chatCell = new ChatCell();
        ImageLoader imageLoader = ImageLoader.getInstance();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);

        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.usernameTextView.setTypeface(tfUserName);
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        chatCell.usernameTextView.setTypeface(tfMessage);
        chatCell.avatarImageView = (ImageView) convertView.findViewById(R.id.avatarImageView);
        ChatData chatData = getItem(position);

        chatCell.usernameTextView.setText(chatData.username);
        chatCell.messageTextView.setText(chatData.message);
        imageLoader.displayImage(chatData.avatarURL, chatCell.avatarImageView);

        return convertView;
    }

    private static class ChatCell
    {
        TextView usernameTextView;
        TextView messageTextView;
        ImageView avatarImageView;
    }
}
