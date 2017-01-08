package meriem.com.smartlight;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Activity1 extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> listchambres;

    // url to get all products list
    private static String url_all_chambres = "http://10.0.2.2:8080/get_all_chambres.php";

    // JSON Node names
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_CHAMBRES = "listechambre";
    public static final String TAG_PID = "id_chambre";
    public static final String TAG_NOM = "nom_chambre";
    public static final String TAG_NBRL = "nombre_lampes";
    public static final String TAG_NBRR = "nombre_rideaux";

    // products JSONArray
    JSONArray chambres=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity1);

        // Hashmap for ListView


        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        listchambres = new ArrayList<HashMap<String, String>>();
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen


    }

    // Response from Edit Product Activity
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

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity1.this);
            pDialog.setMessage("Loading products. Please wait...");
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
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_chambres, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    chambres = json.getJSONArray(TAG_CHAMBRES);

                    // looping through All Products
                    for (int i = 0; i < chambres.length(); i++) {
                        JSONObject c = chambres.getJSONObject(i);

                        // Storing each json item in variable
                        String id_chambre = c.getString(TAG_PID);
                        String nom_chambre = c.getString(TAG_NOM);
                        String nombre_lampes = c.getString(TAG_NBRL);
                        String nombre_rideaux = c.getString(TAG_NBRR);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id_chambre);
                        map.put(TAG_NOM, nom_chambre);
                        map.put(TAG_NBRL, nombre_lampes);
                        map.put(TAG_NBRR, nombre_rideaux);

                        // adding HashList to ArrayList
                        listchambres.add(map);
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
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListViewAdapter adapter = new ListViewAdapter(Activity1.this, listchambres);
                    setListAdapter(adapter);
                }
            });

        }

    }

    }


