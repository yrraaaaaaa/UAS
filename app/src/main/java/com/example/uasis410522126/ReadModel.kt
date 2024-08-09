package com.example.uasis410522126

import java.io.Serializable

data class ReadModel (
    val result: List<Data>
) {
    data class Data (val id_pegawai: String?, val nama_lengkap: String?, val usia: String?, val jabatan: String?, val keahlian: String?, val jenis_kelamin: String?) :Serializable
}
