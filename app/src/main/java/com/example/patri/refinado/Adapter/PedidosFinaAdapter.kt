package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.patri.refinado.Activity.ConfirmacaoDadosActivity
import com.example.patri.refinado.CamadaDados.Pedido
import com.example.patri.refinado.CamadaNegocio.cancelarPedido
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.Constantes.ID_PEDIDO
import com.example.patri.refinado.Util.toastLong

class PedidosFinaAdapter (private var activity: Activity, private var items: ArrayList<Pedido>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txt_numero_pf: TextView? = null
        var txt_totitens_pf: TextView? = null
        var txt_vlsub_pf: TextView? = null
        var txt_status_pf: TextView? = null
        var txt_data_pf: TextView? = null
        var position: Int? = null

        init {
            this.txt_numero_pf = row?.findViewById(R.id.txt_numero_pf)
            this.txt_totitens_pf = row?.findViewById(R.id.txt_totitens_pf)
            this.txt_vlsub_pf = row?.findViewById(R.id.txt_vlsub_pf)
            this.txt_status_pf = row?.findViewById(R.id.txt_status_pf)
            this.txt_data_pf = row?.findViewById(R.id.txt_data_pf)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_pedidofinalizado, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var pedido = items[position]
        viewHolder.position = position
        viewHolder.txt_numero_pf?.text = pedido.id.toString()
        viewHolder.txt_totitens_pf?.text = pedido.vltotalitens.toString()
        viewHolder.txt_vlsub_pf?.text = pedido.vlsubtotal.toString()
        viewHolder.txt_status_pf?.text = "Finalizado"
        viewHolder.txt_data_pf?.text = pedido.dtpedido.toString()

        return view as View
    }


    override fun getItem(position: Int): Pedido {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}