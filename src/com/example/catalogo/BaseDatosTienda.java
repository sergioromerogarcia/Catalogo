/**
 * @author sergioromero
 * Clase Helper para manejar la base de datos y poder llamarla desde nuestra aplicación.
 * En lugar de importarla ya hecha, aprovecharemos esta clase para dar de lata los registros necesarios para la práctica.
 * Crearemos dos tablas relacionadas (1->n) donde cada tienda tendrá asociada 5 ofertas.
 */
package com.example.catalogo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class BaseDatosTienda extends SQLiteOpenHelper{
	//*********** Definición de la base de datos ************
	//Nombre de la base de datos que almacenará todas las tiendas
	static final String NOMBRE_BASEDATOS ="tiendas.sqlite";
	//Nombre de la tabla que almacenará el catálog de Tiendas
	static final String NOMBRE_TABLA_TIENDAS = "tiendas";
	//Nombre de la tabla que almacenará las ofertas de cada tienda
	static final String NOMBRE_TABLA_OFERTAS = "ofertas";
	//Versión de la Base de Datos. En el caso que debamos extenderla en el futuro deberemo cambiar la versión.
	static final int VERSION_BASEDATOS = 1;
	//Ubicación de la base de datos
	static final String DATABASE_FILE_PATH = Environment.getExternalStorageState();
	static final String Separador = File.separator;
	static final String Folder = "/catalogo/";
	static final String EXTERNAL_FILE_PATH = "/sdcard";
	//*******************************************************

	//*********** Definición tabla de tiendas ***************
	static final String tienda_ID = "_id";
	static final String tienda_nombre = "nombreTienda";
	static final String tienda_descripcion = "descripcionTienda";
	static final String tienda_imagen = "imagenTienda";
	//*******************************************************

	//********** Definición tabla de Ofertas ****************
	static final String oferta_id = "_id";
	static final String oferta_nombre = "nombreOferta";
	static final String oferta_descripcion = "descripcionOferta";
	static final String oferta_idTienda = "_idTienda"; //Tienda a la que pertenece la Oferta
	//*******************************************************

	//********** Sentencia SQL para crear la tabla de tiendas *****
	//Asignación de la primera columna como Clave única
	private static final String TABLA_TIENDAS = "CREATE TABLE "+ NOMBRE_TABLA_TIENDAS + 
			" (" + tienda_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			tienda_nombre +  " TEXT," + 
			tienda_descripcion + " TEXT," + 
			tienda_imagen + " TEXT)";

	//********** Sentencia SQL para crear la tabla ofertas *******
	//Asignamos la primera columna como clave única.
	//Utilizamos el campo tienda_id como clave externa con el fín de poder ligar ambas tablas, donde una tienda puede 
	//tener n ofertas asociadas.
	private static final String TABLA_OFERTAS = "CREATE TABLE "+ NOMBRE_TABLA_OFERTAS  +
			"("+ oferta_id +" INTEGER PRIMARY KEY AUTOINCREMENT," + 
			oferta_idTienda + " INTEGER, " + 
			oferta_nombre + " TEXT, " + 
			oferta_descripcion + " TEXT,FOREIGN KEY(" + oferta_idTienda + ") REFERENCES tienda(" + tienda_ID + "))";

	//**************************************************************** 

	public BaseDatosTienda(Context context, String name, CursorFactory factory) {
		//super(context,DATABASE_FILE_PATH + Separador + Folder + File.separator + NOMBRE_BASEDATOS, factory, VERSION_BASEDATOS);
		super(context,NOMBRE_BASEDATOS, factory, VERSION_BASEDATOS);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Ejecutamos las sentencias para la creación de las tablas, solo se ejecutará cuando sea necesario, es decir cunado no existan las tablas
		db.execSQL(TABLA_TIENDAS);
		db.execSQL(TABLA_OFERTAS);
	}
	/**
	 * Insertar Registros en la tabla de Tiendas
	 * @param id
	 * @param nombreTienda
	 * @param descTienda
	 * @param nombreImagen
	 */
	public void insertarTienda(String nombreTienda, String descTienda, String nombreImagen) {
		SQLiteDatabase db = getWritableDatabase();
		if(db != null){
			ContentValues valores = new ContentValues();
			valores.put(tienda_nombre, nombreTienda);
			valores.put(tienda_descripcion, descTienda);
			valores.put(tienda_imagen, nombreImagen);
			db.insert(NOMBRE_TABLA_TIENDAS, null, valores);
			db.close();   
		}
	}
	/**
	 * Insertar Registros en la tabla de Ofertas.
	 * @param id
	 * @param nombreOferta
	 * @param descOferta
	 * @param idTienda
	 */
	public void insertarOferta(String nombreOferta, String descOferta, int idTienda) {
		SQLiteDatabase db = getWritableDatabase();
		if(db != null){
			ContentValues valores = new ContentValues();
			valores.put(oferta_nombre, nombreOferta);
			valores.put(oferta_descripcion, descOferta);
			valores.put(oferta_idTienda, idTienda);
			db.insert(NOMBRE_TABLA_OFERTAS, null, valores);
			db.close();   
		}
	}
	/**
	 * Recuperamos todas las tiendas en objetos.
	 * @return Lista de tiendas, cada item es un objeto.
	 */
	public List<tienda> recuperarTiendas() {
	    SQLiteDatabase db = getReadableDatabase();
	    List<tienda> lista_tiendas = new ArrayList<tienda>();
	    //Definimos las columnas a recuperar.
	    String[] valores_recuperar = {tienda_ID, tienda_nombre, tienda_descripcion, tienda_imagen};
	    //Definimos un cursor para poder movernos por los registros de la tabla tienda.
	    Cursor c = db.query(NOMBRE_TABLA_TIENDAS, valores_recuperar, null, null, null, null, null, null);
	    //Movemos el cursor a la primera posición de la tabla.
	  
	    c.moveToFirst();	    
	    do {
	    	//Creamos el objeto con los datos del registro leido.
	    	tienda objtienda = new tienda(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
	    	//Añadimos el objeto a la lista.
	    	lista_tiendas.add(objtienda);
		} while (c.moveToNext());
	    
	    //Cerramos la base de datos
        db.close();
        //Cerramos el cursor
        c.close();
        
        return lista_tiendas;
	}
	/**
	 * Comprobación si hay datos en las tablas.
	 * @return
	 */
	public boolean hayDatos () {
		Boolean ret = false;
		
		SQLiteDatabase db = getReadableDatabase();
		//Definimos las columnas a recuperar.
	    String[] valores_recuperar = {tienda_ID, tienda_nombre, tienda_descripcion, tienda_imagen};
	    //Definimos un cursor para poder movernos por los registros de la tabla tienda.
	    Cursor c = db.query(NOMBRE_TABLA_TIENDAS, valores_recuperar, null, null, null, null, null, null);
	    if (c.moveToFirst()) ret=true;
	    
		return ret; 
	}
	
	public tienda recuperarTienda(int id) {
	    SQLiteDatabase db = getReadableDatabase();
	    String[] valores_recuperar = {tienda_ID, tienda_nombre, tienda_descripcion, tienda_imagen};
	    Cursor c = db.query(NOMBRE_TABLA_TIENDAS, valores_recuperar, "_id=" + id, null, null, null, null, null);
	    if(c != null) {
	        c.moveToFirst();
	    }
		tienda objtienda = new tienda(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
        db.close();
        c.close();
        return objtienda;
	}
	
	public List<oferta> recuperarOfertas() {
	    SQLiteDatabase db = getReadableDatabase();
	    List<oferta> lista_ofertas = new ArrayList<oferta>();
	    //Definimos las columnas a recuperar.
	    String[] valores_recuperar = {oferta_id, oferta_nombre, oferta_descripcion, oferta_idTienda};
	    //Definimos un cursor para poder movernos por los registros de la tabla tienda.
	    Cursor c = db.query(NOMBRE_TABLA_OFERTAS, valores_recuperar, null, null, null, null, null, null);
	    //Movemos el cursor a la primera posición de la tabla.
	    
	    c.moveToFirst();	    
	    do {
	    	//Creamos el objeto con los datos del registro leido.
	    	oferta objoferta = new oferta(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
	    	//Añadimos el objeto a la lista.
	    	lista_ofertas.add(objoferta);
		} while (c.moveToNext());
	    
	    //Cerramos la base de datos
        db.close();
        //Cerramos el cursor
        c.close();
        
        return lista_ofertas;
	}
	/**
	 * Método que reptorna las ofertas de la tienda pasada por parámetro.
	 * @param idTienda
	 * @return
	 */
	public List<oferta> getOfertasTienda(String idTienda){
		//Ligamos la tabla de tiendas y de ofertas medinante el _id
		String queryOfertas = "SELECT " + NOMBRE_TABLA_OFERTAS + "." + oferta_id + "," + oferta_nombre + "," + oferta_descripcion + "," + 
		oferta_idTienda + " from " + NOMBRE_TABLA_OFERTAS + "," + NOMBRE_TABLA_TIENDAS +" WHERE " + 
		NOMBRE_TABLA_TIENDAS + "." +tienda_ID + "=" + NOMBRE_TABLA_OFERTAS + "." + oferta_idTienda + " AND " + 
				NOMBRE_TABLA_TIENDAS+ "."+ tienda_ID + "=" + idTienda;
				
		SQLiteDatabase db = getReadableDatabase();
	    List<oferta> lista_ofertas = new ArrayList<oferta>();
	    //Utilizamos el método rawquery para facilitar el paso de argumentos de la SELECT
	    Cursor c = db.rawQuery(queryOfertas, null);

	    //Movemos el cursor a la primera posición de la tabla.	    
	    c.moveToFirst();	    
	    do {
	    	//Creamos el objeto con los datos del registro leido.
	    	oferta  objoferta = new oferta(c.getInt(0), c.getString(1), c.getString(2),c.getString(3));
	    	//Añadimos el objeto a la lista.
	    	lista_ofertas.add(objoferta);
		} while (c.moveToNext());
	    
	    //Cerramos la base de datos
        db.close();
        //Cerramos el cursor
        c.close();
        
        return lista_ofertas;		
	}

	@Override	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Este método se lanzará automáticamente cuando sea necesaria una actualización de la estructura de la base de datos o una conversión de los datos.
		//Un ejemplo puede ser añadir un nuevo campo en la tabla.		
		//Para la presente práctica tan solo nos limitamos a borrar la base de datos actual y crear de nuevo con la nueva estructura.
		db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BASEDATOS);
		onCreate(db);

	}
	/**
	 * Método para copiar la Base de datos de SQLITE a sdcard.
	 * @throws IOException
	 */
	public void copiarBD(Context ctx) throws IOException{
		
		InputStream myInput = new FileInputStream("/data/data/" + ctx.getPackageName() + "/databases/" + NOMBRE_BASEDATOS);

		File directory = new File(EXTERNAL_FILE_PATH  + Folder + NOMBRE_BASEDATOS);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		OutputStream myOutput = new FileOutputStream(directory.getPath() + "/" + NOMBRE_BASEDATOS);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
}
	


