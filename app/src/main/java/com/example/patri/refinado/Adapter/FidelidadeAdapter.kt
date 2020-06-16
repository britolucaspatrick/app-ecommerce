package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Fidelidade
import com.example.patri.refinado.R

class FidelidadeAdapter (private var activity: Activity, private var items: ArrayList<Fidelidade>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var pontos: TextView? = null
        var unidade: TextView? = null

        init {
            this.pontos = row?.findViewById<TextView>(R.id.txt_nome_item)
            this.unidade = row?.findViewById<TextView>(R.id.txt_prefixo_item)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.z_custom_list_pais, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var fidelidade = items[position]
        viewHolder.pontos?.text = fidelidade.pontos.toString()
        //viewHolder.unidade?.text = fidelidade.regraCobranca!!.unidade

        return view as View
    }

    override fun getItem(position: Int): Fidelidade {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}