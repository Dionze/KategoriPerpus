package com.si.kategoriperpus;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.*;
import android.graphics.*;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;

public class MainActivity extends Activity implements OnClickListener {
	
	KategoriActivity kategoriActivity = new KategoriActivity();
	TableLayout tableLayout;
	Button buttonTambahKategori;
	ArrayList<Button>buttonEdit 	= new ArrayList<Button>();
	ArrayList<Button>buttonDelete 	= new ArrayList<Button>();
	JSONArray arrayKategori;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	// Jika SDK Android diatas API Ver.9
		if (android.os.Build.VERSION.SDK_INT > 9) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	// Mendapatkan data widget dari XML Activity melalui ID
		tableLayout 			= (TableLayout) findViewById(R.id.tableKategori);
		buttonTambahKategori 	= (Button) findViewById(R.id.buttonTambahKategori);
		buttonTambahKategori.setOnClickListener(this);
		
	//Menambah baris untuk tabel
		TableRow barisTabel = new TableRow(this);
		barisTabel.setBackgroundColor(Color.CYAN);
		
	// Menambahkan tampilan teks untuk judul pada tabel
		TextView viewHeaderId_kategori 	 = new TextView(this);
		TextView viewHeaderNama_kategori = new TextView(this);
		
		viewHeaderId_kategori.setText	("Id_kategori");
		viewHeaderNama_kategori.setText ("Nama_kategori");
		
		viewHeaderId_kategori.setPadding	(5, 1, 5, 1);
		viewHeaderNama_kategori.setPadding	(5, 1, 5, 1);
		
	// Menampilkan tampilan TextView ke dalam tabel
		barisTabel.addView	(viewHeaderId_kategori);
		barisTabel.addView	(viewHeaderNama_kategori);
		
