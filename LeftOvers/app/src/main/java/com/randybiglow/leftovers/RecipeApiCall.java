package com.randybiglow.leftovers;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RecipeApiCall {
    private static RecipeApiCall instance;
    private static RecipeCallback callback;
    private String title, source_url, image_url;

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

    public void doRequest() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
                "http://food2fork.com/api/search/?key=" + RecipeAPIData.RECIPE_API_KEY + "&format=json&nojsoncallback=1",
                null,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        String recipeResult = null;

                        try {
                            JSONArray results = response.getJSONArray("recipes");
                            JSONObject post = results.getJSONObject(0);
                            title = post.getString("title");
                            source_url = post.getString("source_url");
                            image_url = post.getString("image_url");
                            //recipeResult = image_url;
                            recipeResult = title + " /n" + source_url;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.handleCallback(recipeResult);
                    }
                }
        );
    }
}
