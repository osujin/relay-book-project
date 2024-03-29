package com.example.intentdemob2;

import java.io.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.provider.MediaStore.Images;
import android.telephony.*;
import android.view.*;
import android.widget.*;

public class PictureCtrlActivity extends Activity {
	static int REQUEST_PICTURE = 1;
	static int REQUEST_PHOTO_ALBUM = 2;
	
	static String SAMPLEIMG = System.currentTimeMillis() + ".png";	
	
    /** Called when the activity is first created. */
	Context mContext = this;
	ImageView iv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        iv = (ImageView) findViewById(R.id.imgView);
        
        /**
        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    	String myNumber = mTelephonyMgr.getLine1Number();
    	
    	TextView phoneText = (TextView) findViewById(R.id.PhoneNum);        
        phoneText.setText(myNumber);
    	**/
    }
    
    //button 클릭
    Dialog dialog;
    public void onClickImg(View v) {	
    	switch(v.getId()) {
    	case R.id.getCustom:
    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    		View customLayout = View.inflate(mContext, R.layout.custom_button, null);
    		builder.setView(customLayout);
    		dialog = builder.create();
    		dialog.show();
    		break;
    	case R.id.camera:
    		dialog.dismiss();
    		takePicture();
    		break;
    	case R.id.photoAlbum:
    		dialog.dismiss();
    		photoAlbum();
    		break;
    	}
    }
    
    //사진 촬영
  	void takePicture() {
  		//카메라 호출 intent 생성
  		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  		File file = new File(Environment.getExternalStorageDirectory(), SAMPLEIMG);
  		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
  		startActivityForResult(intent, REQUEST_PICTURE);
  	}
  	
  	//사진 불러오기
  	void photoAlbum() {
  		//photo Album 호출 intent 생성
  		Intent intent = new Intent(Intent.ACTION_PICK);
  		
  		intent.setType(Images.Media.CONTENT_TYPE);
  		intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
  		startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
  	}
  	
  	//촬영한 사진을 수정하기 위해서
  	Bitmap loadPicture() {
  		File file = new File(Environment.getExternalStorageDirectory(), SAMPLEIMG);
  		BitmapFactory.Options option = new BitmapFactory.Options();
  		option.inSampleSize = 4;
  		return BitmapFactory.decodeFile(file.getAbsolutePath(), option);
  	}
  	
  	@Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  		// TODO Auto-generated method stub
  		super.onActivityResult(requestCode, resultCode, data);
  		
  		if(resultCode != RESULT_OK)
  			return;
  		
  		if(requestCode == REQUEST_PICTURE) {
  			iv.setImageBitmap(loadPicture());
  		}
  		
  		if(requestCode == REQUEST_PHOTO_ALBUM) {
  			iv.setImageURI(data.getData());
  		}
  	}
}