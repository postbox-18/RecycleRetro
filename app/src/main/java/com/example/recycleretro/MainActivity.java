package com.example.recycleretro;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserService userService;
    String TAG = "userexecuter";
    RecyclerView recyclerView;

    private ArrayList<ResObj> resObjsList = new ArrayList<>();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Do some work that takes 50 milliseconds
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
    private final Executor otherBackgroundOperationsExecutor = Executors.newFixedThreadPool(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        userService = Apiutils.getUserService();
        Call<ResponseBody> call = userService.login();
        ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.setCancelable(false);
        progressBar.show();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //  progressBar.dismiss();
                String json_obj = null;
                try {
                    json_obj = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "Response" + json_obj);

                try {
                    // get JSONObject from JSON file
                    JSONArray jsonArray = new JSONArray(json_obj);
                    JSONObject obj = jsonArray.getJSONObject(0);
                    // fetch JSONObject named employee
                    JSONArray emp = obj.getJSONArray("users");

                    otherBackgroundOperationsExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < emp.length(); i++) {
                                try {
                                JSONObject employee = emp.getJSONObject(i);
                                String name = null;

                                    name = employee.getString("name");

                                String email = employee.getString("email");
                                String id = employee.getString("id");
                                //  name.add(employee.getString("name"));
                                //id.add(employee.getString("id"));
                                //email.add(employee.getString("email"));
                                ResObj resObj = new ResObj(name, email, id);
                                resObjsList.add(resObj);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                for(int k=0;k<2;k++)
                                {
                                    for(int j=0;j<2;j++)
                                    {
                                        for (int l=0;l<2;l++)
                                        {
                                            Log.i(TAG,"For loop k="+k+"l="+l+"j="+j);
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }

                                Handler handler=new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.dismiss();
                                        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, resObjsList);
                                        recyclerView.setAdapter(customAdapter);
                                    }
                                });

                            }

                        }
                    });







                    // get employee name and salary

                    // salary = employee.getString("salary");
                    // set employee name and salary in TextView's



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "catch error" + e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure:" + t.getMessage());
                progressBar.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }
}
