package com.upj.upjfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MahasiswaAdapter (val mContext : Context, val layoutResId : Int, val mhsList :
    List<Mahasiswa>) : ArrayAdapter<Mahasiswa>(mContext, layoutResId, mhsList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view  : View = layoutInflater.inflate(layoutResId, null)
        val tvNim : TextView = view.findViewById(R.id.tvNim)
        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvEdit : TextView = view.findViewById(R.id.tvEdit)
        val mahasiswa : Mahasiswa = mhsList[position]
        tvEdit.setOnClickListener{
            showUpdateDialog(mahasiswa)
        }
        tvNim.text = mahasiswa.nim
        tvName.text = mahasiswa.name
        return view
    }
    private fun showUpdateDialog(mahasiswa: Mahasiswa){
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Edit Data")
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.update_dialog, null)
        val txtNim : TextView = view.findViewById(R.id.etNim)
        val txtName : TextView = view.findViewById(R.id.etName)
        txtNim.setText(mahasiswa.nim)
        txtName.setText(mahasiswa.name)

        builder.setView(view)
        builder.setPositiveButton("Update") {
            p0,p1 ->
            val dbMhs = FirebaseDatabase.getInstance().getReference("dbmhs")
            val nim = txtNim.text.toString().trim()
            val name = txtName.text.toString().trim()

            if (nim.isEmpty()) {
                txtNim.error = "NIM cant be empty"
                txtNim.requestFocus()
                return@setPositiveButton
            }
            if (name.isEmpty()) {
                txtName.error = "Name cant be empty"
                txtName.requestFocus()
                return@setPositiveButton
            }
            val mahasiswa = Mahasiswa(mahasiswa.id, nim, name)
            dbMhs.child(mahasiswa.id!!).setValue(mahasiswa)
            Toast.makeText(mContext, "Update data success", Toast.LENGTH_SHORT).show()


        }
        builder.setNegativeButton("Delete") { p0,p1 ->
            val delMhs = FirebaseDatabase.getInstance().getReference("dbmhs").child(mahasiswa.id.toString())
            delMhs.removeValue()
            Toast.makeText(mContext, "Data removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Cancel") {p0, p1 ->}
        val alert = builder.create()
        alert.show()

    }
}