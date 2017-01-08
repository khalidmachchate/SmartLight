package meriem.com.smartlight;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Activity3 extends Activity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText s1,s2,s3,s4;
    String nom_chambre,id_chambre,nombre_lampes,nombre_rideaux;


    private static String url_create_product = "http://10.0.2.2:8080/create_chambre.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);

        // Edit Text
        s1 = (EditText) findViewById(R.id.id_chambre3);
        s2 = (EditText) findViewById(R.id.nom_chambre3);
        s3 = (EditText) findViewById(R.id.nombre_lampes3);
        s4 = (EditText) findViewById(R.id.nombre_rideaux3);

        Button button5 = (Button) findViewById(R.id.vld);

        // button click event
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                id_chambre = s1.getText().toString();
                nom_chambre = s2.getText().toString();
                nombre_lampes = s3.getText().toString();
                nombre_rideaux = s4.getText().toString();

                new CreateNewProduct().execute();
                Intent i = new Intent(Activity3.this,Activity1.class);
                startActivity(i);

            }
        });


        Button button11 = (Button) findViewById(R.id.retour);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity3.this, Activity2.class);
                startActivity(i);
            }
        });

        Button button10 = (Button) findViewById(R.id.cancel);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setText("");
                s2.setText("");
                s3.setText("");
                s4.setText("");

            }
        });

        // Create button


    }

    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity3.this);
            pDialog.setMessage("enregistrer chambre..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id_chambre", id_chambre));
            params.add(new BasicNameValuePair("nom_chambre", nom_chambre));
            params.add(new BasicNameValuePair("nombre_lampes", nombre_lampes));
            params.add(new BasicNameValuePair("nombre_rideaux", nombre_rideaux));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), Activity1.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
