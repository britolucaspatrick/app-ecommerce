package com.example.patri.refinado.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.patri.refinado.CamadaDados.Carrinho
import com.example.patri.refinado.CamadaDados.Produto
import com.example.patri.refinado.CamadaNegocio.primeiroProdutoImagem
import com.example.patri.refinado.R
import com.example.patri.refinado.Util.getBitmapImage
import com.example.patri.refinado.Util.toastLong
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_produto_detalhado.*

class ProdutoDetalhadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_detalhado)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Produto Selecionado"

        val intent = intent
        val bundle = intent.extras
        val id = bundle.getLong("produtoselecionado")

        val produto = primeiroProdutoImagem(id)

//        imagemProdutoD.setImageBitmap(getBitmapImage(produto.listProdutoImagem!![0].imagem!!))
        txt_nome_pd.text = produto.nome
        txt_preco_pd.text = "R$ " + produto.preco.toString()
        txt_descricao_pd.text = "Descrição\n" + produto.descricao
        txt_ean_pd.text = produto.ean
        txt_referencia_pd.text = produto.referencia
        txt_quantidade_pd.text = "Quantidade disponível: " + produto.quantidade
        txt_largura_pd.text = "L " + produto.largura
        txt_altura_pd.text = "A " + produto.altura
        txt_profundidade_pd.text = "P " + produto.profundidade
        txt_peso_pd.text = "Peso " + produto.peso


        btn_carrinho_pd.setOnClickListener {

            val carrinho = Carrinho(produto.id, 1, produto.preco)
            carrinho.save()
            toastLong("Produto adicionado ao carrinho", this)
            finish()
        }

        btn_finalizar_pd.setOnClickListener {
            if (produto.quantidade!! > 0){
                val carrinho = Carrinho(produto.id, 1, produto.preco)
                carrinho.save()
                val intent = Intent(this, CarrinhoActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                toastLong("Produto sem estoque.",this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home
            -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else -> {
            }
        }
        return true
    }
}
