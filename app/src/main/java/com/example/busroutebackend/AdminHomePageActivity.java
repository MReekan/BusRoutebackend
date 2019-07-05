package com.example.busroutebackend;


import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class AdminHomePageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
    }

    //click Travel path button and view the Travelling Path page
    public void addDetails(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
