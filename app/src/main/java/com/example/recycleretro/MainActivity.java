package com.example.recycleretro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    UserService userService;
    String TAG="user";
    RecyclerView recyclerView;
    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> email=new ArrayList<>();;
    ArrayList<String> name=new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        userService = Apiutils.getUserService();
        Call<ResponseBody> call = userService.login();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json_obj=response.body().string();
                    Log.e(TAG, "Response" +json_obj);

                    try {
                        // get JSONObject from JSON file
                        JSONArray jsonArray=new JSONArray(json_obj);
                        JSONObject obj = jsonArray.getJSONObject(0);
                        // fetch JSONObject named employee
                        JSONArray emp = obj.getJSONArray("users");
                        // get employee name and salary
                        for(int i=0;i< emp.length();i++) {
                            JSONObject employee = emp.getJSONObject(i);
                            name.add(employee.getString("name"));
                            id.add(employee.getString("id"));
                            email.add(employee.getString("email"));
                        }
                        // salary = employee.getString("salary");
                        // set employee name and salary in TextView's


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,name,email,id);
                    recyclerView.setAdapter(customAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "IOException:" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure:" + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }
}