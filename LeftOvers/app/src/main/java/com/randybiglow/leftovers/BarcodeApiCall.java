package com.randybiglow.leftovers;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class BarcodeApiCall {
    private static BarcodeApiCall instance;
    public static BarcodeCallback callback;
    private String productName, imageurl;

    public BarcodeApiCall() {
        //Method needs to be empty
    }

    public static BarcodeApiCall getInstance(BarcodeCallback call) {
        callback = call;
        if (instance == null) {
            instance = new BarcodeApiCall();
        }
        return instance;
    }

    public void doRequest(String input){
        Log.d("<><><><>", "doRequest");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=" + BarcodeApiData.BARCODE_API_KEY + "&upc=" + input;

        client.get(url, null,
                new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        String scannedResult = null;

                        try {
                            JSONObject results = response.getJSONObject("0");
                            productName = results.getString("productname");
                            //imageurl = results.getString("imageurl");
                            scannedResult = productName;

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                        if(null != scannedResult) callback.barcodeCallback(scannedResult);
                    }
                }
        );
    }
}
