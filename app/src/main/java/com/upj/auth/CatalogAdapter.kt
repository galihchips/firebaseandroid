package com.upj.auth

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class CatalogAdapter (val mContext : Context, val layoutResId : Int, val catalogList :
    List<Catalog>) : ArrayAdapter<Catalog>(mContext, layoutResId, catalogList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view  : View = layoutInflater.inflate(layoutResId, null)
        val tvTitleBook : TextView = view.findViewById(R.id.tvTitleBook)
        val tvDescBook : TextView = view.findViewById(R.id.tvDescBook)
        val tvEdit : TextView = view.findViewById(R.id.tvEdit)
        val catalog : Catalog = catalogList[position]
        tvEdit.setOnClickListener{
            showUpdateDialog(catalog)
        }
        tvTitleBook.text = catalog.title
        tvDescBook.text = catalog.desc
        return view
    }
    private fun showUpdateDialog(catalog: Catalog){
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Edit Data")
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.update_dialog, null)
        val txtNim : TextView = view.findViewById(R.id.etTitle)
        val txtName : TextView = view.findViewById(R.id.etDesc)
        txtNim.setText(catalog.title)
        txtName.setText(catalog.desc)

        builder.setView(view)
        builder.setPositiveButton("Update") {
            p0,p1 ->
            val dbcatalog = FirebaseDatabase.getInstance().getReference("dbcatalog")
            val nim = txtNim.text.toString().trim()
            val name = txtName.text.toString().trim()

            if (nim.isEmpty()) {
                txtNim.error = "title cant be empty"
                txtNim.requestFocus()
                return@setPositiveButton
            }
            if (name.isEmpty()) {
                txtName.error = "desc cant be empty"
                txtName.requestFocus()
                return@setPositiveButton
            }
            val catalog = Catalog(catalog.id, nim, name)
            dbcatalog.child(catalog.id!!).setValue(catalog)
            Toast.makeText(mContext, "Update data success", Toast.LENGTH_SHORT).show()


        }
        builder.setNegativeButton("Delete") { p0,p1 ->
            val delMhs = FirebaseDatabase.getInstance().getReference("dbmhs").child(catalog.id.toString())
            delMhs.removeValue()
            Toast.makeText(mContext, "Data removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Cancel") {p0, p1 ->}
        val alert = builder.create()
        alert.show()

    }
}