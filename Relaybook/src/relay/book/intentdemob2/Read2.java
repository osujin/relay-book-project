package relay.book.intentdemob2;


import java.net.*;
import relay.book.image.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.SmsManager;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;


public class Read2 extends Activity {

	String imageUrl = "http://14.63.212.134:8080/MyRelayServer/Image/";
	Bitmap bmImg;
	URL myFileUrl = null;
	 
	 
	private static final String URL = "http://14.63.212.134:8080/MyRelayServer/Image/";
	static String URL_book_inform = "http://14.63.212.134:8080/MyRelayServer/Send.jsp";
	
	String filename = null;
	 
	
	//ViewPaper
	private ViewPager mPager;
	private CountDownTimer timer;  
	private int currentPosition;
	private int PAGE_TOTAL_NUMBER = 3;
	//
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read2);

		// getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        final String title = in.getStringExtra("title");
        final String phone = in.getStringExtra("phone");
        String school = in.getStringExtra("school");
        String writer = in.getStringExtra("writer");
        String price = in.getStringExtra("price");
        String subject = in.getStringExtra("subject");
        String memo = in.getStringExtra("memo");        
        int relaycount = Integer.parseInt(in.getStringExtra("relaycount"));
        String publisher = in.getStringExtra("publisher");
        filename = in.getStringExtra("filename");
        float quality = Float.parseFloat( in.getStringExtra("quality") );
        
        
		
        TextView Title = (TextView)findViewById(R.id.title); // 책 제목
	    Title.setText(title);
	    
	    TextView Subject = (TextView)findViewById(R.id.subject); // 과목명
	    Subject.setText(subject);
	   
	    TextView Price = (TextView)findViewById(R.id.price); // 가격
	    Price.setText(price);
	   
	    TextView Publisher = (TextView)findViewById(R.id.publisher); // 출판사
	    Publisher.setText(publisher);
	 
	    TextView Writer = (TextView)findViewById(R.id.writer); //가격
	    Writer.setText(writer);
	   
	    TextView Memo = (TextView)findViewById(R.id.memo); //가격
	    Memo.setText(memo);
	    
	    RatingBar Rating = (RatingBar) findViewById(R.id.quality); 
	    Rating.setRating(quality);
	    
	    EditText School = (EditText)findViewById(R.id.Seller_school);
	    School.setText(school);
	    
	    EditText Phone = (EditText)findViewById(R.id.Seller_phone);
	    Phone.setText(phone);

	    Button Request = (Button)findViewById(R.id.Request);
	    
	    Request.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SmsManager smsManager = SmsManager.getDefault();
		        String sendTo = phone;
		        String myMessage = "-RelayBook-\n등록하신 "+title+" 책을 구매 하고 싶습니다.";
		        smsManager.sendTextMessage(sendTo, null, myMessage, null, null);
		        Toast.makeText(getApplicationContext(), "판매 요청이 전송되었습니다.", Toast.LENGTH_SHORT).show();
		        finish();
			}
		});
	    
	    

		//ViewPaper
		timer = new CountDownTimer(2*1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				if(currentPosition==PAGE_TOTAL_NUMBER-1)
					mPager.setCurrentItem(0);
				else
					mPager.setCurrentItem(currentPosition+1);
			}
		};

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				currentPosition = position;
				timer.cancel();
				timer.start();
			}

			
			@Override public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override public void onPageScrollStateChanged(int arg0) {}
		});
		
		
	}

	

	//ViewPaper
	private class PagerAdapterClass extends PagerAdapter{

		private Context context;
		private LayoutInflater mInflater;

		public PagerAdapterClass(Context c){
			context = c;
			mInflater = LayoutInflater.from(c);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return PAGE_TOTAL_NUMBER;
		}

		@Override
		public Object instantiateItem(View pager, int position) {

			ImageView image = new ImageView(context);
 			
			if(position==0){
				ImageDownloader.download(URL+filename+"_1.jpg", image);
			}else if(position==1){
				ImageDownloader.download(URL+filename+"_2.jpg", image);
			}else if(position==2){
				ImageDownloader.download(URL+filename+"_3.jpg", image);
				
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
			}

			((ViewPager)pager).addView(image, 0);
			return image;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0==arg1;
		}

		public void destroyItem(View pager, int position, Object view) {    
			((ViewPager)pager).removeView((View)view);
		}

	}

}