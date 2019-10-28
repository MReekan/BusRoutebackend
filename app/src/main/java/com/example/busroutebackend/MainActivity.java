package com.example.busroutebackend;


import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import 	androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger(MainActivity.class.getName());

    public static final String FIRST_LOCATION = "firstlocation";
    public static final String SECOND_LOCATION = "secondlocation";
    public static final String TRAVALLING_ID = "travellingid";

    EditText editFirstLocation, editSecondLocation, editTravellingDistance,editTicketFees;
    Button addTravellingPath;

    DatabaseReference databaseTravellingPath;

    ListView listViewTravellingPath;
    List<TravellingPath> travellingPathList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


        databaseTravellingPath   = FirebaseDatabase.getInstance().getReference("travelling_paths");


        editFirstLocation        = (EditText) findViewById(R.id.editFirstLocation);
        editSecondLocation       = (EditText) findViewById(R.id.editSecondLocation);
        editTravellingDistance   = (EditText) findViewById(R.id.editTravellingDistance);
        editTicketFees           = (EditText) findViewById(R.id.editTicketFees);
        addTravellingPath        = (Button) findViewById(R.id.addTravellingPath);


        listViewTravellingPath = (ListView) findViewById(R.id.listViewTravellingPath);
        travellingPathList     = new ArrayList<>();

        addTravellingPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTravellingPath();
            }
        });

        // click the listView items and go do the route add page
        listViewTravellingPath.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TravellingPath travelling=travellingPathList.get(i);

                Intent intent=new Intent(getApplicationContext(),AddRouteActivity.class);

                intent.putExtra(TRAVALLING_ID,travelling.getTravellingId());
                intent.putExtra(FIRST_LOCATION,travelling.getLocationOne());
                intent.putExtra(SECOND_LOCATION,travelling.getLocationTwo());

                startActivity(intent);

            }
        });

        // long click the listView items and update the ticket fees
        listViewTravellingPath.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {

                TravellingPath travelling =travellingPathList.get(i);

                showUpdateDialog(travelling.getTravellingId(),travelling.getLocationOne(),travelling.getLocationTwo(),travelling.getTravellingDistance(),travelling.getTicketFees());
                return false;
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

         databaseTravellingPath.addValueEventListener(new ValueEventListener() {
            @Override
            // read the firebase db and change the db changes
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                travellingPathList.clear();
                for (DataSnapshot travellingSnapshot : dataSnapshot.getChildren()) {
                    TravellingPath travellingPath = travellingSnapshot.getValue(TravellingPath.class);
                    travellingPathList.add(travellingPath);

                }
                TravellingPathList adapter = new TravellingPathList(MainActivity.this, travellingPathList);
                listViewTravellingPath.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    // update ticket fees details
    private void showUpdateDialog(final String travellingId,String location_1,String location_2,String distance, String ticketFees){
        AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);

        LayoutInflater inflater =getLayoutInflater();

        final View dialogView =inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

        final EditText editFirstLocation  =(EditText)dialogView.findViewById(R.id.editFirstLocation);
        final EditText editSecondLocation =(EditText)dialogView.findViewById(R.id.editSecondLocation);
        final EditText editDistance       =(EditText)dialogView.findViewById(R.id.editDistance);
        final EditText editTicketFees     =(EditText)dialogView.findViewById(R.id.editTicketFees);
        final Button   buttonUpdate       =(Button)dialogView.findViewById(R.id.buttonUpdate);
        final Button   buttonDelete       =(Button)dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Updating Travelling Path Details ");

        final AlertDialog alertDialog =dialogBuilder.create();
        alertDialog.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location_1 = editFirstLocation.getText().toString().trim();
                String location_2 = editSecondLocation.getText().toString().trim();
                String distance   = editDistance.getText().toString().trim();
                String ticket     = editTicketFees.getText().toString().trim();

                if (TextUtils.isEmpty(ticket)){
                    editTicketFees.setError("Ticket Fees Required");
                }

                updateTicketFees(travellingId,location_1,location_2,distance,ticket);
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTravellingPath(travellingId);
            }
        });



    }
    private void deleteTravellingPath(String travellingId){
        DatabaseReference drTravellingPath =FirebaseDatabase.getInstance().getReference("travelling_paths").child(travellingId);
        DatabaseReference drRoute =FirebaseDatabase.getInstance().getReference("Routes").child(travellingId);

        drTravellingPath.removeValue();
        drRoute.removeValue();

        Toast.makeText(this, " Travelling Path Deleted ", Toast.LENGTH_LONG).show();

    }


    private boolean updateTicketFees(String id,String location_1,String location_2,String distance,String ticket){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("travelling_paths").child(id);

        TravellingPath travelling = new TravellingPath(id,location_1,location_2,distance,ticket);


        databaseReference.setValue(travelling);

        Toast.makeText(this, " updated successfully ", Toast.LENGTH_LONG).show();
        return true;
    }

 // add travelling path details
    private void addTravellingPath() {
        String location_1 = editFirstLocation.getText().toString().trim();
        String location_2 = editSecondLocation.getText().toString().trim();
        String distance   = editTravellingDistance.getText().toString().trim();
        String ticket     = editTicketFees.getText().toString().trim();


        if (!TextUtils.isEmpty(location_1) && !TextUtils.isEmpty(location_2) && !TextUtils.isEmpty(distance) && !TextUtils.isEmpty(ticket)) {

//            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//            Query query = rootRef.child("travelling_paths").orderByChild("travellingId").equalTo("-LiPMJBt1xy78Bc_-tIc");
//
//            LOGGER.info("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//            System.out.print("rrrrrrrrrrrrrrrrrrrr"+query);
            String id = databaseTravellingPath.push().getKey();

            TravellingPath travelling_path = new TravellingPath(id, location_1, location_2, distance,ticket);

            databaseTravellingPath.child(id).setValue(travelling_path);

            Toast.makeText(this, "travelling path added ", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "you should enter whole details ", Toast.LENGTH_LONG).show();
        }
    }

}
