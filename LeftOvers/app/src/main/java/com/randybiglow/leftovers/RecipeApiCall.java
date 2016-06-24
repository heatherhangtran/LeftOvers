package com.randybiglow.leftovers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RecipeApiCall {
    private static RecipeApiCall instance;
    private static RecipeCallback callback;
    private static String title, source_url, image_url;

    private RecipeApiCall() {
        //Method needs to be empty.
    }

    public static RecipeApiCall getInstance(RecipeCallback call) {
        callback = call;
        if (instance == null) {
            instance = new RecipeApiCall();
        }
        return instance;
    }

    public static void doRequest(String...ingredients) {
        AsyncHttpClient client = new AsyncHttpClient();

        StringBuilder sb = new StringBuilder();
        for(String ingredient : ingredients){
            sb.append(ingredient+",");
        }
        String q = sb.toString();
        q = q.replaceAll(",$", "");

        String url = "http://food2fork.com/api/search/?key=" + RecipeAPIData.RECIPE_API_KEY + "&format=json&nojsoncallback=1&q="+q;

        client.get(url, null,
                new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        Log.e("<><><><>", "onSuccess");

                        String recipeResult = null;

                        try {
                            JSONArray results = response.getJSONArray("recipes");
                            JSONObject post = results.getJSONObject(0);
                            title = post.getString("title");
                            image_url = post.getString("image_url");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println(title + image_url);
                        callback.handleCallback(title, image_url);
                    }
                });

        System.out.println(url);
    }
}