	// Menyusun ukuran dari tabel
		tableLayout.addView(barisTabel, new
		TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		try {
			
	// Mengubah data dari BiodataActivity yang berupa String menjadi array
		arrayKategori = new JSONArray(kategoriActivity.tampilKategori());
		for (int i = 0; i < arrayKategori.length(); i++) {
		JSONObject jsonChildNode = arrayKategori.getJSONObject(i);
		
		String nama_kategori = jsonChildNode.optString	("nama_kategori");
		String id_kategori 	 = jsonChildNode.optString	("id_kategori");
		
		System.out.println	("Nama_kategori : " + nama_kategori );
		System.out.println	("Id_kategori : " + id_kategori);
		barisTabel = new TableRow(this);
		
	// Memberi warna pada baris tabel
		if (i % 2 == 0) {
		barisTabel.setBackgroundColor(Color.LTGRAY);
		}
		
		TextView viewId_kategori = new TextView(this);
		viewId_kategori.setText(id_kategori);
		viewId_kategori.setPadding(5, 1, 5, 1);
		barisTabel.addView(viewId_kategori);

		TextView viewNama_kategori = new TextView(this);
		viewNama_kategori.setText(nama_kategori);
		viewNama_kategori.setPadding(5, 1, 5, 1);
		barisTabel.addView(viewNama_kategori);

	// Menambahkan button Edit
		buttonEdit.add(i, new Button(this));
		buttonEdit.get(i).setId(Integer.parseInt(id_kategori));
		buttonEdit.get(i).setTag("Edit");
		buttonEdit.get(i).setText("Edit");
		buttonEdit.get(i).setOnClickListener(this);
		barisTabel.addView(buttonEdit.get(i));
		
	// Menambahkan tombol Delete
		buttonDelete.add(i, new Button(this));
		buttonDelete.get(i).setId(Integer.parseInt(id_kategori));
		buttonDelete.get(i).setTag("Delete");
		buttonDelete.get(i).setText("Delete");
		buttonDelete.get(i).setOnClickListener(this);
		barisTabel.addView(buttonDelete.get(i));
		
		tableLayout.addView(barisTabel, new TableLayout.LayoutParams
				(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}
	catch (JSONException e) {
		e.printStackTrace();
	}
	}
public void onClick (View view) {
	if (view.getId() == R.id.buttonTambahKategori) {
		tambahKategori();
	}else{
	for (int i= 0; i < buttonEdit.size(); i++) {
	// Jika ingin mengedit data pada biodata
		if (view.getId() == buttonEdit.get(i).getId() &&
		view.getTag().toString().trim().equals("Edit")) {
		Toast.makeText(MainActivity.this, "Edit : " + buttonEdit.get(i).getId(),
		Toast.LENGTH_SHORT).show();
		int id_kategori = buttonEdit.get(i).getId();
		getKategoriByID(id_kategori);
		}
	// Menghapus data di Tabel
		else if (view.getId() == buttonDelete.get(i).getId() &&
		view.getTag().toString().trim().equals("Delete")){
		Toast.makeText(MainActivity.this, "Delete : " +
		buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
		int id_kategori = buttonDelete.get(i).getId();
		deleteKategori(id_kategori);
		}
		}
	}
}

public void deleteKategori (int id_kategori) {
	kategoriActivity.deleteKategori(id_kategori);
		finish();
		startActivity(getIntent());
	}
	// Mendapatkan Biodata melalui ID
public void getKategoriByID (int id_kategori) {
	String nama_kategoriEdit = null;
		JSONArray arrayPersonal;
		try {
			arrayPersonal = new JSONArray(kategoriActivity.getKategoriById(id_kategori));
			for (int i = 0; i < arrayPersonal.length(); i++) {
				JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
				nama_kategoriEdit = jsonChildNode.optString("nama_kategori");
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		LinearLayout layoutInput = new LinearLayout(this);
		layoutInput.setOrientation(LinearLayout.VERTICAL);
		
	// Membuat id tersembunyi pada AlertDialog
		final TextView viewId = new TextView(this);
		viewId.setText(String.valueOf(id_kategori));
		viewId.setTextColor(Color.TRANSPARENT);
		layoutInput.addView(viewId);
		final EditText editNama_kategori = new EditText(this);
		editNama_kategori.setText(nama_kategoriEdit);
		layoutInput.addView(editNama_kategori);
		
	// Membuat AlertDialog untuk mengubah data di Biodata
		AlertDialog.Builder builderEditKategori = new AlertDialog.Builder(this);
		
	//builderEditBiodata.setIcon(R.drawable.webse);
		builderEditKategori.setTitle("Update Kategori");
		builderEditKategori.setView(layoutInput);
		builderEditKategori.setPositiveButton("Update", new DialogInterface.OnClickListener()
		{
			
@Override
public void onClick(DialogInterface dialog, int which) {
	String nama_kategori = editNama_kategori.getText().toString();
	
	System.out.println("Nama_kategori : " + nama_kategori);
	
	String laporan = kategoriActivity.updateKategori(viewId.getText().toString(),
					editNama_kategori.getText().toString());
	Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
	finish();
	startActivity(getIntent());
	}
	});
		
	// Jika tidak ingin mengubah data pada Biodata
		builderEditKategori.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
		{
			
@Override
public void onClick(DialogInterface dialog, int which) {
	dialog.cancel();
	}
});
		builderEditKategori.show();
}
public void tambahKategori() {
	LinearLayout layoutInput = new LinearLayout(this);
	layoutInput.setOrientation(LinearLayout.VERTICAL);
	final EditText editNama_kategori = new EditText(this);
	editNama_kategori.setHint("Nama_kategori");
	layoutInput.addView(editNama_kategori);
	
// Membuat AlertDialog untuk menambahkan data pada Biodata
AlertDialog.Builder builderInsertKategori= new AlertDialog.Builder(this);

//builderInsertBiodata.setIcon(R.drawable.webse);
	builderInsertKategori.setTitle("Insert Kategori");
	builderInsertKategori.setView(layoutInput);
	builderInsertKategori.setPositiveButton("Insert", new
	DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
	String nama_kategori = editNama_kategori.getText().toString();
	System.out.println("Nama_kategori : " + nama_kategori);
	String laporan = kategoriActivity.insertKategori(nama_kategori);
	Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
	finish();
	startActivity(getIntent());
	}
});
	builderInsertKategori.setNegativeButton("Cancel", new
	DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
	dialog.cancel();
	}
});
	builderInsertKategori.show();
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
//Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
	}
}
