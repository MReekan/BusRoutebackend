package com.example.busroutebackend;

import android.app.Activity;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;

import java.util.List;
import java.util.logging.Logger;

public class RouteList extends ArrayAdapter<Route> {
    private static final Logger LOGGER = Logger.getLogger(RouteList.class.getName());


    private Activity context;
    private List<Route> routeList;

    public RouteList(Activity context, List<Route> routeList) {
        super(context, R.layout.list_route, routeList);
        this.context = context;
        this.routeList = routeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_route, null, true);


        TextView textViewRoute = (TextView) listViewItem.findViewById(R.id.textViewRoute);


        Route route = routeList.get(position);

        textViewRoute.setText(route.getRouteNumber());


//        LOGGER.info("is it print listViewItem");

        return listViewItem;
    }
}
