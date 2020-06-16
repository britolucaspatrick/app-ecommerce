package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.RegraCobranca
import com.example.patri.refinado.R

class RegrasCobrancaAdapter  (private var activity: Activity, private var items: ArrayList<RegraCobranca>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txt_nome_item: TextView? = null
        var txt_prefixo_item: TextView? = null

        init {
            this.txt_nome_item = row?.findViewById(R.id.txt_nome_item)
            this.txt_prefixo_item = row?.findViewById(R.id.txt_prefixo_item)
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

        var regraCobranca = items[position]
        viewHolder.txt_nome_item?.text = regraCobranca.unidade
        viewHolder.txt_prefixo_item?.text = regraCobranca.valor.toString()

        return view as View
    }

    override fun getItem(position: Int): RegraCobranca {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}