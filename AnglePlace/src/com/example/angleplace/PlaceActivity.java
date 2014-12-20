package com.example.angleplace;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.angleplace.bean.Place;
import com.example.angleplace.common.Constant;
import com.example.angleplace.db.WildFishingContract;
import com.example.angleplace.db.WildFishingDatabase;

public class PlaceActivity extends ActionBarActivity {

	WildFishingDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		
		db = new WildFishingDatabase(getApplicationContext());
		
		fillSpinner();
		
		setPlaceSpinnerItemSelectedListener();
	}
	
	public void buttonAddPlaceOnClick(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	private void setPlaceSpinnerItemSelectedListener() {
		OnItemSelectedListener oisl = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                    int position, long id) {
                
                String placeId = String.valueOf(parent.getItemIdAtPosition(position));
                showPlace(placeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        };
        Spinner s = (Spinner) findViewById(R.id.spinnerPlace);
        s.setOnItemSelectedListener(oisl);
	}
	
	public void showPlace(String placeId){
		Place place = db.getPlaceById(placeId);
		
		TextView tvTitle = (TextView) findViewById(R.id.textViewTitle);
		TextView tvDetail = (TextView) findViewById(R.id.textViewDetail);
		ImageView imageView = (ImageView) findViewById(R.id.imageViewPlace);
		
		tvTitle.setText(place.getTitle());
		tvDetail.setText(place.getDetail());
		String pathName = getApplicationContext().getFilesDir() + Constant.PLACE_IMAGE_PATH + place.getFileName();
		imageView.setImageBitmap(BitmapFactory.decodeFile(pathName));
	}
	
	private void fillSpinner() {

		Cursor c = db.getPlaceForPinner();
//		startManagingCursor(c);

		// create an array to specify which fields we want to display
		String[] from = new String[] { WildFishingContract.Places.COLUMN_NAME_TITLE };
		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
		// create simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to, 0);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// get reference to our spinner
		Spinner s = (Spinner) findViewById(R.id.spinnerPlace);
		s.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.place, menu);
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
