package com.upj.upjfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var txtNim : EditText
    private lateinit var txtName : EditText
    private lateinit var btnSave : Button
    private lateinit var listMhs : ListView
    private lateinit var ref : DatabaseReference
    private lateinit var mhsList : MutableList<Mahasiswa>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ref = FirebaseDatabase.getInstance().getReference("dbmhs")
        txtNim = findViewById(R.id.nim)
        txtName = findViewById(R.id.name)
        btnSave = findViewById(R.id.btnSave)
        listMhs = findViewById(R.id.lvMhs)
        btnSave.setOnClickListener(this)


        mhsList = mutableListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    mhsList.clear()
                    for (h : DataSnapshot in snapshot.children){
                        val mahasiswa : Mahasiswa? = h.getValue(Mahasiswa::class.java)
                        if (mahasiswa !== null){
                            mhsList.add(mahasiswa)
                        }
                    }
                    val adapter = MahasiswaAdapter(this@MainActivity, R.layout.item_data, mhsList)
                    listMhs.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        listMhs.setOnItemClickListener{ parent, view, position, id ->
            val mahasiswa = mhsList.get(position)
            val intent = Intent(this@MainActivity, AddMatkul::class.java)
            intent.putExtra(AddMatkul.EXTRA_ID, mahasiswa.id)
            intent.putExtra(AddMatkul.EXTRA_NAME, mahasiswa.name)
            startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        saveData()
    }
    private fun saveData() {
        val nim : String = txtNim.text.toString().trim()
        val name : String = txtName.text.toString().trim()
        if (nim.isEmpty()) {
            txtNim.error = "NIM tidak boleh kosong"
        }
        if (name.isEmpty()) {
            txtName.error = "Nama tidak boleh kosong"
        }
        val ref = FirebaseDatabase.getInstance().getReference("dbmhs")
        val mhsId = ref.push().key
        val mhs = Mahasiswa(mhsId!!,nim,name)
        if (mhsId !== null) {
            ref.child(mhsId).setValue(mhs).addOnCompleteListener{
                Toast.makeText(applicationContext, "Data Successfully Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

