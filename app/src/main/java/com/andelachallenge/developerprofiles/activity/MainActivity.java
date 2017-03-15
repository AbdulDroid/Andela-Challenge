package com.andelachallenge.developerprofiles.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.andelachallenge.developerprofiles.R;
import com.andelachallenge.developerprofiles.adapter.ProfileAdapter;
import com.andelachallenge.developerprofiles.model.Items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public ListView listView;
    public ArrayList<Items> listitems;
    public ProfileAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://api.github.com/search/users?q=java+location:lagos";
        listitems = new ArrayList<Items>();
        downloader(url);


    }

    public void downloader(String url){

        new Download().execute(url);
    }

    class Download extends AsyncTask<String, Void, String>{

        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        String content;
        boolean error = false;

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Fetching Data... Please wait");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... urls) {

            URL website;
            StringBuilder response = null;
            try {

                /**
                 * Connecting to the API to fetch the data to be displayed on the listView
                 * and on the individual profile details page
                 */
                website = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) website.openConnection();
                connection.setRequestProperty("charset", "utf-8");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()));

                response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);

                in.close();
                content = response.toString();

            } catch (Exception e) {
                //Handling exceptions that might occur
                content = e.toString();
                error = true;
                cancel(true);
            }

            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            dialog.dismiss();
            Toast toast;

            if(error){

                //Displaying error message to the user if any

                toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP,25,400);
                toast.show();
            }else{
                //Process the response from the API
                get_data(s);
            }
        }
    }

    public void get_data(String data)
    {
        try {

            /**
             * Extracting data from the JSON response from the API
             */
            JSONObject jObj=new JSONObject(data);
            JSONArray items = jObj.getJSONArray("items");

            /**
             * Extracting individual profiles from the array of profiles from the JSON
             * response from the API
             */

            for (int i = 0 ; i < items.length() ; i++)
            {
                JSONObject obj=items.getJSONObject(i);

                /**
                 * Adding items extracted from the JSON response from the API
                 * to the Items array list.
                 */

                Items addItem=new Items();
                addItem.login = obj.getString("login");
                addItem.avatarUrl = obj.getString("avatar_url");
                addItem.htmlUrl = obj.getString("html_url");

                listitems.add(addItem);

                /**
                 * Initializing the ListView and assigning an adapter to it.
                 */
                listView = (ListView) findViewById(R.id.list_view);
                profileAdapter = new ProfileAdapter(this, listitems);
                listView.setAdapter(profileAdapter);
                listView.setTextFilterEnabled(true);

                /**
                 * Detecting user clicks on List view items and responding appropriately.
                 */

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Items currentItem = listitems.get(position);

                        /**
                         * Sending Intent to open Profile details of the user selected as directed in
                         * the challenge, and passing the user details to be used to populate the
                         * user profile activity
                         */

                        Intent intent = new Intent(MainActivity.this, ProfilesActivity.class);
                        intent.putExtra("login", currentItem.login);
                        intent.putExtra("avatar", currentItem.avatarUrl);
                        intent.putExtra("url", currentItem.htmlUrl);
                        startActivity(intent);

                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}