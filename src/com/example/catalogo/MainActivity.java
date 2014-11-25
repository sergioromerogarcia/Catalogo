package com.example.catalogo;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {
	TableRow row1, row2, row3, row4, row5;
	TextView textTienda1, textTienda2,textTienda3,textTienda4,textTienda5;
	BaseDatosTienda tiendadb;
	SQLiteDatabase db;
	//Constante para pasar el valor entrado a la segunda actividad.
	public final static String EXTRA_VALOR = "com.example.catalogo.VALOR";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Creamos el objeto tiendadb de nuestra clase de Base de datos que utilizamos para crear la base de datos así como 
        //los métodos principales para trabajar con los datos.
        //Los parámetros los pasamos a null ya que los valores los tenemos definidos como constantes en la clase de Base de Datos
        tiendadb = new BaseDatosTienda(this, null, null);
               
        if (!tiendadb.hayDatos()){
        	 //Insertamos las datos de las tiendas
          	tiendadb.insertarTienda("Grupo Bielas", "Empresa dedicada especialmente a la fabricación de bielas para todo tipos de bicicletas.", "biela");
            tiendadb.insertarTienda("Contender Bikes", "Bicicletas de uso urbano fabricadas en Barcelona y para todo el mundo.", "contender");
            tiendadb.insertarTienda("Ominium", "Bicicletas diseñadas para la competición. Geometrías personalizadas y componentes a la carta.","omnium");
            tiendadb.insertarTienda("Campy", "Directo desde Estados Unidos los mejores componentes de bicicletas al mejor precio.", "campy");
            tiendadb.insertarTienda("Keirin World", "Bicicletas directas de Japón, diseñadas para competir en los velódromos con la garnatía de NJS.", "keirin");
            //Insertamos los datos de las oferta para cada tienda
            tiendadb.insertarOferta("Juego de platos compatos Shimano DuraAce 7950", "El juego de platos y bielas de 10 velocidades Dura-Ace Hollowtech II proporciona un equilibrio óptimo entre peso y rigidez.",1);
            tiendadb.insertarOferta("Juego de dos platos y bielas de 11 vel. para ciclocrós", "El plato externo hueco HollowGlide ofrece mejoras enormes en rigidez al eliminar prácticamente la flexibilidad del plato externo y al mejorar enormemente el rendimiento de cambio.", 1);
            tiendadb.insertarOferta("Bicicleta pulida Bombtrack", "La Bombtrack Script, con tubos de aluminio 6061 y buen aspecto, está disponible en 2 colores.", 2);
            tiendadb.insertarOferta("Bicicleta Cinelli Bootleg", "Bicicleta de carretera y pista clásica de tan solo 8,1 kg. Con tubos Columbus (geometría monomarcha)", 2);
            tiendadb.insertarOferta("Bicicleta de carretera Felt", "Una auténtica bici de pista gracias a su pedalier mecanizado, punteras horizontales con placas de acero moldeado", 3);
            tiendadb.insertarOferta("Bicicleta de pista Cinelli - Vigorelli", "Cuadro de pista emblemático que se ha establecido como todo un clásico.", 3);
            tiendadb.insertarOferta("Manillar de carbono Syntace", "La reducción del alcance contribuye a una posición más compacta y facilita el acceso a las manetas de freno", 4);
            tiendadb.insertarOferta("Cubierta plegable de carretera Schwalbe", "Como diseñadores de cubiertas, Schwalbe señala que la Ultremo es la cúspide de su ambición.", 4);
            tiendadb.insertarOferta("3Rensho NJS keirin track", "Cuadro de acero con punteras metálicas", 5);
            tiendadb.insertarOferta("Kalavinka NJS keirin track frame", "Cuadro ganador de las tres últimas carreras en Osaka", 5);
            /*
             * Ya que hemos utilizado SQLiteOpenHelper no es posible guardar directamente la base de datos al almacenamiento externo.Para ello deberiamos 
             * utilizar las funciones de SQLiteDatabase e implementar manualmente la creación así como el upgrade.
             * Ya que la presente práctica tiene datos estáticos en base de datos he tomado la decisión de realizar una copia de la misma en 
             * el momento de la creación.
             */
            try {
    			tiendadb.copiarBD(this.getBaseContext());
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
         
        // Capturamos los objetos gráficos que vamos a usar
        textTienda1 = (TextView) findViewById(R.id.Tienda1);
        textTienda2 = (TextView) findViewById(R.id.Tienda2);
        textTienda3 = (TextView) findViewById(R.id.Tienda3);
        textTienda4 = (TextView) findViewById(R.id.Tienda4);
        textTienda5 = (TextView) findViewById(R.id.Tienda5);
        
        // Llenamos los textos con los nombres recuperados de la base de datos de tiendas.
        textTienda1.setText(tiendadb.recuperarTiendas().get(0).getTienda_nombre());
        textTienda2.setText(tiendadb.recuperarTiendas().get(1).getTienda_nombre());
        textTienda3.setText(tiendadb.recuperarTiendas().get(2).getTienda_nombre());
        textTienda4.setText(tiendadb.recuperarTiendas().get(3).getTienda_nombre());
        textTienda5.setText(tiendadb.recuperarTiendas().get(4).getTienda_nombre()); 
        
        //Definimos la propiedad onClick para todas las secciones (TableRow) del menu.
        row1 = (TableRow) findViewById(R.id.TableRow1);
        row1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generamos un Intent para poder comunicar ambas Activitys
				Intent intent = new Intent(MainActivity.this,pantallaTienda.class);
				intent.putExtra(EXTRA_VALOR,"1");
				startActivity(intent);
			}
		});
        
        row2 = (TableRow) findViewById(R.id.TableRow2);
        row2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generamos un Intent para poder comunicar ambas Activitys
				Intent intent = new Intent(MainActivity.this,pantallaTienda.class);
				intent.putExtra(EXTRA_VALOR,"2");
				startActivity(intent);
				
			}
		});
        
        row3 = (TableRow) findViewById(R.id.TableRow3);
        row3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generamos un Intent para poder comunicar ambas Activitys
				Intent intent = new Intent(MainActivity.this,pantallaTienda.class);
				intent.putExtra(EXTRA_VALOR,"3");
				startActivity(intent);
				
			}
		});
        row4 = (TableRow) findViewById(R.id.TableRow4);
        row4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generamos un Intent para poder comunicar ambas Activitys
				Intent intent = new Intent(MainActivity.this,pantallaTienda.class);
				intent.putExtra(EXTRA_VALOR,"4");
				startActivity(intent);
				
			}
		});
        row5 = (TableRow) findViewById(R.id.TableRow5);
        row5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Generamos un Intent para poder comunicar ambas Activitys
				Intent intent = new Intent(MainActivity.this,pantallaTienda.class);
				intent.putExtra(EXTRA_VALOR,"5");
				startActivity(intent);
				
			}
		});                            
        
    }
  

}
