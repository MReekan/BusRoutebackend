package com.example.busroutebackend;

import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {
    TextView textViewLocationOne,textViewLocationTwo;
    EditText editRouteNumber;
    ListView listViewRoutes;
    Button addRoute;

    DatabaseReference databaseRoute;
    List<Route> routes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        textViewLocationOne=(TextView) findViewById(R.id.textViewLocationOne);
        textViewLocationTwo=(TextView) findViewById(R.id.textViewLocationTwo);
        editRouteNumber =(EditText) findViewById(R.id.editRouteNumber);

        addRoute =(Button) findViewById(R.id.addRoute);
        listViewRoutes     =  (ListView) findViewById(R.id.listViewRoutes);

        Intent intent =getIntent();
        routes =new ArrayList<>();

        String id=intent.getStringExtra(MainActivity.TRAVALLING_ID);
        String location_1=intent.getStringExtra(MainActivity.FIRST_LOCATION);
        String location_2=intent.getStringExtra(MainActivity.SECOND_LOCATION);

        textViewLocationOne.setText(location_1);
        textViewLocationTwo.setText(location_2);

        databaseRoute = FirebaseDatabase.getInstance().getReference("Routes").child(id);

           addRoute.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   saveRoute();
               }
            });



    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseRoute.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                routes.clear();
                for (DataSnapshot routeSnapshot : dataSnapshot.getChildren()) {
                    Route route = routeSnapshot.getValue(Route.class);
                    routes.add(route);

                }
                RouteList adapter = new RouteList(AddRouteActivity.this, routes);
                listViewRoutes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void saveRoute(){
        String routeNumber =editRouteNumber.getText().toString().trim();


        if (!TextUtils.isEmpty(routeNumber)){
            String id=databaseRoute.push().getKey();
            Route route=new Route(id,routeNumber);
            databaseRoute.child(id).setValue(route);

            Toast.makeText(this,"save Route successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Route number sholdn't empty",Toast.LENGTH_LONG).show();
        }

    }
}
