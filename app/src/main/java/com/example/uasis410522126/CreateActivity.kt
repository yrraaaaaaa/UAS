package com.example.uasis410522126

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CreateActivity : AppCompatActivity() {

    private val api by lazy { ApiRetrofit().endpoint}
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etPosition: EditText
    private lateinit var etSkills: EditText
    private lateinit var etGender: EditText
    private lateinit var mbCreate: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v,
                                                                             insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,
                systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupListener()
    }
    private fun setupView() {
        etName = findViewById(R.id.EditTextName)
        etAge = findViewById(R.id.EditTextAge)
        etPosition = findViewById(R.id.EditTextPosition)
        etSkills = findViewById(R.id.EditTextSkills)
        etGender = findViewById(R.id.EditTextGender)
        mbCreate = findViewById(R.id.mbCreate)
    }
    private fun setupListener() {
        mbCreate.setOnClickListener {
            if (etName.text.toString().isNotEmpty() ||
                etAge.text.toString().isNotEmpty() ||
                etPosition.text.toString().isNotEmpty() ||
                etSkills.text.toString().isNotEmpty() ||
                etGender.text.toString().isNotEmpty()
            ) {
                Log.i("db_is4_10522126", "nama_lengkap: ${etName.text.toString()}")
                Log.i("db_is4_10522126", "usia: ${etAge.text.toString()}")
                Log.i("db_is4_10522126", "jabatan: ${etPosition.text.toString()}")
                Log.i("db_is4_10522126", "keahlian: ${etSkills.text.toString()}")
                Log.i("db_is4_10522126", "jenis_kelamin: ${etGender.text.toString()}")
                api.create(etName.text.toString(),
                    etAge.text.toString(),
                    etPosition.text.toString(),
                    etSkills.text.toString(),
                    etGender.text.toString()
                )
                    .enqueue(object : Callback<SubmitModel> {
                        override fun onResponse(p0: Call<SubmitModel>,
                                                p1: Response<SubmitModel>) {
                            val submit = p1.body()
                            Toast.makeText(
                                applicationContext,
                                submit!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                        override fun onFailure(p0: Call<SubmitModel>,
                                               p1: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name, Age, Position, Skills or Gender cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}