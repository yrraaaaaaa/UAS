package com.example.uasis410522126

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiEndpoint {
    @GET("tampilSemua.php")
    fun data() : Call<ReadModel>
    @FormUrlEncoded
    @POST("tambah.php")
    fun create(
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("usia") usia: String,
        @Field("jabatan") jabatan: String,
        @Field("keahlian") keahlian: String,
        @Field("jenis_kelamin") jenis_kelamin: String
    ) : Call<SubmitModel>
    @FormUrlEncoded
    @POST("ubah.php")
    fun update(
        @Field("id_pegawai") id_pegawai: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("usia") usia: String,
        @Field("jabatan") jabatan: String,
        @Field("keahlian") keahlian: String,
        @Field("jenis_kelamin") jenis_kelamin: String
    ) : Call<SubmitModel>
    @FormUrlEncoded
    @POST("hapus.php")
    fun delete(
        @Field("id_pegawai") id_pegawai: String
    ) : Call<SubmitModel>
}