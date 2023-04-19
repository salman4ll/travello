package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();


       buttonStart = findViewById(R.id.button);

       buttonStart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               new MyDestination(MainActivity.this);

               DatabaseUserHandler db = new DatabaseUserHandler(MainActivity.this);

               int checkUser = db.getUserModelCount();
               switch (checkUser){
                   case 1:
                       Intent intent = new Intent(MainActivity.this, Dashboard.class);
                       startActivity(intent);
                       finish();
                       break;
                   case 0:
                       Intent intent2 = new Intent(MainActivity.this, Login2.class);
                       startActivity(intent2);
                       finish();
                       break;
                   default:
                       System.out.println(checkUser);
               }
           }
       });
    }
}