package com.example.anglepoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PointDetailActivity extends ActionBarActivity {

	public static final String ROD_LENGTH_NAME = "rod_length_name";
	public static final String DEPTH = "depth";
	public static final String LURE_METHOD_NAME = "lure_method_name";
	public static final String BAIT = "bait";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_point_detail);
		
		Intent intent = getIntent();
		String rodLengthName = intent.getStringExtra(ROD_LENGTH_NAME);
		String depth = intent.getStringExtra(DEPTH);
		String lureMethodName = intent.getStringExtra(LURE_METHOD_NAME);
		String bait = intent.getStringExtra(BAIT);
		((TextView) findViewById(R.id.pointDetailRodLength)).setText(rodLengthName);
		((TextView) findViewById(R.id.pointDetailDepth)).setText(depth);
		((TextView) findViewById(R.id.pointDetailLureMethod)).setText(lureMethodName);
		((TextView) findViewById(R.id.pointDetailBait)).setText(bait);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.point_detail, menu);
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
