package com.example.recycleretro;

public class Apiutils {
    public static final String base_url="https://6131f725ab7b1e001799b275.mockapi.io/";
    public static UserService getUserService()
    {
        return Retrofitclient.getClient(base_url)
                .create(UserService.class);
    }
}
