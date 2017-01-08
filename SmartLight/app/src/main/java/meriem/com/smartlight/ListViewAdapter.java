package meriem.com.smartlight;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView id_chambre, nom_chambre,nombre_lampes, nombre_rideaux;

        ImageView image;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        id_chambre = (TextView) itemView.findViewById(R.id.id_chambre2);
        nom_chambre = (TextView) itemView.findViewById(R.id.nom_chambre2);
        nombre_lampes = (TextView) itemView.findViewById(R.id.nombre_lampes2);
        nombre_rideaux = (TextView) itemView.findViewById(R.id.nombre_rideaux2);

        // Capture position and set results to the TextViews
        id_chambre.setText(resultp.get(Activity1.TAG_PID));
        nom_chambre.setText(resultp.get(Activity1.TAG_NOM));
        nombre_lampes.setText(resultp.get(Activity1.TAG_NBRL));
        nombre_rideaux.setText(resultp.get(Activity1.TAG_NBRR));

        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("id_chambre", resultp.get(Activity1.TAG_PID));
                // Pass all data country
                intent.putExtra("nom_chambre", resultp.get(Activity1.TAG_NOM));
                // Pass all data population
                intent.putExtra("nombre_lampes",resultp.get(Activity1.TAG_NBRL));
                // Pass all data flag
                intent.putExtra("nombre_rideaux", resultp.get(Activity1.TAG_NBRR));
                context.startActivity(intent);


            }
        });
        return itemView;
    }
}