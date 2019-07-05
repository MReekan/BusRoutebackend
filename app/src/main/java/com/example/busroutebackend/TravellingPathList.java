package com.example.busroutebackend;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.logging.Logger;

public class TravellingPathList extends ArrayAdapter<TravellingPath> {
    private static final Logger LOGGER = Logger.getLogger(TravellingPathList.class.getName());


    private Activity context;
    private List<TravellingPath> travellingPathList;

    public TravellingPathList(Activity context, List<TravellingPath> travellingPathList) {
        super(context, R.layout.list_travelling_path, travellingPathList);
        this.context = context;
        this.travellingPathList = travellingPathList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_travelling_path, null, true);


        TextView textViewLocationOne = (TextView) listViewItem.findViewById(R.id.textViewLocationOne);
        TextView textViewLocationTwo = (TextView) listViewItem.findViewById(R.id.textViewLocationTwo);
        TextView textViewDistance = (TextView) listViewItem.findViewById(R.id.textViewDistance);
        TextView textViewTicketFees = (TextView) listViewItem.findViewById(R.id.textViewTicketFees);



        TravellingPath travelling = travellingPathList.get(position);

        textViewLocationOne.setText(travelling.getLocationOne());
        textViewLocationTwo.setText(travelling.getLocationTwo());
        textViewDistance.setText(travelling.getTravellingDistance());
        textViewTicketFees.setText(travelling.getTicketFees());

//        LOGGER.info("is it print listViewItem");

        return listViewItem;
    }
}
