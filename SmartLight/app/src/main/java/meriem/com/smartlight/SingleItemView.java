package meriem.com.smartlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleItemView extends Activity {


    String sharebody;
    // Declare Variables
    String id_chambre;
    String nom_chambre;
    String nombre_lampes;
    String nombre_rideaux;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
        Button btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SingleItemView.this,typecontrol.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        // Get the result of rank
        id_chambre = i.getStringExtra("id_chambre");
        // Get the result of country
        nom_chambre = i.getStringExtra("nom_chambre");
        // Get the result of population
        nombre_lampes = i.getStringExtra("nombre_lampes");
        // Get the result of flag
        nombre_rideaux = i.getStringExtra("nombre_rideaux");


        // Locate the TextViews in singleitemview.xml
        TextView id = (TextView) findViewById(R.id.textView22);
        TextView nom = (TextView) findViewById(R.id.nom1);
        TextView lampe = (TextView) findViewById(R.id.nbrl1);
        TextView rideau = (TextView) findViewById(R.id.nbrr1);



        // Locate the ImageView in singleitemview.xml


        // Set results to the TextViews
        id.setText(id_chambre);
        nom.setText(nom_chambre);
        lampe.setText(nombre_lampes);
        rideau.setText(nombre_rideaux);


    }

}