package com.upj.auth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MatakuliahAdapter(val mContext : Context, val layoutResId : Int, val mtkList :
    List<MataKuliah>) : ArrayAdapter<MataKuliah>(mContext, layoutResId, mtkList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view  : View = layoutInflater.inflate(layoutResId, null)
        val vMtk : TextView = view.findViewById(R.id.tvMtk)
        val vSks : TextView = view.findViewById(R.id.tvSks)
        val matakuliah : MataKuliah = mtkList[position]

        vMtk.text = matakuliah.mtk
        vSks.text = matakuliah.sks.toString()
        return view
    }
}