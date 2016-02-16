package com.jaApps.cquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoScreen extends Activity {
	public String data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		Button btn_close1 = (Button)findViewById(R.id.btn_close1);
		Button btn_close2 = (Button)findViewById(R.id.btn_close2);
		TextView txtView = (TextView)findViewById(R.id.txt_info);
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null) {
			data = extras.getString("data");
			if(data != null)
				txtView.setText(data);
			else txtView.setText("more information is not available");
		}
		else
			txtView.setText("more information is not available");
		
		btn_close2.setVisibility(View.INVISIBLE);
		btn_close1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});

		btn_close2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				close();
			}
		});

	}
	public void close(){
		finish();
	}

}
