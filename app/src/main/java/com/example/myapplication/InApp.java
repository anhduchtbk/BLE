package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.data.LoginDataSource;

import java.util.ArrayList;
import java.util.List;

public class InApp extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    private BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
//    private final int PERMISSION_REQUEST_CODE = 123;
//    private ArrayList<String> t = new ArrayList<>();
//    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
//        @Override
//        public void onLeScan(BluetoothDevice device, int i, byte[] bytes) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // Quyền truy cập BLE chưa được cấp, yêu cầu người dùng cho phép
////                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
//                        System.out.println("loi roi duc anh oi");
//                    } else {
//                        String deviceName = device.getName();
//                        t.add(deviceName);
//                        Log.d(TAG, "Device found: " + deviceName);
//                        System.out.println(deviceName);
//                    }
//                }
//            });
//        }
//    };
    private BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    private boolean scanning;
    private Handler handler = new Handler();
    private static final long SCAN_PERIOD = 10000;
    private List<BluetoothDevice> leDeviceListAdapter = new ArrayList<>();
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    leDeviceListAdapter.add(result.getDevice());
//                    leDeviceListAdapter.notifyDataSetChanged();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_in_app);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
//        }
//        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        } else {
//            bluetoothAdapter.startLeScan(mLeScanCallback);
//        }
        scanLeDevice();

        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // click handling code
                try {
                    String username = getIntent().getStringExtra("Username");
                    String url = "https://local.estech777.com/v1/guest/local/ble-access-key";
//                System.out.println(username);
                    class PostRequestApi extends AsyncTask<Void, Void, Boolean> {
                        private LoginDataSource t;

                        public PostRequestApi(LoginDataSource t) {
                            this.t = t;
                        }

                        @SuppressLint("WrongThread")
                        @Override
                        protected Boolean doInBackground(Void... voids) {
//                            System.out.println(username);
                            t.sendGet(url, username);
                            return true;
                        }

                    }
                    LoginDataSource t = new LoginDataSource();
                    PostRequestApi postRequestApi = new PostRequestApi(t);
                    postRequestApi.execute();
                    postRequestApi.get();
                    check();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling code
                try {
                    String username = getIntent().getStringExtra("Username");
                    String url = "https://local.estech777.com/v1/guest/local/elevator-access-key";
//                System.out.println(username);
                    class PostRequestApi extends AsyncTask<Void, Void, Boolean> {
                        private LoginDataSource t;

                        public PostRequestApi(LoginDataSource t) {
                            this.t = t;
                        }

                        @SuppressLint("WrongThread")
                        @Override
                        protected Boolean doInBackground(Void... voids) {
//                            System.out.println(username);
                            t.sendGet(url, username);
                            return true;
                        }

                    }
                    LoginDataSource t = new LoginDataSource();
                    PostRequestApi postRequestApi = new PostRequestApi(t);
                    postRequestApi.execute();
                    postRequestApi.get();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void scanLeDevice() {

        if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    bluetoothLeScanner.stopScan(leScanCallback);
                }
            }, SCAN_PERIOD);

            scanning = true;
            bluetoothLeScanner.startScan(leScanCallback);
        } else {
            scanning = false;
            bluetoothLeScanner.stopScan(leScanCallback);
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Quyền truy cập BLE đã được cấp, bắt đầu quét thiết bị
//                    if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    } else {
//                        bluetoothAdapter.startLeScan(mLeScanCallback);
//                    }
//                } else {
//                    // Quyền truy cập BLE không được cấp, hiển thị thông báo và yêu cầu người dùng cấp quyền
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//        }
//    }
    @SuppressLint("MissingPermission")
    public void check() {
        System.out.print("name device");
        for(int i = 0; i < this.leDeviceListAdapter.size(); i++) {
            System.out.println(this.leDeviceListAdapter.get(i).getName());
        }
    }

}