package com.fahamin.transcomtest.ui.gps;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fahamin.transcomtest.MainActivity;
import com.fahamin.transcomtest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsFragment extends Fragment {

    private GpsViewModel mViewModel;
    TextView addressTV, cityTV, subCityTV, posterCodeTV, divisionTV, countryTV, countryCodeTV, latitudeTV, longitudeTV;
    LinearLayout refreshLayout, mapLayout;

    ProgressDialog progressDialog;

    private FusedLocationProviderClient mFusedLocationClient;
    LocationManager lm;
    LocationManager locationManager;

    double lat, lng;

    String longitude, latitude;

    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public LocationListener locationListener;

    public String cityName;
    public String address;
    public String subCity;
    public String posterCode;
    public String division;
    public String country;
    public String countryCode;

    int i = 0;
    public static GpsFragment newInstance() {
        return new GpsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gps_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GpsViewModel.class);
        // TODO: Use the ViewModel


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGeoDataClient = Places.getGeoDataClient(getContext(), null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getContext(), null);


        addressTV = (TextView) view.findViewById(R.id.addressTV);
        cityTV = (TextView) view.findViewById(R.id.cityTV);
        subCityTV = (TextView) view.findViewById(R.id.subcityTV);
        posterCodeTV = (TextView) view.findViewById(R.id.posterCodeTV);
        divisionTV = (TextView) view.findViewById(R.id.divisionTV);
        countryTV = (TextView) view.findViewById(R.id.countryTV);
        countryCodeTV = (TextView) view.findViewById(R.id.countryCodeTV);
        latitudeTV = (TextView) view.findViewById(R.id.latitudeTV);
        longitudeTV = (TextView) view.findViewById(R.id.longitudeTV);

        refreshLayout = (LinearLayout) view.findViewById(R.id.refreshLayout);
        init(view);
        checkLocationPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestLocationPermission();
        }

    }

    private void init(View view) {


        //  mapLayout = (LinearLayout) view.findViewById(R.id.mapLayout);


        refreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);


                String ss = (address + " " + subCity + " " + cityName + " Postal Code : " + posterCode + " " + division + "lat: " + lat + "lng: " + lng);

                intent.putExtra("meskey", ss);
                startActivity(intent);

                progressDialog.dismiss();

            }
        });


      /*  mapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Please wait.....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (lat != 0 && lng != 0) {

                    Intent intent = new Intent(getContext(), MapsActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivity(intent);

                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Please refresh the location first !!!", Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    }


    @Override
    public void onResume() {
        super.onResume();


        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            fullLocationService();

            // getPlaceResult();


        }


    }

    private void fullLocationService() {


        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while fetching data from GPS .......");
            progressDialog.setCancelable(false);
            progressDialog.show();


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


            final LocationListener locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                progressDialog.dismiss();

                return;
            }


            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {


                            if (location != null) {


                                lat = location.getLatitude();
                                lng = location.getLongitude();

                                setLocation(location);


                            } else {


                                if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                        return;
                                    }


                                    locationManager.requestLocationUpdates(
                                            LocationManager.GPS_PROVIDER, 5000, 10, locationListener);


                                } else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                                    locationManager.requestLocationUpdates(
                                            LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);

                                }


                            }
                        }
                    });


        } else {

            checkGpsStatus();
            Toast.makeText(getContext(), "GPS off", Toast.LENGTH_SHORT).show();
        }

    }


    public void checkGpsStatus() {

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("GPS is not enabled ");
            dialog.setIcon(android.R.drawable.ic_menu_mylocation);
            dialog.setMessage("Please turn on GPS first.");
            dialog.setPositiveButton("Ok",

                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                            fullLocationService();

                        }
                    });

            dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {


                }
            });
            dialog.show();
        }
    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {


            longitude = String.valueOf(loc.getLongitude());
            latitude = String.valueOf(+loc.getLatitude());

            lat = loc.getLatitude();
            lng = loc.getLongitude();

            //   mustExecute();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


    }

    public boolean checkLocationPermission() {

        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //  Log.e("Permission error", "You have permission");
                return true;
            } else {

                //  Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{(Manifest.permission.ACCESS_FINE_LOCATION)}, 1244);
                Toast.makeText(getContext(), "Need to Permission ", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            //  Log.e("Permission error", "You already have the permission");
            return true;
        }
    }

  /*  public boolean checkLocationPermission() {

       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


            return false;

        } else {

            return true;
        }
    }*/


    private void requestLocationPermission() {

        boolean foreground = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            boolean background = ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        //   Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                        continue;
                    } else {
                        Toast.makeText(getContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        // Toast.makeText(getApplicationContext(), "Background  location permission allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Background  location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }
        }
    }

    private void handleLocationUpdates() {
        //foreground and background
        // Toast.makeText(getApplicationContext(),"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
    }

    private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        //  Toast.makeText(getApplicationContext(),"Start foreground location updates",Toast.LENGTH_SHORT).show();
    }


    public void setLocation(Location loc) {

        String longitude = String.valueOf(loc.getLongitude());
        String latitude = String.valueOf(+loc.getLatitude());


        /*------- To get city name from coordinates -------- */
        cityName = null;
        address = null;
        subCity = null;
        posterCode = null;
        division = null;
        country = null;
        countryCode = null;


        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {

                cityName = addresses.get(0).getLocality();
                address = addresses.get(0).getFeatureName();
                subCity = addresses.get(0).getSubLocality();
                posterCode = addresses.get(0).getPostalCode();
                division = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                countryCode = addresses.get(0).getCountryCode();

                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        addressTV.setText(address + ", " + subCity + ", " + cityName);

        // addressTV.setText(address);
        //  cityTV.setText(cityName);
        //  subCityTV.setText(subCity);
        posterCodeTV.setText(posterCode);
        divisionTV.setText(division);
        countryTV.setText(country);
        countryCodeTV.setText(countryCode);
        latitudeTV.setText(latitude);
        longitudeTV.setText(longitude);

        progressDialog.dismiss();

    }

  

    
}




