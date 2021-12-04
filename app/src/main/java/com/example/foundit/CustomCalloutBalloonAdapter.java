package com.example.foundit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    private final View mCalloutBalloon;

    public CustomCalloutBalloonAdapter(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCalloutBalloon = inflater.inflate(R.layout.custom_callout_balloon, null);
        Log.d("mapBalloon", mCalloutBalloon +"11111");
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        if(poiItem.getItemName().equals("현재위치")){
            ((TextView) mCalloutBalloon.findViewById(R.id.balloon_title)).setText("현재위치");
            ((TextView) mCalloutBalloon.findViewById(R.id.balloon_desc)).setText("");
        }else{
            ((TextView) mCalloutBalloon.findViewById(R.id.balloon_title)).setText("힌트!!");
            ((TextView) mCalloutBalloon.findViewById(R.id.balloon_desc)).setText(Adapter.location_array[2]);
        }

        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return null;
    }
}
