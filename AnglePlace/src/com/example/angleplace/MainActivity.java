package com.example.angleplace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.angleplace.bean.Place;
import com.example.angleplace.db.WildFishingDatabase;

public class MainActivity extends ActionBarActivity {

	private static int RESULT_LOAD_IMAGE = 1;
	private static String PLACE_IMAGE_PATH = "/placeImages/";
	Bitmap b;
	WildFishingDatabase db;
	String dbPlaceId;
	String dbFileName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db = new WildFishingDatabase(getApplicationContext());
		
		Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                 
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
				b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
				ImageView imageView = (ImageView) findViewById(R.id.imgView);
				imageView.setImageBitmap(b);
            } catch (FileNotFoundException e) {
			} catch (IOException e) {
			} 
        }
    }
	
	public void savePlace(View v){
		
		EditText etTitle = (EditText) findViewById(R.id.editTextTitle);
		EditText etDetail = (EditText) findViewById(R.id.editTextDetail);
		String title = etTitle.getText().toString();
		String detail = etDetail.getText().toString();
		
		String fileName = saveToInternalStorage(b);
		
		SavePlaceToDB(fileName, title, detail);
	}
	
	public void updatePlace(View v){
		
		EditText etTitle = (EditText) findViewById(R.id.editTextTitle);
		EditText etDetail = (EditText) findViewById(R.id.editTextDetail);
		String title = etTitle.getText().toString();
		String detail = etDetail.getText().toString();
		
		// ɾ��֮ǰͼƬ
		File storagePath = new File(getApplicationContext().getFilesDir() + PLACE_IMAGE_PATH); 
        File myImage = new File(storagePath, dbFileName);
        myImage.delete();
        
		String fileName = saveToInternalStorage(b);
		
		Place place = new Place();
		place.setId(dbPlaceId);
		place.setTitle(title);
		place.setDetail(detail);
		place.setFileName(fileName);
		db.updatePlace(place);
	}
	
	public void deletePlace(View v){
		
		// ɾ��֮ǰͼƬ
		File storagePath = new File(getApplicationContext().getFilesDir() + PLACE_IMAGE_PATH); 
        File myImage = new File(storagePath, dbFileName);
        myImage.delete();
        
		db.deletePlace(dbPlaceId);
	}
	
	public void showPlace(View v){
		Place place = db.getPlaceById("1");
		dbPlaceId = place.getId();
		dbFileName = place.getFileName();
		
		EditText etTitle = (EditText) findViewById(R.id.editTextTitle);
		EditText etDetail = (EditText) findViewById(R.id.editTextDetail);
		ImageView imageView = (ImageView) findViewById(R.id.imgView);
		
		etTitle.setText(place.getTitle());
		etDetail.setText(place.getDetail());
		String pathName = getApplicationContext().getFilesDir() + PLACE_IMAGE_PATH + place.getFileName();
		imageView.setImageBitmap(BitmapFactory.decodeFile(pathName));
	}

	private void SavePlaceToDB(String fileName, String title, String detail) {
		Place place = new Place();
		place.setTitle(title);
		place.setDetail(detail);
		place.setFileName(fileName);
		db.addPlace(place);
	}
	
	private String saveToInternalStorage(Bitmap outputImage){
		
		String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
		
        File storagePath = new File(getApplicationContext().getFilesDir() + PLACE_IMAGE_PATH); 
        storagePath.mkdirs(); 

        File myImage = new File(storagePath, fileName);

        try { 
            FileOutputStream out = new FileOutputStream(myImage); 
            outputImage.compress(Bitmap.CompressFormat.JPEG, 80, out); 
            out.flush();    
            out.close();
        } catch (Exception e) { 
        	fileName = null;
        }
        
        return fileName;
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
