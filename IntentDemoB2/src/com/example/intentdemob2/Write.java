package com.example.intentdemob2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.Relaybook.Option.PhoneNum;
import com.http.Send_Recv.Sell_inform_thread;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.provider.MediaStore.Images;
import android.telephony.*;
import android.view.*;
import android.widget.*;
import android.widget.RatingBar.OnRatingBarChangeListener;




public class Write extends Activity{
	ScrollView scrollview;
	RatingBar rating;     
	TextView tv01;
	
	/* multipart 전송규약을 위한 인자값*/
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";	
	/* ++++ ++++ ++++ ++++ ++++ ++++ */
	
	
	/* 파일 업로드를 위한 인자값 */
	String filename;
//	Sell_inform_thread SellThread = new Sell_inform_thread();
	MyThread mMyThread =null;
	
	String Subject, Title, Writer, publisher, Price,Quality; //판매 정보를 전송하기위함 변수
	
	/* */
	
	static int i=1;
	
	static int REQUEST_PICTURE = 1;
	static int REQUEST_PHOTO_ALBUM = 2;


	
    /** Called when the activity is first created. */
	Context mContext = this;
	ImageView iv1,iv2,iv3;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.write);

	    
        Button button = (Button)findViewById(R.id.Send);
        button.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				//MainPage.class이 부분을 이동할 곳으로 수정
//				Intent intent = new Intent(Write.this, MainPage.class); 
//		    	startActivity(intent);
		    			    	
		    	Subject = ((EditText) findViewById(R.id.Subject)).getText().toString();;
		    	Title = ((EditText) findViewById(R.id.Title)).getText().toString();;
		    	Writer =((EditText) findViewById(R.id.Writer)).getText().toString();;
		    	publisher = ((EditText) findViewById(R.id.Publisher)).getText().toString();;
		    	Price = ((EditText) findViewById(R.id.Price)).getText().toString();;
		    	
		    	
		    	mMyThread = (MyThread) new MyThread().execute((Void) null);
				
			}
		});
        
	    scrollview = (ScrollView) findViewById(R.id.Scroll);
	    
	    rating = (RatingBar) findViewById(R.id.Quality);         
	    //tv01 = (TextView) findViewById(R.id.tv01);           
	    rating.setStepSize((float) 0.5); //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감         
	    rating.setRating((float) 0.0); // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다 
	    rating.setIsIndicator(false); //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능   
	    
	    
	    rating.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {               
	    	@Override            
	    	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {                 
	    		//tv01.setText("평점 : " + rating);    
	    		Quality = Float.toString(rating);
	    	}         
	    }); 

	    iv1 = (ImageView) findViewById(R.id.imgView1);
	    iv2 = (ImageView) findViewById(R.id.imgView2);
	    iv3 = (ImageView) findViewById(R.id.imgView3);
        
     
	}
	
	//button 클릭
    Dialog dialog;
    public void onClickImg(View v) {
    	i = 1;
    	
    	switch(v.getId()) {
    	case R.id.put_Image:
    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    		View customLayout = View.inflate(mContext, R.layout.custom_button, null);
    		builder.setView(customLayout);
    		dialog = builder.create();
    		dialog.show();
    		break;
    	case R.id.camera:
    		dialog.dismiss();
    		takePicture();
    		++i;
    		dialog.dismiss();
    		takePicture();
    		++i;
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
  		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Relaybook/"  , "img-" + i +".png");
  		
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
  		
  		filename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Relaybook/" + "img-" + i +".png"; //파일이름을 저장
  		
  		BitmapFactory.Options option = new BitmapFactory.Options();
  		option.inSampleSize = 8;
  		Bitmap src = BitmapFactory.decodeFile(filename, option);
  		Bitmap resized = Bitmap.createScaledBitmap(src, 400, 530, true);

  		/* 
  		 * 리사이즈된 bitmap 파일을 sdcard에 다시 저장해준다.
  		 */
  		try {
  	       FileOutputStream out = new FileOutputStream(filename);
  	       resized.compress(Bitmap.CompressFormat.PNG, 90, out);
  	       out.close();
	  	} catch (Exception e) {
	  	       e.printStackTrace();
	  	}
	  	/* ++++ ++++ ++++ ++++ ++++ ++++ ++++ */
	  		
	  		return resized;
	  	}

  	@Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  		// TODO Auto-generated method stub
  		super.onActivityResult(requestCode, resultCode, data);
  		
  		if(resultCode != RESULT_OK)
  			return;
  		  		
	  			if(i==1){
	  				iv3.setImageBitmap(loadPicture());
	  			} else if(i==2){
	  				iv2.setImageBitmap(loadPicture());
	  				i--;
	  			} else {
	  				iv1.setImageBitmap(loadPicture());
	  				i--;
	  			}
  			
  		
  		
  		if(requestCode == REQUEST_PHOTO_ALBUM) {
  			if(i==1){
  				iv3.setImageURI(data.getData());
  			} else if(i==2){
  				iv2.setImageURI(data.getData());
  			} else {
  				iv1.setImageURI(data.getData());
  			}
  		}
  		
  	}
  	
  	/*
  	 * 파일 업로드 
  	 */
	    public class MyThread extends AsyncTask<Void, Void, Void> {

	    	
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
//				 HttpFileUpload("http://14.63.212.134/MyRelayServer/RecvBookInform.jsp");
				HttpFileUpload("http://121.156.253.58:8090/MyRelayServer/RecvBookInform.jsp");

				return null;
			}}
  	
	    private boolean HttpFileUpload(String urlString) {
	    	
	    	try {
				FileInputStream mFileInputStream = new FileInputStream(new File(filename));			
				URL connectUrl = new URL(urlString);

				//데이터 경계선
				String delimiter = "\r\n--" + boundary + "\r\n";
				StringBuffer postDataBuilder = new StringBuffer();

				//전화번호 불러오기
				TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		    	String myNumber = mTelephonyMgr.getLine1Number();
				
				// 추가하고 싶은 Key & Value 추가
			    // key & value를 추가한 후 꼭 경계선을 삽입해줘야 데이터를 구분할 수 있다.
				
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("Subject", Subject));
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("Title", Title));
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("Writer", Writer));
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("publisher", publisher));
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("Price", Price));
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("Quality",Quality));
				postDataBuilder.append(delimiter);
				postDataBuilder.append(setValue("PhoneNum", PhoneNum.getPhoneNum()));
				postDataBuilder.append(delimiter);
				
				System.out.println("파일명 : "+filename);
				
				//파일 첨부
				postDataBuilder.append(setFile("upload2",filename));
