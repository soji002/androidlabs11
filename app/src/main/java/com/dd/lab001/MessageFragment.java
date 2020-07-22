package com.dd.lab001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MessageFragment extends Fragment {


    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private String msg;
    private int position;
    private boolean type;

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);
        msg = dataFromActivity.getString(ChatRoomActivity.ITEM_MSG);
        type = dataFromActivity.getBoolean(ChatRoomActivity.ITEM_TYPE);
        position = dataFromActivity.getInt(ChatRoomActivity.ITEM_POSITION);

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_message, container, false);

        //show the id
        TextView messageID = (TextView) result.findViewById(R.id.message_id);
        messageID.setText("ID = " + id);

        //show the message:
        TextView messageText = (TextView) result.findViewById(R.id.messageText);
        messageText.setText("text=" + msg);

        TextView messageType = (TextView) result.findViewById(R.id.messageType);
        messageType.setText("isSend=" + type);

        TextView messagePosition = (TextView) result.findViewById(R.id.messagePosition);
        messagePosition.setText("Position=" + position);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button) result.findViewById(R.id.deleteMessage);
        deleteButton.setOnClickListener(clk -> {

            if (isTablet) { //both the list and details are on the screen:
                ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                //parent=new ChatRoomActivity();
                //parent.deleteMessageId((int)id); //this deletes the item and updates the list
                //Log.e("Tablet id",id);
                parent.deleteMessageId(id, position); //this deletes the item and updates the list

                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else //You are only looking at the details, you need to go back to the previous list page
            {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(ChatRoomActivity.ITEM_ID, dataFromActivity.getLong(ChatRoomActivity.ITEM_ID));
                backToFragmentExample.putExtra(ChatRoomActivity.ITEM_POSITION, dataFromActivity.getInt(ChatRoomActivity.ITEM_POSITION));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });
        return result;
    }
}