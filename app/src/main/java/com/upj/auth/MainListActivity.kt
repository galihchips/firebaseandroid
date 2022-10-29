package com.upj.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var txtTitleBook : EditText
    private lateinit var txtDescBook : EditText
    private lateinit var btnSave : Button
    private lateinit var btnLogout : Button
    private lateinit var listCatalog : ListView
    private  lateinit var auth : FirebaseAuth
    private lateinit var ref : DatabaseReference
    private lateinit var catalogList : MutableList<Catalog>
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        val userProfile: String = auth.currentUser?.email.toString()
        Log.d("valueeeee", userProfile)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ref = FirebaseDatabase.getInstance().getReference("dbcatalog")
        txtTitleBook = findViewById(R.id.bookTitle)
        txtDescBook = findViewById(R.id.descBook)
        btnSave = findViewById(R.id.btnSave)
        listCatalog = findViewById(R.id.lvCatalog)
        btnSave.setOnClickListener(this)
        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are you sure want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, which -> // Perform Your Task Here--When Yes Is Pressed.
                    dialog.cancel()
                    auth.signOut()
                    finish();
                    super.onBackPressed();
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which -> // Perform Your Task Here--When No is pressed
                    dialog.cancel()
                }.show()
        }
        catalogList = mutableListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    catalogList.clear()

                    for (h : DataSnapshot in snapshot.children){
                        val mahasiswa : Catalog? = h.getValue(Catalog::class.java)
                        if (mahasiswa !== null){
                            catalogList.add(mahasiswa)
                        }
                    }
                    val adapter = CatalogAdapter(this@MainListActivity, R.layout.item_data, catalogList)
                    listCatalog.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        listCatalog.setOnItemClickListener{ parent, view, position, id ->
            val catalog = catalogList.get(position)
            val intent = Intent(this@MainListActivity, AddCatalog::class.java)
            intent.putExtra(AddCatalog.EXTRA_ID, catalog.nim)
            intent.putExtra(AddCatalog.EXTRA_NAME, catalog.name)
            startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        saveData()
    }
    private fun saveData() {
        val nim : String = txtTitleBook.text.toString().trim()
        val name : String = txtDescBook.text.toString().trim()
        if (nim.isEmpty()) {
            txtTitleBook.error = "Title tidak boleh kosong"
        }
        if (name.isEmpty()) {
            txtDescBook.error = "Desc tidak boleh kosong"
        }
        val ref = FirebaseDatabase.getInstance().getReference("dbcatalog")
        val mhsId = ref.push().key
        val mhs = Catalog(mhsId!!,nim,name)
        if (mhsId !== null) {
            if (!nim.isEmpty() && !name.isEmpty()) {
                ref.child(mhsId).setValue(mhs).addOnCompleteListener{
                    txtTitleBook.setText("")
                    txtDescBook.setText("")
                    Toast.makeText(applicationContext, "Data Successfully Saved", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}

