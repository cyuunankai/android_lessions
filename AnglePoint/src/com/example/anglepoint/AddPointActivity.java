package com.example.anglepoint;

import java.util.ArrayList;
import java.util.List;

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

    public static final String POINT_ID = "point_id";
    String globalPointId;
	WildFishingDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_point);
		
		db = new WildFishingDatabase(getApplicationContext());
		
        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getExtras().containsKey(POINT_ID)) {
            String pointId = intent.getStringExtra(POINT_ID);
            globalPointId = pointId;
            Point point = db.getPointById(pointId);
            EditText editTextDepth = (EditText)findViewById(R.id.addPointEditTextDepth);
            editTextDepth.setText(point.getDepth());
            fillRodLengthSpinner(point.getRodLengthId());
            fillLureMethodSpinner(point.getLureMethodId());
            fillBaitSpinner(point.getBaitId());
        } else {
            fillRodLengthSpinner(null);
            fillLureMethodSpinner(null);
            fillBaitSpinner(null);
        }
		
	}
	
	private void fillRodLengthSpinner(String selectedId) {

		Cursor c = db.getRodLengthForPinner();
		List<Point> pList = new ArrayList<Point>();
		while(c.moveToNext()){
		    Point p = new Point();
		    p.setRodLengthId(c.getString(c.getColumnIndex(WildFishingContract.RodLengths._ID)));
		    pList.add(p);
        }
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
		
		// 设定选中项目
		String colId = WildFishingContract.RodLengths._ID;
		setSpinnerSelection(selectedId, c, s, colId);
	}

	private void setSpinnerSelection(String selectedId, Cursor c, Spinner s, String colId) {
		if(selectedId != null){
			int position = 0;
			c.moveToPosition(-1);
			while(c.moveToNext()){
				if(c.getString(c.getColumnIndex(colId)).equals(selectedId)){
					break;
				}
				position++ ;
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
		
		// 设定选中项目
		String colId = WildFishingContract.LureMethods._ID;
		setSpinnerSelection(selectedId, c, s, colId);
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
		
		// 设定选中项目
		String colId = WildFishingContract.Baits._ID;
		setSpinnerSelection(selectedId, c, s, colId);
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
	    Intent intent = getIntent();
	    
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
		if(globalPointId != null){
		    p.setId(globalPointId);
		    db.updatePoint(p);
		}else{
		    long pointId = db.addPoint(p);
		    intent.putExtra("pointId", String.valueOf(pointId));
		}
		
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
