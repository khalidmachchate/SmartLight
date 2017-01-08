package meriem.com.smartlight;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class typecontrol extends Activity {
    Button btn7,btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typecontrol);
        btn7 =(Button)findViewById(R.id.button7);
        btn8 =(Button)findViewById(R.id.button8);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(typecontrol.this,remoteprofile.class);
                startActivity(i);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(typecontrol.this,remoteprogress.class);
                startActivity(i);

            }
        });
    }


}
