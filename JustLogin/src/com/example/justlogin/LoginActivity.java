package com.example.justlogin;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	EditText number;
	
	Button button;
	// Session Manager Class
	SessionManager session;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextpage); 
        
        // Session Manager
        session = new SessionManager(getApplicationContext());                
        
        number = (EditText) findViewById(R.id.editText1);
  
       Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
           
       // Login button
       button = (Button) findViewById(R.id.button1);
       
       button.setOnClickListener(new View.OnClickListener() {
	
    	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String num = number.getText().toString();
			int len = num.trim().length();
    		
    		
			if(len > 9){
				//int numb = Integer.parseInt(num);
				session.createLoginSession(num);
			//	Toast.makeText(getApplicationContext(), " " + len, Toast.LENGTH_LONG);
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
			}
			else {
				Toast.makeText(getApplicationContext(), "Please Enter 10 digit number", Toast.LENGTH_LONG).show();
			}
			
		}
	});
	}
}