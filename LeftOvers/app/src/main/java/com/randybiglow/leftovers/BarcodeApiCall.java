package com.randybiglow.leftovers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BarcodeApiCall {
    private static BarcodeApiCall instance;
    private static BarcodeCallback callback;
    private int index;
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

    public void doRequest(){
        Log.d("<><><><>", "doRequest");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=" + BarcodeApiData.BARCODE_API_KEY + "&upc=737628005000";
                //"http://www.searchupc.com/handlers/upcsearch.ashx?request_type=3&access_token=6029921A-4A03-4937-9E35-FD3A0628CB81&upc=737628005000";

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

                        callback.barcodeCallback(scannedResult);
                    }
                }
        );
    }
}
