package com.example.anglepoint;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.anglepoint.bean.Bait;
import com.example.anglepoint.bean.LureMethod;
import com.example.anglepoint.bean.Point;
import com.example.anglepoint.bean.RodLength;
import com.example.anglepoint.db.WildFishingContract;
import com.example.anglepoint.db.WildFishingDatabase;

public class AddPointActivity extends ActionBarActivity implements RodLengthDialogFragment.NoticeDialogListener, 
														           LureMethodDialogFragment.NoticeDialogListener,
														           BaitDialogFragment.NoticeDialogListener {

	WildFishingDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_point);
		
		db = new WildFishingDatabase(getApplicationContext());
		fillRodLengthSpinner(null);
		fillLureMethodSpinner(null);
		fillBaitSpinner(null);
	}
	
	private void fillRodLengthSpinner(String selectedId) {

		Cursor c = db.getRodLengthForPinner();

		// create an array to specify which fields we want to display
		String[] from = new String[] { WildFishingContract.RodLengths.COLUMN_NAME_NAME };
		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
		// create simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to, 0);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// get reference to our spinner
		Spinner s = (Spinner) findViewById(R.id.addPointSpinnerRodLength);
		s.setAdapter(adapter);
		
		// ѡ���ƶ���Ŀ
		if(selectedId != null){
			int position = 0;
			while(c.moveToNext()){
				position++ ;
				if(c.getString(c.getColumnIndex(WildFishingContract.RodLengths._ID)).equals(selectedId)){
					break;
				}
			}
			s.setSelection(position);
		}
	}
	
	private void fillLureMethodSpinner(String selectedId) {

		Cursor c = db.getLureMethodForPinner();

		// create an array to specify which fields we want to display
		String[] from = new String[] { WildFishingContract.LureMethods.COLUMN_NAME_NAME };
		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
		// create simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to, 0);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// get reference to our spinner
		Spinner s = (Spinner) findViewById(R.id.addPointSpinnerLureMethod);
		s.setAdapter(adapter);
		
		// ѡ���ƶ���Ŀ
		if(selectedId != null){
			int position = 0;
			while(c.moveToNext()){
				position++ ;
				if(c.getString(c.getColumnIndex(WildFishingContract.LureMethods._ID)).equals(selectedId)){
					break;
				}
			}
			s.setSelection(position);
		}
	}
	
	private void fillBaitSpinner(String selectedId) {

		Cursor c = db.getBaitForPinner();

		// create an array to specify which fields we want to display
		String[] from = new String[] { WildFishingContract.Baits.COLUMN_NAME_NAME };
		// create an array of the display item we want to bind our data to
		int[] to = new int[] { android.R.id.text1 };
		// create simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, c, from, to, 0);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// get reference to our spinner
		Spinner s = (Spinner) findViewById(R.id.addPointSpinnerBait);
		s.setAdapter(adapter);
		
		// ѡ���ƶ���Ŀ
		if(selectedId != null){
			int position = 0;
			while(c.moveToNext()){
				position++ ;
				if(c.getString(c.getColumnIndex(WildFishingContract.Baits._ID)).equals(selectedId)){
					break;
				}
			}
			s.setSelection(position);
		}
	}
	
	public void addPointShowAddRodLengthBtnClick(View v){
		RodLengthDialogFragment dialog = new RodLengthDialogFragment();
		dialog.show(getSupportFragmentManager(), "rodLengthDialog");
	}
	
	public void addPointShowAddLureMethodBtnClick(View v){
		LureMethodDialogFragment dialog = new LureMethodDialogFragment();
		dialog.show(getSupportFragmentManager(), "lureMethodDialog");
	}
	
	public void addPointShowAddBaitBtnClick(View v){
		BaitDialogFragment dialog = new BaitDialogFragment();
		dialog.show(getSupportFragmentManager(), "baitDialog");
	}
	
	// The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onRLDialogPositiveClick(DialogFragment dialog, String rodLength) {
        // User touched the dialog's positive button
    	RodLength rl = new RodLength();
    	rl.setName(rodLength);
    	long rowId = db.addRodLength(rl);
    	
    	fillRodLengthSpinner(String.valueOf(rowId));
    	
    }

    @Override
    public void onRLDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
    
 // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onLMDialogPositiveClick(DialogFragment dialog, String name, String detail) {
        // User touched the dialog's positive button
    	LureMethod lm = new LureMethod();
    	lm.setName(name);
    	lm.setDetail(detail);
    	long rowId = db.addLureMethod(lm);
    	
    	fillLureMethodSpinner(String.valueOf(rowId));
    	
    }

    @Override
    public void onLMDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
    
 // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onBaitDialogPositiveClick(DialogFragment dialog, String name, String detail) {
        // User touched the dialog's positive button
    	Bait b = new Bait();
    	b.setName(name);
    	b.setDetail(detail);
    	long rowId = db.addBait(b);
    	
    	fillBaitSpinner(String.valueOf(rowId));
    	
    }

    @Override
    public void onBaitDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }

	
	public void addPointSaveBtnClick(View v){
		Spinner spinnerRodLength = (Spinner) findViewById(R.id.addPointSpinnerRodLength);
		Spinner spinnerLureMethod = (Spinner) findViewById(R.id.addPointSpinnerLureMethod);
		Spinner spinnerBait = (Spinner) findViewById(R.id.addPointSpinnerBait);
		EditText editTextDepth = (EditText) findViewById(R.id.addPointEditTextDepth);
		
		Point p = new Point();
		p.setPlaceId("1");
		p.setRodLengthId(String.valueOf(spinnerRodLength.getSelectedItemId()));
		p.setDepth(editTextDepth.getText().toString());
		p.setLureMethodId(String.valueOf(spinnerLureMethod.getSelectedItemId()));
		p.setBaitId(String.valueOf(spinnerBait.getSelectedItemId()));
		long pointId = db.addPoint(p);
		
		Intent intent = getIntent();
		intent.putExtra("pointId", String.valueOf(pointId));
		setResult(RESULT_OK, intent);
		finish();
//		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_point, menu);
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
