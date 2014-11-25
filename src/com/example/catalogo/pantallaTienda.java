package com.example.catalogo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class pantallaTienda extends Activity {
	int id_Tienda;
	TextView textTienda, descTienda;
	BaseDatosTienda tiendadb;
	Spinner userSpinner;
	String[] descripOferta;
	String[] nomOferta;
	int[] idsOferta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tienda);
		
		tiendadb = new BaseDatosTienda(this, null, null);
		 
		// Creamos un Intent para poder recibir la llamada de la primera actividad así como la tienda a mostrar.
	    Intent intent = getIntent();
	    id_Tienda = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_VALOR));	    

	    // Capturamos los objetos gráficos que vamos a usar
        textTienda = (TextView) findViewById(R.id.tituloTienda);
        descTienda = (TextView) findViewById(R.id.descripcionTienda);
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        userSpinner = (Spinner) findViewById(R.id.spinner1);
        
        // Llenamos los textos con los nombres recuperados de la base de datos de tiendas.        
        int id = tiendadb.recuperarTienda(id_Tienda).getTienda_ID();
        String idTienda = id+"";
        String nombreTienda = tiendadb.recuperarTienda(id_Tienda).getTienda_nombre();
        String descripcion = tiendadb.recuperarTienda(id_Tienda).getTienda_descripcion();
        String imagenTienda = tiendadb.recuperarTienda(id_Tienda).getTienda_imagen();
        
        // Recuperamos las ofertas relacionadas de la tienda cargada
        idsOferta = new int[tiendadb.getOfertasTienda(idTienda).size()];
        nomOferta = new String[tiendadb.getOfertasTienda(idTienda).size()];
        descripOferta = new String[tiendadb.getOfertasTienda(idTienda).size()];        
        for (int i = 0; i < tiendadb.getOfertasTienda(idTienda).size(); i++) {
        	
        	idsOferta[i] = tiendadb.getOfertasTienda(idTienda).get(i).getOferta_id();
        	nomOferta[i] = tiendadb.getOfertasTienda(idTienda).get(i).getOferta_nombre();
        	descripOferta[i] = tiendadb.getOfertasTienda(idTienda).get(i).getOferta_descripcion();		
        	
		}
        
        // Cargamos el spinner con los datos recuperados. Utilizamos un layout propio para el spinner que tan solo contiene un TextView
        ArrayAdapter <String> userAdapter = new ArrayAdapter <String> (this, R.layout.text,nomOferta );      
        userSpinner.setAdapter(userAdapter);		
        
        userSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				Toast.makeText(pantallaTienda.this , descripOferta[(int) userSpinner.getSelectedItemId()] ,Toast.LENGTH_LONG).show();	
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {	
			}
			
        });

        // Cargamos los valores de la base de datos en la actividad.
        textTienda.setText(nombreTienda);
        descTienda.setText(descripcion);
        image.setImageResource(getResources().getIdentifier(imagenTienda, "drawable", getApplicationContext().getPackageName()));
        
	}
}
