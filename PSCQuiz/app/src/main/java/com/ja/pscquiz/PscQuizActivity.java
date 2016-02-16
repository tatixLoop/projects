package com.ja.pscquiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PscQuizActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_psc_quiz);
		
		// add button click for quantitative aptitude category
		Button btn_qAptitude = (Button)findViewById(R.id.btn_aptitude);
		btn_qAptitude.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 0);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// button for mental Ability and Reasoning
		Button btn_mental_ability = (Button)findViewById(R.id.btn_mental_ability);
		btn_mental_ability.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 1);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// General Science
		Button btn_genSci = (Button)findViewById(R.id.btn_Science);
		btn_genSci.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 2);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// Current Affairs
		Button btn_curr_affair = (Button)findViewById(R.id.btn_current_affairs);
		btn_curr_affair.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 3);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// Facts about India
		Button btn_india = (Button)findViewById(R.id.btn_india);
		btn_india.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 4);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// Facts about Kerala
		Button btn_kerala = (Button)findViewById(R.id.btn_kerala);
		btn_kerala.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 5);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// Indian constitution and civil rights
		Button btn_indianConstition = (Button)findViewById(R.id.btn_civil_rights);
		btn_indianConstition.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 6);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// General english
		Button btn_english = (Button)findViewById(R.id.btn_english);
		btn_english.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 7);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// Social Welfare Scheme
		Button btn_social = (Button)findViewById(R.id.btn_social);
		btn_social.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 8);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
		// Information Technology
		Button btn_it = (Button)findViewById(R.id.btn_it);
		btn_it.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getApplicationContext(), "selected quantitative aptitude", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(PscQuizActivity.this, QuizScreen.class);
				i.putExtra("catagory", 9);
                startActivity(i);
 
                // close this activity
              //  finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.psc_quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	///	int id = item.getItemId();
	//	if (id == R.id.action_settings) {
		//	return true;
	//	}
		return super.onOptionsItemSelected(item);
	}
}
