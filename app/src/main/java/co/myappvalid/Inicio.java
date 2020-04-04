package co.myappvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Inicio extends AppCompatActivity {

    /***Objectos***/
    Spinner spnPais;
    /******/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        spnPais = (Spinner) findViewById(R.id.spnPais);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPais.setAdapter(adapter);
        Button btnArtistas = (Button) findViewById(R.id.btnArtistas);
        Button btnCanciones = (Button) findViewById(R.id.btnCanciones);
        btnArtistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avanzar(0);
            }
        });
        btnCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avanzar(1);
            }
        });
        iniciar();
    }

    void iniciar(){
        String sql = "SELECT pais FROM tbl_pais_seleccionado";
        String pais = Negocio.listarString(sql,this);
        if(!pais.equals("")) {
            String[] paises = getResources().getStringArray(R.array.countries);
            for (int i = 0; i < paises.length; i++) {
                String p = paises[i];
                if (p.equals(pais)) {
                    spnPais.setSelection(i);
                    break;
                }
            }
        }
    }

    void avanzar(int opcion){
        String country = spnPais.getSelectedItem().toString();
        String url = "";
        Negocio.ejecutarSQL("DELETE FROM tbl_pais_seleccionado",getApplicationContext());
        Negocio.ejecutarSQL("INSERT INTO tbl_pais_seleccionado(pais) VALUES('"+country+"')",getApplicationContext());

        if(opcion==0)url = VariableGenerales.url_artistas + "&country=" + country;
        else url = VariableGenerales.url_canciones + "&country=" + country;
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("url",url);
        i.putExtra("destino",opcion);
        i.putExtra("pais",country);
        startActivity(i);
        finish();
    }
}
