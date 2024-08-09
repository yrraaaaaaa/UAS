package com.example.uasis410522126


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var pegawaiAdapter: PegawaiAdapter
    private lateinit var listPegawai: RecyclerView
    private lateinit var fabCreate: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        setupList()
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        getPegawai()  // Panggil getPegawai saat aktivitas dimulai
    }

    private fun setupView() {
        listPegawai = findViewById(R.id.RecyclerViewPegawai)
        fabCreate = findViewById(R.id.fabCreate)
    }

    private fun setupList() {
        pegawaiAdapter = PegawaiAdapter(arrayListOf(), object : PegawaiAdapter.OnAdapterListener {
            override fun onUpdate(pegawai: ReadModel.Data) {
                startActivity(
                    Intent(this@MainActivity, UpdateActivity::class.java)
                        .putExtra("pegawai", pegawai)
                )
            }

            override fun onDelete(pegawai: ReadModel.Data) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                builder
                    .setMessage("Yakin akan menghapus data ${pegawai.nama_lengkap} ?")
                    .setTitle("Hapus Data")
                    .setPositiveButton("Hapus") { dialog, which ->
                        pegawai.id_pegawai?.let { id_pegawai ->
                            api.delete(id_pegawai)
                                .enqueue(object : Callback<SubmitModel> {
                                    override fun onResponse(
                                        call: Call<SubmitModel>,
                                        response: Response<SubmitModel>
                                    ) {
                                        if (response.isSuccessful) {
                                            val submit = response.body()
                                            Toast.makeText(
                                                applicationContext,
                                                submit?.message ?: "Berhasil Menghapus Pegawai",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            getPegawai()  // Panggil getPegawai untuk memperbarui daftar setelah penghapusan
                                        }
                                    }

                                    override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                                        Log.e(
                                            "MainActivity",
                                            "Failed to delete pegawai: ${t.message}"
                                        )
                                    }
                                })
                        } ?: run {
                            Log.e("MainActivity", "Pegawai ID is null, cannot delete")
                        }
                    }
                    .setNegativeButton("Batal") { dialog, which ->
                        // Do something else.
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        })
        listPegawai.adapter = pegawaiAdapter
    }

    private fun setupListener() {
        fabCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

    private fun getPegawai() {
        api.data().enqueue(object : Callback<ReadModel> {
            override fun onResponse(call: Call<ReadModel>, response: Response<ReadModel>) {
                if (response.isSuccessful) {
                    val listData = response.body()?.result ?: emptyList()

                    // Logging untuk memastikan data yang diterima
                    listData.forEach { data ->
                        Log.d("API Response", "ID: ${data.id_pegawai}, Nama: ${data.nama_lengkap}")
                    }

                    pegawaiAdapter.setData(listData)
                } else {
                    Log.e("API Response", "Response unsuccessful")
                }
            }

            override fun onFailure(call: Call<ReadModel>, t: Throwable) {
                Log.e("API Response", t.toString())
            }
        })
    }
}

