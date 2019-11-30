package com.si.kategoriperpus;

import com.si.kategoriperpus.KoneksiActivity;

public class KategoriActivity extends KoneksiActivity {
// sourcecode untuk URL ->URL menggunakan IP address default eclipse
String URL = "http://192.168.8.101/kategori_perpus/server.php";
String url = "";
String response = "";

//menampilkan kategori dari database
public String tampilKategori() {
	try{
		url = URL + "?operasi_kategori=view";
		System.out.println("URL Tampil Kategori : " + url);
		response = call(url);
		}
	catch(Exception e) {
	}
	return response;
	}

//memasukan biodata baru ke dlama database
public String insertKategori(String nama_kategori) {
	try{
		url = URL + "?operasi_kategori=insert&nama_kategori=" + nama_kategori;
		System.out.println("URL Insert Kategori : " + url);
		response = call(url);
	}
	catch (Exception e){
	}
	return response;
	}
//melihat biodata berdasarkan ID
public String getKategoriById (int id_kategori) {
	try{
		url=URL + "?operasi_kategori=get_kategori_by_id&id_kategori=" + id_kategori;
		System.out.println("URL Insert Kategori : " + url);
		response = call(url);
	}
	catch(Exception e) {
	}
	return response;
	}
//mengubah isi biodata
public String updateKategori(String id_kategori, String nama_kategori) {
	try{
		url=URL + "?operasi_kategori=update&id_kategori=" + id_kategori + "&nama_kategori=" + nama_kategori;
		System.out.println("URL Update Kategori : " + url);
		response = call(url);
	}
	catch(Exception e){
	}
	return response;
	}
//coding hapus
public String deleteKategori (int id_kategori) {
	try{
		url = URL + "?operasi_kategori=delete&id_kategori=" + id_kategori;
		System.out.println("URL Hapus Kategori : " + url);
		response = call(url);
	}
	catch(Exception e){
	}
	return response;
	}
}