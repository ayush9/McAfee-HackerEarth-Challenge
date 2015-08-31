package com.example.mcaffe;
import com.example.mcaffe.R;
import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends ListActivity {
 
    private ProgressDialog pDialog;
 
    // URL to get contacts JSON
    private static String url = "http://mcafee.0x10.info/api/app?type=json";
 
    // contacts JSONArray
    JSONArray contacts = null;
 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        contactList = new ArrayList<HashMap<String, String>>();
 
        ListView lv = getListView();
 int a=1;
        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name))
                        .getText().toString();
                String cost = ((TextView) view.findViewById(R.id.email))
                        .getText().toString();
                String description = ((TextView) view.findViewById(R.id.mobile))
                        .getText().toString();
                
               //  Toast.makeText(getApplicationContext(),""+(contactList.get(position)).get("name"),1).show();
                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        MyActivity.class);
                in.putExtra("hashMap", contactList.get(position));
                  //in.putStringArrayListExtra("stock_list", con);
                //  in.putExtra(TAG_NAME, name);
     //           in.putExtra(type, cost);
       //         in.putExtra(users, description);
               // in.putExtra(description, description);
         //       in.putExtra(url1, description);
           //     in.putExtra(last_update, description);
               startActivity(in);
 
            }
        });
 
        // Calling async task to get json
        new GetContacts().execute();
    }
 
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                   // JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    contacts = new JSONArray(jsonStr);
 
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                         
                        String name = c.getString("name");
                        String imagee = c.getString("imagee");
                        String type = c.getString("type");
                        String price = c.getString("price");
                        String rating = c.getString("rating");
                        String users = c.getString("users");
                        String last_update = c.getString("last_update");
                        String description = c.getString("description");
                        String url1 = c.getString("url");
                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        contact.put("name", name);
                        contact.put("imagee", imagee);
                        contact.put("type", type);
                        contact.put("price", price);
                        contact.put("rating", rating);
                        contact.put("users", users);
                        contact.put("last_update", last_update);
                        contact.put("description", description);
                        contact.put("url", url1);
 
                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] { "name", "rating",
                    		"price" }, new int[] { R.id.name,
                            R.id.email, R.id.mobile });
 
            setListAdapter(adapter);
        }
 
    }
 
}