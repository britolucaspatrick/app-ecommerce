package com.example.patri.refinado.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.patri.refinado.CamadaDados.Carrinho
import com.example.patri.refinado.CamadaNegocio.*
import com.example.patri.refinado.R
import com.example.patri.refinado.R.id.lvCarrinho
import com.example.patri.refinado.R.id.txt_quantidade_carrinho
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.example.patri.refinado.Util.getBitmapImage
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.content_carrinho.*

class CarrinhoAdapter (private var activity: Activity, private var items: ArrayList<Carrinho>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var imagem: ImageView? = null
        var nome: TextView? = null
        var quantidade: TextView? = null
        var valor: TextView? = null
        var btn_favoritar_ca: Button? = null
        var btn_remover_ca: Button? = null
        var idproduto: Long? = null
        var idcarrinho: Long? =  null
        var position: Int? = null
        var btn_confirmacao_quantidade: ImageButton? = null
        var carrinho: Carrinho? = null


        init {
            this.imagem = row?.findViewById(R.id.imagemItemCarrinho)
            this.nome = row?.findViewById(R.id.txt_produto_carrinho)
            this.quantidade = row?.findViewById(R.id.txt_quantidade_carrinho)
            this.valor = row?.findViewById(R.id.txt_valor_carrinho)
            this.btn_favoritar_ca = row?.findViewById(R.id.btn_favoritar_ca)
            this.btn_remover_ca = row?.findViewById(R.id.btn_remover_ca)
            this.btn_confirmacao_quantidade = row?.findViewById(R.id.btn_confirmacao_quantidade)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_carrinho, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var carrinho = items[position]
        val produto = primeiroProdutoImagem(carrinho.produto)

        viewHolder.carrinho = carrinho
        viewHolder.idcarrinho = carrinho.id
        viewHolder.idproduto = carrinho.produto
        viewHolder.position = position
        viewHolder.imagem?.setImageBitmap(getBitmapImage(produto.listProdutoImagem!![0].imagem!!))
        viewHolder.nome?.text = produto.nome
        viewHolder.quantidade?.text = carrinho.quantidade.toString()
        viewHolder.valor?.text = carrinho.preco.toString()

        viewHolder.btn_favoritar_ca?.setOnClickListener {
            toastLong(salvarFavorito(ID_USUARIO, viewHolder.idproduto!!), activity)
        }

        viewHolder.btn_remover_ca?.setOnClickListener {
            val a = SugarRecord.findById(Carrinho::class.java, viewHolder.idcarrinho)
            SugarRecord.delete(a)
            items.remove(a)
            notifyDataSetChanged()
        }

        viewHolder.btn_confirmacao_quantidade?.setOnClickListener {
            if (viewHolder.quantidade!!.text.toString().isNullOrEmpty())
                toastLong("Obrigatório informar quantidade", activity)
            else if (!intervaloQuantidadeDisponivelProd(viewHolder.idproduto, viewHolder.quantidade!!.text.toString().toInt()))
                toastLong("A quantidade deve estar no intervalo mínimo e máximo estabelecidos.", activity)
            else{
                toastLong("Quantidade disponível.", activity)
                items[viewHolder.position!!].quantidade = viewHolder.quantidade!!.text.toString().toInt()
            }
        }

        return view as View
    }

    override fun notifyDataSetChanged() {
        items = SugarRecord.listAll(Carrinho::class.java) as ArrayList<Carrinho>

        super.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Carrinho {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun getCount(): Int {
        return items.size
    }

    fun getCarrinho(): ArrayList<Carrinho>{
        return items
    }
}