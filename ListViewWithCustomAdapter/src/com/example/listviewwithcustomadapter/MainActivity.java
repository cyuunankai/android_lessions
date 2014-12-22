package com.example.listviewwithcustomadapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	ListView listViewSMS;
    Cursor cursor;
    SMSListAdapter smsListAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            
            context=this;
            listViewSMS=(ListView)findViewById(R.id.listView1);
            listViewSMS.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

//            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
//            
//            List<MyMenuItem> list = new ArrayList<MyMenuItem>();
//            // move the cursor to required position 
//            while (cursor.moveToNext()) {
//            	MyMenuItem item = new MyMenuItem();
//            	item.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
//            	item.setComment(cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
//            	list.add(item);
//            }
            
            List<MyMenuItem> list = new ArrayList<MyMenuItem>();
            MyMenuItem item = new MyMenuItem();
            item.setText("11111111111111");
        	item.setComment("11111111111111");
        	list.add(item);
        	item = new MyMenuItem();
            item.setText("2222222222222");
         	item.setComment("222222222222");
        	list.add(item);
            
            // Create the Adapter
            smsListAdapter=new SMSListAdapter(this, R.layout.activity_listview_each_item, android.R.id.text1, list);
            
            // Set The Adapter to ListView
            listViewSMS.setAdapter(smsListAdapter);
            
            // to handle click event on listView item
            listViewSMS.setOnItemClickListener(new OnItemClickListener()
            {
                    public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                    {
                        // when user clicks on ListView Item , onItemClick is called 
                        // with position and View of the item which is clicked
                        // we can use the position parameter to get index of clicked item
                        TextView textViewSMSSender=(TextView)v.findViewById(R.id.text);
                        TextView textViewSMSBody=(TextView)v.findViewById(R.id.comment);
                        String smsSender=textViewSMSSender.getText().toString();
                        String smsBody=textViewSMSBody.getText().toString();
                        
                        // Show The Dialog with Selected SMS 
                        AlertDialog dialog = new AlertDialog.Builder(context).create();
                        dialog.setTitle("SMS From : "+smsSender);
                        dialog.setIcon(android.R.drawable.ic_dialog_info);
                        dialog.setMessage(smsBody);
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) 
                            {
                            
                                    dialog.dismiss();
                                    return;
                        }    
                        });
                        dialog.show();
                    }
                });

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
