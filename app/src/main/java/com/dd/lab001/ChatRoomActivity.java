package com.dd.lab001;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<Message> listMessage = new ArrayList<>();
    Button sendBtn;
    Button receiveBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        listView = (ListView)findViewById(R.id.ListView);
        editText = (EditText)findViewById(R.id.ChatEditText);
        sendBtn = (Button)findViewById(R.id.SendBtn);
        receiveBtn = (Button)findViewById(R.id.ReceiveBtn);





        sendBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            Message model = new Message(message, true);
            listMessage.add(model);
            editText.setText("");
            ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(adt);
            adt.notifyDataSetChanged();
        });


        receiveBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            Message model = new Message(message, false);
            listMessage.add(model);
            editText.setText("");
            ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(adt);
            adt.notifyDataSetChanged();
        });





        Log.d("ChatRoomActivity","onCreate");
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                new AlertDialog.Builder(ChatRoomActivity.this)
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Are you sure to Delete that");
                builder.setMessage("The selected row is "+ id + "which was selected");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
//

                        Message item = listMessage.get(position);
                        listMessage.remove(position);
                        ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
                        listView.setAdapter(adt);
                        adt.notifyDataSetChanged();
                    }
                });
              builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                    }
                });//set icon
//set title
//set message
//set positive button
//set negative button
                AlertDialog alertDialog = builder
                        .show();
                return false;
            }
        });


    }


}
