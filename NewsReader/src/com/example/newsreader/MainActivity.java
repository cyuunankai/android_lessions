package com.example.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	boolean mIsDualPane;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 是否为多面板显示模式
		View articleView = findViewById(R.id.article);
        mIsDualPane = articleView != null && 
                        articleView.getVisibility() == View.VISIBLE;
        
        Button btn = (Button) findViewById(R.id.button1);
        OnClickListener ocl = new OnClickListener() {

            @Override
            public void onClick(View view) {
            	if(mIsDualPane){
            		// 改变右侧面板显示内容
                	TextView tv = (TextView) findViewById(R.id.articleText);
                	tv.setText("changed by headLines");
                }else{
                	// 打开新窗口
                	Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                    startActivity(intent);
                }
            }
        };
        
        btn.setOnClickListener(ocl);
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
