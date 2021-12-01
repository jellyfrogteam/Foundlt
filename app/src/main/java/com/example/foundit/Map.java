package com.example.foundit;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class Map extends AppCompatActivity {

    Button btn_zoomPlus;
    Button btn_zoomMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // java code
        MapView mapView = new MapView(this);

        CustomCalloutBalloonAdapter balloonAdapter = new CustomCalloutBalloonAdapter(getApplicationContext());
        mapView.setCalloutBalloonAdapter(balloonAdapter);

//
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
//
      //  MapPOIItem customMarker = new MapPOIItem();
        MapPoint markerPoint= MapPoint.mapPointWithGeoCoord(37.434921215200994, 127.08022310897256);
//        customMarker.setItemName("Custom Marker");
//        customMarker.setTag(1);
//        customMarker.setMapPoint(markerPoint);
//        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
//        customMarker.setCustomImageResourceId(R.drawable.map_marker); // 마커 이미지.
//        customMarker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
//
//        mapView.addPOIItem(customMarker);


        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("ACustom Marker");
        customMarker.setTag(1);
        customMarker.setMapPoint(markerPoint);
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        customMarker.setCustomImageResourceId(R.drawable.map_marker); // 마커 이미지.
        customMarker.setCustomImageAutoscale(true); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.


        mapView.addPOIItem(customMarker);
        mapView.setMapCenterPoint(markerPoint, true);


        btn_zoomPlus = findViewById(R.id.btn_zoom_plus);
        btn_zoomMinus = findViewById(R.id.btn_zoom_minus);
        // 줌 레벨 변경
        mapView.setZoomLevel(7, true);
        btn_zoomPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 줌 인
                mapView.zoomIn(true);
            }
        });

        btn_zoomMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 줌 아웃
                mapView.zoomOut(true);
            }
        });
    }
}