package meriem.com.smartlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


public class MainActivity extends Activity {

     // Progress Dialog
     private ProgressDialog pDialog;

     // Creating JSON Parser object
     JSONParser jParser = new JSONParser();

     ArrayList<HashMap<String, String>> userList;

     // url to get all products list
     private static String url_all_products = "http://10.0.2.2:8080/get_all_users.php";

     // JSON Node names
     private static final String TAG_SUCCESS = "success";
     private static final String TAG_USER = "users";
     private static final String TAG_LOGIN = "login";
     private static final String TAG_MDP = "mdp";
     JSONArray users = null;
    EditText login,mdp;
     Button btnlog,create;
    public String searchkey1;
    public String searchkey2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = new ArrayList<HashMap<String, String>>();
        login =(EditText)findViewById(R.id.enreg1);
        mdp = (EditText)findViewById(R.id.enreg2);

        btnlog = (Button)findViewById(R.id.button);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchkey1 = login.getText().toString();
                searchkey2 = mdp.getText().toString();
                new LoadAllUsers().execute();
                Intent i = new Intent(MainActivity.this,Activity2.class);
                startActivity(i);
            }
        });
        create = (Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,enregistrement.class);
                startActivity(i);

            }
        });
        Intent myIntent1 = getIntent();
        // gets the arguments from previously created intent
        searchkey1 = myIntent1.getStringExtra("login");
        Intent myIntent2 = getIntent();
        // gets the arguments from previously created intent
        searchkey2 = myIntent2.getStringExtra("mdp");
    }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         // if result code 100
         if (resultCode == 100) {
             // if result code 100 is received
             // means user edited/deleted product
             // reload this screen again
             Intent intent = getIntent();
             finish();
             startActivity(intent);
         }

     }

     class LoadAllUsers extends AsyncTask<String, String, String> {

         /**
          * Before starting background thread Show Progress Dialog
          * */
         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pDialog = new ProgressDialog(MainActivity.this);
             pDialog.setMessage("Loading users. Please wait...");
             pDialog.setIndeterminate(false);
             pDialog.setCancelable(false);
             pDialog.show();
         }

         /**
          * getting All products from url
          * */
         protected String doInBackground(String... args) {
             // Building Parameters
             List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("login", searchkey1));
             params.add(new BasicNameValuePair("mdp", searchkey2));
             // getting JSON string from URL
             JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);


             // Check your log cat for JSON reponse
             Log.d("All users: ", json.toString());

             try {
                 // Checking for SUCCESS TAG
                 int success = json.getInt(TAG_SUCCESS);

                 if (success == 1) {
                     // products found
                     // Getting Array of Products
                     users = json.getJSONArray(TAG_USER);

                     // looping through All Products
                     for (int i = 0; i < users.length(); i++) {
                         JSONObject c = users.getJSONObject(i);


                     }
                 } else {
                     // no products found
                     // Launch Add New product Activity
                     Intent i = new Intent(getApplicationContext(),
                             Activity2.class);
                     // Closing all previous activities
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(i);
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }

             return null;
         }

         /**
          * After completing background task Dismiss the progress dialog
          * **/
         protected void onPostExecute(String file_url) {
             // dismiss the dialog after getting all products
             pDialog.dismiss();
             // updating UI from Background Thread


         }

     }



}
