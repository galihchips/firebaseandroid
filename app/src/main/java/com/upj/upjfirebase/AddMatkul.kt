package com.upj.upjfirebase

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class AddMatkul : AppCompatActivity() {
    private lateinit var vName : TextView
    private lateinit var txtMtk : EditText
    private lateinit var txtSks : EditText
    private lateinit var btnSave : Button
    private lateinit var mtkList : ListView
    private lateinit var matkulList : MutableList<MataKuliah>
    private lateinit var ref : DatabaseReference


    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_matakuliah)
        val id = intent.getStringExtra(EXTRA_ID)
        val name = intent.getStringExtra(EXTRA_NAME)

        matkulList = mutableListOf()

        ref = FirebaseDatabase.getInstance().getReference("tmtk").child(id!!)
        vName = findViewById(R.id.tvNameMhs)
        txtMtk = findViewById(R.id.etNameMtk)
        txtSks = findViewById(R.id.etSks)
        btnSave = findViewById(R.id.btnSaveMtk)
        mtkList = findViewById(R.id.lvMtk)
        vName.setText(name)
        btnSave.setOnClickListener{
            saveMatkul()
        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    matkulList.clear()
                    for (h : DataSnapshot in snapshot.children){
                        val matakuliah : MataKuliah? = h.getValue(MataKuliah::class.java)
                        if (matakuliah!== null) {
                            matkulList.add(matakuliah)
                        }
                    }
                    val adapter = MatakuliahAdapter(this@AddMatkul, R.layout.item_mtk, matkulList)
                    mtkList.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    fun saveMatkul(){
        val name = txtMtk.text.toString().trim()
        val sksText = txtSks.text.toString().trim()
        val sks = sksText.toInt()

        if (name.isEmpty()){
            txtMtk.error = "Matkul cant be empty"
        }
        if (sksText.isEmpty()){
            txtMtk.error = "Matkul cant be empty"
        }
        val matkulid = ref.push().key
        val mtkkul = MataKuliah(matkulid!!, name, sks)
        if (matkulid !== null) {
            ref.child(matkulid).setValue(mtkkul).addOnCompleteListener{
                Toast.makeText(applicationContext, "save data successful", Toast.LENGTH_SHORT).show()
            }
        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    matkulList.clear()
                    for (h : DataSnapshot in snapshot.children){
                        val matakuliah : MataKuliah? = h.getValue(MataKuliah::class.java)
                        if (matakuliah!== null) {
                            matkulList.add(matakuliah)
                        }
                    }
                    val adapter = MatakuliahAdapter(this@AddMatkul, R.layout.item_mtk, matkulList)
                    mtkList.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


}