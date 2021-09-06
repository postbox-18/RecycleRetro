package com.example.recycleretro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
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

    private ArrayList<ResObj> resObjsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        userService = Apiutils.getUserService();
        Call<ResponseBody> call = userService.login();
        ProgressDialog progressBar=new ProgressDialog(this);
        progressBar.setMessage("Loading");
        progressBar.show();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.dismiss();
                String json_obj= null;
                try {
                    json_obj = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "Response" +json_obj);

                    try {
                        // get JSONObject from JSON file
                        JSONArray jsonArray=new JSONArray(json_obj);
                        JSONObject obj = jsonArray.getJSONObject(0);
                        // fetch JSONObject named employee
                        JSONArray emp = obj.getJSONArray("users");
                        for(int i=0;i<10000;i++)
                        {
                            for(int j=0;j<10000;j++)
                            {
                                for(int k=0;k<10000;k++)
                                {

                                }
                            }
                        }

                        // get employee name and salary
                       for(int i=0;i< emp.length();i++) {
                            JSONObject employee = emp.getJSONObject(i);
                            String name=employee.getString("name");
                            String email=employee.getString("email");
                           String id=employee.getString("id");
                          //  name.add(employee.getString("name"));
                            //id.add(employee.getString("id"));
                            //email.add(employee.getString("email"));
                           ResObj resObj=new ResObj(name,email,id);
                           resObjsList.add(resObj);
                        }
                        // salary = employee.getString("salary");
                        // set employee name and salary in TextView's

                        CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,resObjsList);
                        recyclerView.setAdapter(customAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG,"catch error"+e.getMessage());
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
