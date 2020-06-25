package com.example.weatherapp.Views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.R;
import com.example.weatherapp.Views.Libs.SpacesItemDecoration;
import com.example.weatherapp.Views.ViewModels.WeatherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    @BindView(R.id.rv_weather)
    RecyclerView rcycView;
    @BindView(R.id.photobtn)
    FloatingActionButton photobtn;
    WeatherAdapter weatherAdapter;
    List<Uri> albumList;
    String weatherTxt = "";
    double lat;
    double longt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        albumList = new ArrayList<>();
        rcycView.setLayoutManager(new GridLayoutManager(this, 3));
        rcycView.addItemDecoration(new SpacesItemDecoration(3, 50, false));
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImageFromStorage();
    }

    void init(){
        getCurrentLocation();
        final WeatherViewModel viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        viewModel.getLongLat(longt, lat);
        viewModel.getCurrentWeather();
        getWeatherDetails(viewModel);
        photobtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
    }

    private void loadImageFromStorage() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        Log.d("dirpath ", Uri.fromFile(directory).toString());
        File[] files = directory.listFiles();
        Log.d("Size", files.length + "");
        albumList.clear();
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", files[i].getName());
            File file = new File(directory, files[i].getName());
            Uri uri = Uri.fromFile(file);
            albumList.add(uri);
        }
        weatherAdapter = new WeatherAdapter(getApplicationContext(), albumList);
        rcycView.setAdapter(weatherAdapter);

    }
    void getCurrentLocation(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            onLocationChanged(location);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }
    }


    public void getWeatherDetails(WeatherViewModel viewModel) {
        viewModel.getWeatherLiveData().observe(this, weatherModel ->
                weatherTxt = "Time zone :" + weatherModel.getTimezone() + "\n Temp :" +
                weatherModel.getCurrent().getTemp() + "\n Weather :" +
                weatherModel.getCurrent().getWeather().get(0).getDescription());
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Intent myIntent = new Intent(MainActivity.this, PhotoActivity.class);
            myIntent.putExtra("key", photo);
            myIntent.putExtra("WeatherTxtKey", weatherTxt);
            MainActivity.this.startActivity(myIntent);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        longt = location.getLongitude();
        Log.d("Latitude", lat + "" + longt);
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");
    }
    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enable");
    }
    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }
}