package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.TipoPagamento
import com.example.patri.refinado.R

class TpPagamentoAdapter (private var activity: Activity, private var items: ArrayList<TipoPagamento>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var nome: TextView? = null

        init {
            this.nome = row?.findViewById(R.id.txt_nome_item_tp)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.z_custom_list_tppagamento, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var tipoPagamento = items[position]
        viewHolder.nome?.text = tipoPagamento.forma

        return view as View
    }

    override fun getItem(position: Int): TipoPagamento {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}