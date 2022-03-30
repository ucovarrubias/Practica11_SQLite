package utiak.covarrubias.tarjetasd

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import utiak.covarrubias.tarjetasd.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import android.database.sqlite.SQLiteDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var amigosDBHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        amigosDBHelper = miSQLiteHelper(this)
        binding.btnGuardar.setOnClickListener {
            if (binding.etNombre.text.isNotBlank() && binding.etEmail.text.isNotBlank()) {
                   amigosDBHelper.anadirDato(binding.etNombre.text.toString(), binding.etEmail.text.toString())
                binding.etNombre.text.clear()
                binding.etEmail.text.clear()
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se ha podido guardar", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnConsultar.setOnClickListener {
            binding.tvConsulta.text = ""
            val db: SQLiteDatabase = amigosDBHelper.readableDatabase
            val cursor= db.rawQuery(
                "SELECT * FROM amigos",
                null
            )
            if (cursor.moveToFirst()){
                do {
                    binding.tvConsulta.append(cursor.getInt(0).toString() + ": ")
                    binding.tvConsulta.append(cursor.getString(1).toString() + ", ")
                    binding.tvConsulta.append(cursor.getString(2).toString() + "\n")
                } while (cursor.moveToNext())
            }
        }
    }
}