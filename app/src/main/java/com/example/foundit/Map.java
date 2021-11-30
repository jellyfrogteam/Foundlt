package com.example.foundit;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // java code
        MapView mapView = new MapView(this);
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

        MapPOIItem marker = new MapPOIItem();
        mapView.setMapCenterPoint(markerPoint, true);
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(markerPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);
    }
}