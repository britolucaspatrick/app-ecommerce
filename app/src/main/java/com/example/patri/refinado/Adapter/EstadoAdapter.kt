package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Estado
import com.example.patri.refinado.CamadaDados.Pais
import com.example.patri.refinado.R
import com.orm.SugarRecord

class EstadoAdapter (private var activity: Activity, private var items: ArrayList<Estado>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txt_estado_item_e: TextView? = null
        var txt_sigla_item_e: TextView? = null

        init {
            this.txt_estado_item_e = row?.findViewById(R.id.txt_estado_item_e)
            this.txt_sigla_item_e = row?.findViewById(R.id.txt_sigla_item_e)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.z_custom_list_estado, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var estado = items[position]
        viewHolder.txt_estado_item_e?.text = estado.nome
        viewHolder.txt_sigla_item_e?.text = estado.sigla

        return view as View
    }

    override fun getItem(position: Int): Estado {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}