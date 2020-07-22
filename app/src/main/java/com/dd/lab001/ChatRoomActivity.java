package com.dd.lab001;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    private ListView listView;
    private EditText editText;
    private List<Message> messages = new ArrayList<>();
    private ChatAdapter adapter;
//    List<Message> messagesessages;
    public static final String ITEM_ID = "id";
    public static final String ITEM_POSITION = "position";
    public static final String ITEM_MSG = "msg";
    public static final String ITEM_TYPE = "type";

    public static final int EMPTY_ACTIVITY = 345;
    private Button sendBtn;
    private Button receiveBtn;
    private DatabaseClass databaseHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        listView = (ListView)findViewById(R.id.ListView);
        editText = (EditText)findViewById(R.id.chatEditText);
        sendBtn = (Button)findViewById(R.id.SendBtn);
        receiveBtn = (Button)findViewById(R.id.ReceiveBtn);
        databaseHelp = new DatabaseClass(this);

//        final ChatAdapter messageAdapter = new ChatAdapter(messages, this);
//        listView.setAdapter(messageAdapter);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded --- fragment
        Cursor cursor = databaseHelp.printCursor();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                Message model = new Message((int) cursor.getLong(0), cursor.getString(1), cursor.getInt(2) == 0);
                messages.add(model);
                adapter = new ChatAdapter(messages, getApplicationContext());
                listView.setAdapter(adapter);
            }
        } else {
            adapter = new ChatAdapter(messages, getApplicationContext());
            listView.setAdapter(adapter);
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendMessage = editText.getText().toString();
                updateMessageIntoList(sendMessage, true);
            }
        });

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMessage = editText.getText().toString();
                updateMessageIntoList(sendMessage, false);
            }
        });



        viewData();

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


                        databaseHelp.deleteData(messages.get(position).getId());
                        Log.d("adn",String.valueOf(messages.get(position).getId()));
//                        Message item = listMessage.get(position);
                        messages.remove(position);
                        ChatAdapter adt = new ChatAdapter(messages, getApplicationContext());
                        listView.setAdapter(adt);
                        adt.notifyDataSetChanged();

//                        String bookid=messages.getItem(position).id
//                        db.delete_order("tbl_order", bookid);


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

        listView.setOnItemClickListener((parent, view, position, id) -> {


            Message message = (Message) adapter.getItem(position);
            Log.e("ClickStartPosition",Integer.toString(position));
            Log.e("message= ",message.toString());


            Bundle dataToPass = new Bundle();
            dataToPass.putLong(ITEM_ID, message.getId());
            dataToPass.putInt(ITEM_POSITION, position);

            dataToPass.putString(ITEM_MSG, message.getMessage());
            dataToPass.putBoolean(ITEM_TYPE, message.isSend());
            Log.e("Bundle data id",ITEM_ID);
            Log.e("position",ITEM_POSITION);
            Log.e("positionGet",Integer.toString(dataToPass.getInt(ITEM_POSITION)));
            Log.e("msg",ITEM_MSG);
            Log.e("item_type==",ITEM_TYPE);

            if(isTablet)
            {
                MessageFragment messageFragment = new MessageFragment();
                messageFragment.setArguments( dataToPass );
                messageFragment.setTablet(true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, messageFragment)
                        .addToBackStack("AnyName")
                        .commit();
            }
            else
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass);

                Log.e("phone intent pos",Integer.toString(dataToPass.getInt(ITEM_POSITION)));
                startActivityForResult(nextActivity, EMPTY_ACTIVITY);
            }


        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EMPTY_ACTIVITY)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                int position=data.getIntExtra(ITEM_POSITION,0);
                Log.e("functionActivityResult",Long.toString(id));
                Log.e("ITEM_POSITION == ",Integer.toString(position));
                deleteMessageId(id,position);
            }
        }
    }

    public void deleteMessageId(long id,int position)
    {
        //ChatRoomHelper chatRoomHelper=new ChatRoomHelper(this);
        Log.i("Delete this message:" , " id="+id);
        int result=databaseHelp.deleteData(id);
        Log.e("ggg",String.valueOf(result));
        if(result==1) {
            Message m=messages.remove(position);
            Log.e("item_Position===",Integer.toString(position));
            Log.e("item_Position===",ITEM_POSITION);
            Log.e("item===",m.getMessage());
            adapter.notifyDataSetChanged();
        }
    }

    private void updateMessageIntoList(final String msg, final boolean isSend) {
        final Message model = new Message(msg, isSend);
        long id = databaseHelp.insertData(msg, isSend);
        if (id > -1) {
            model.setId((int) id);
            messages.add(model);
            adapter.notifyDataSetChanged();
            editText.setText("");
        }

    }


    private void viewData(){

        Cursor cursor = databaseHelp.printCursor();

        if (cursor.getCount() != 0){

            while (cursor.moveToNext()){

                Message msg = new Message(cursor.getInt(0), cursor.getString(1) ,cursor.getInt(2)==0);

                messages.add(msg);

                ChatAdapter chatAdapter = new ChatAdapter(messages, getApplicationContext());

                listView.setAdapter(chatAdapter);

            }

        }

    }
}