//				postDataBuilder.append(delimiter);
//				postDataBuilder.append(setFile("upload1",Environment.getExternalStorageDirectory().getAbsolutePath() + "/Relaybook/" + "img-2.png"));
//				postDataBuilder.append(delimiter);
//				postDataBuilder.append(setFile("upload1",Environment.getExternalStorageDirectory().getAbsolutePath() + "/Relaybook/" + "img-3.png"));
				postDataBuilder.append(lineEnd);

				// open connection 
				HttpURLConnection conn = (HttpURLConnection)connectUrl.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				
				// write data
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));

					
				//위에서 작성한 메타데이터를 먼저 전송(한글때메 UTF-8 메소드 사용)
				dos.writeUTF(postDataBuilder.toString());
				
				System.out.println(postDataBuilder.toString());
		
				int bytesAvailable = mFileInputStream.available();
				int maxBufferSize = 1024;
				int bufferSize = Math.min(bytesAvailable, maxBufferSize);
				
				byte[] buffer = new byte[bufferSize];
				int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
				
				// read image
				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = mFileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
				}	
				
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				
				// close streams
				mFileInputStream.close();
				dos.flush(); // finish upload...			
				
				// get response
				int ch;
				InputStream is = conn.getInputStream();
				StringBuffer b =new StringBuffer();
					
					
				while( ( ch = is.read() ) != -1 ){
					b.append( (char)ch );
				}
				
				if(b.toString().trim().equals("RecvOK")){
					System.out.println("응답 : "+b.toString().trim());
				}
				
				dos.close();
				
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	    
		
		public  String setValue(String key, String value) {
		        return "Content-Disposition: form-data; name=\"" + key + "\"\n\r\n"
		                + value;
		}	 
		    /**
		     * 업로드할 파일에 대한 메타 데이터를 설정한다.
		     * @param key : 서버에서 사용할 파일 변수명
		     * @param fileName : 서버에서 저장될 파일명
		     * @return
		     */
	    public  String setFile(String key, String fileName) {
	        return "Content-Disposition: form-data; name=\"" + key
	                + "\";filename=\"" + fileName ;
	    }
}
