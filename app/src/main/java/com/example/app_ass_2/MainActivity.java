package com.example.app_ass_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();
        username =findViewById(R.id.username);
        password =findViewById(R.id.password);
        login =findViewById(R.id.login);
        register =findViewById(R.id.register);

        preferences =getSharedPreferences("userinfo",0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue =username.getText().toString();
                String passwordValue =password.getText().toString();
                String registeredusername = preferences.getString("username","");
                String registeredpassword = preferences.getString("password","");

                if(usernameValue.equals(registeredusername) && passwordValue.equals(registeredpassword)) {
                        Intent intent =new Intent(MainActivity.this,TwoAppActivity.class );
                        startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                }
                if(usernameValue.equals("")&&passwordValue.equals("")){
                    Toast.makeText(MainActivity.this, "Failed to enter value", Toast.LENGTH_SHORT).show();

                }
                if(!usernameValue.equals(registeredusername) && !passwordValue.equals(registeredpassword)){
                    Toast.makeText(MainActivity.this, "No account with this Name", Toast.LENGTH_SHORT).show();

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}