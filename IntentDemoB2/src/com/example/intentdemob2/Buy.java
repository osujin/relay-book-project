package com.example.intentdemob2;

import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class Buy extends Activity implements OnItemSelectedListener{
	ArrayList<String> arraylist;


	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);
        
        arraylist = new ArrayList<String>();
		arraylist.add("책이름");
		arraylist.add("저자");
		arraylist.add("교과명");
		arraylist.add("학교명");


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, arraylist); 
                               //스피너 속성
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner sp = (Spinner) this.findViewById(R.id.Spinner);
		sp.setPrompt("골라봐"); // 스피너 제목
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(this);


	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, arraylist.get(arg2), Toast.LENGTH_LONG).show();//해당목차눌렸을때
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub


	}
}

