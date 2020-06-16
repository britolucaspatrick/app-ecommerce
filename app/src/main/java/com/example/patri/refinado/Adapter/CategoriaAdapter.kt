package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Categoria
import com.example.patri.refinado.R

class CategoriaAdapter (private var activity: Activity, private var items: ArrayList<Categoria>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var item_nome_cat: TextView? = null
        var item_root_cat: TextView? = null
        var item_posicao_cat: TextView? = null
        var item_parente_cat: TextView? = null

        init {
            this.item_nome_cat = row?.findViewById(R.id.item_nome_cat)
            this.item_root_cat = row?.findViewById(R.id.item_root_cat)
            this.item_posicao_cat = row?.findViewById(R.id.item_posicao_cat)
            this.item_parente_cat = row?.findViewById(R.id.item_parente_cat)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.z_custom_list_categoria, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var categoria = items[position]
        viewHolder.item_nome_cat?.text = categoria.nome
        viewHolder.item_root_cat?.text = categoria.root.toString()
        viewHolder.item_posicao_cat?.text = categoria.posicao.toString()
        viewHolder.item_parente_cat?.text = categoria.categoria?.toString()

        return view as View
    }

    override fun getItem(position: Int): Categoria {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}