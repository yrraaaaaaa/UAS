package com.example.uasis410522126

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PegawaiAdapter(
    val pegawai: ArrayList<ReadModel.Data>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<PegawaiAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder (
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_pegawai, parent, false)
        )

    override fun onBindViewHolder(holder: PegawaiAdapter.ViewHolder,
                                  position: Int) {
        val data = pegawai[position]
        holder.tvName.text = data.nama_lengkap
        holder.tvAge.text = data.usia
        holder.tvPosition.text = data.jabatan
        holder.tvSkills.text = data.keahlian
        holder.tvGender.text = data.jenis_kelamin
        holder.itemView.setOnClickListener {
            listener.onUpdate(data)
        }
        holder.imgDelete.setOnClickListener {
            listener.onDelete(data)
        }
    }
    override fun getItemCount() = pegawai.size
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.TextViewName)
        val tvAge = view.findViewById<TextView>(R.id.TextViewAge)
        val tvPosition = view.findViewById<TextView>(R.id.TextViewPosition)
        val tvSkills = view.findViewById<TextView>(R.id.TextViewSkills)
        val tvGender = view.findViewById<TextView>(R.id.TextViewGender)
        val imgDelete = view.findViewById<ImageView>(R.id.ImageViewDelete)
    }
    public fun setData(data: List<ReadModel.Data>) {
        pegawai.clear()
        pegawai.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onUpdate(pegawai: ReadModel.Data)
        fun onDelete(pegawai: ReadModel.Data)
        }
}