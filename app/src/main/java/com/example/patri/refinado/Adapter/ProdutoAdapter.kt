package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.patri.refinado.CamadaDados.Carrinho
import com.example.patri.refinado.CamadaDados.Produto
import com.example.patri.refinado.CamadaDados.ProdutoImagem
import com.example.patri.refinado.CamadaNegocio.primeiroProdutoImagem
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.getBitmapImage
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.item_favorito.view.*

class ProdutoAdapter (private var activity: Activity, private var items: ArrayList<Produto>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var imagem: ImageView? = null
        var nome: TextView? = null
        var valor: TextView? = null

        init {
            this.imagem = row?.findViewById(R.id.imageProdutoImagem)
            this.nome = row?.findViewById(R.id.txt_nome_pro)
            this.valor = row?.findViewById(R.id.txt_valor_pro)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_produto, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var produto = items[position]

        val imagens = SugarRecord.findById(ProdutoImagem::class.java, produto.id)

        //viewHolder.imagem?.setImageBitmap(getBitmapImage(imagens.imagem!!))
        viewHolder.nome?.text = produto.nome
        viewHolder.valor?.text = produto.preco.toString()

        return view as View
    }

    override fun getItem(position: Int): Produto {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

}