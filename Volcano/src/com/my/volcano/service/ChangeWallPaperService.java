package com.my.volcano.service;

import java.io.IOException;

import com.my.volcano.R;
import com.my.volcano.util.FileUtil;
import com.my.volcano.util.LogUtil;

import android.app.IntentService;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;

public class ChangeWallPaperService extends IntentService {
    
    Handler mMainThreadHandler = null;
    
    /** 
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public ChangeWallPaperService() {
        super("ChangeWallPaperService");
        
        mMainThreadHandler = new Handler();
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        mMainThreadHandler.post(new Runnable() {
              @Override
              public void run() {
                  
                  WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                  Bitmap bm = ((BitmapDrawable)myWallpaperManager.getDrawable()).getBitmap();
                  String fileName = getApplicationContext().getFilesDir() + "/wallPaper/userWallPaper.jpg";
                  FileUtil.saveToInternalStorage(fileName, bm);
                  
                  try {
                      myWallpaperManager.setResource(R.drawable.ic_launcher);
                  } catch (IOException e) {
                      LogUtil.appendLog("set wallPaper error : " + e.getMessage());
                  }
                  
              }
          });
    }
    
    

}
