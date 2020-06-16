package com.example.patri.refinado.CamadaNegocio

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.patri.refinado.Activity.SplashActivity
import com.example.patri.refinado.CamadaDados.*
import com.example.patri.refinado.Util.Constantes.ID_PRODUTO
import com.example.patri.refinado.Util.getByteImage
import com.orm.*
import com.orm.util.QueryBuilder
import java.sql.Timestamp

fun salvarProduto(id_produto: Long?,
                  nome:String,
                  desc:String,
                  ean:String,
                  referencia:String,
                  quantidade:String,
                  preco:String,
                  unidade:String,
                  largura:String,
                  altura:String,
                  profundidade:String,
                  peso: String,
                  listCategorias: List<Categoria>?,
                  listCores: List<Cor>?,
                  listImagens: List<Bitmap>?): String
{
    try {
        val validacao = validarProduto(nome, desc, ean, referencia, quantidade, preco, unidade, largura, altura, profundidade, peso, listCategorias, listCores, listImagens)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val produto = Produto(nome, desc, ean, referencia, quantidade.toInt(), preco.toFloat(), largura.toFloat(), altura.toFloat(), profundidade.toFloat(), peso.toFloat(), unidade, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
        if (id_produto != null)
            produto.id = id_produto

        val id_produto = produto.save()
        ID_PRODUTO = null

        //CATEGORIA
        var i = 0
        listCategorias!!.forEach {
            var produtoCategoria = ProdutoCategoria(it.id, id_produto, i)
            produtoCategoria.save()
            i++
        }

        //CORES
        i = 0
        listCores!!.forEach {
            var produtoCor = ProdutoCor(it.id, id_produto, i)
            produtoCor.save()
            i++
        }

        //IMAGENS
        i = 0
        listImagens!!.forEach {
            var produtoImagem = ProdutoImagem(id_produto, getByteImage(it), i)
            produtoImagem.save()
            i++
        }

        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
    return ""
}

fun validarProduto(nome:String,
                   desc:String,
                   ean:String,
                   referencia:String,
                   quantidade:String,
                   preco:String,
                   unidade:String,
                   largura:String,
                   altura:String,
                   profundidade:String,
                   peso: String,
                   listCategorias: List<Categoria>?,
                   listCores: List<Cor>?,
                   listImagens: List<Bitmap>?):String
{


    return ""
}

fun primeiroProdutoImagem(x: Long? = null, topImagem: Int? = null): Produto {
    var produto = SugarRecord.findById(Produto::class.java, x)
    val id = x.toString()
    if (topImagem == null){
        produto.listProdutoImagem = SugarRecord.find(ProdutoImagem::class.java, "id = ?", id) as ArrayList<ProdutoImagem>
        return produto
    }else{
        val query = "select top $topImagem from produtoimagem where id = ?"
        produto.listProdutoImagem = SugarRecord.findWithQuery(ProdutoImagem::class.java, query, id) as ArrayList<ProdutoImagem>
        return  produto
    }
}

fun listarTodosProdutos(topImagem: Int? = null, registro: String? = null): ArrayList<Produto> {
    var produto: ArrayList<Produto>
    if (registro == null){
        produto = SugarRecord.listAll(Produto::class.java) as ArrayList<Produto>
    }
    else{
        produto = SugarRecord.find(Produto::class.java, "registro = ?", registro) as ArrayList<Produto>
    }

    produto.forEach {
        //IMAGENS
        val id = it.id.toString()
        if (topImagem == null){
            it.listProdutoImagem = SugarRecord.find(ProdutoImagem::class.java, "produto = ?", id) as ArrayList<ProdutoImagem>
        }else{
            it.listProdutoImagem = SugarRecord.find(ProdutoImagem::class.java, "produto = ?", id) as ArrayList<ProdutoImagem>
            //SugarRecord.find(ProdutoImagem::class.java,
            //"produto = ?", id, "", "", topImagem.toString()) as ArrayList<ProdutoImagem>
        }

        //CORES
        it.listProdutoCor = SugarRecord.find(ProdutoCor::class.java, "produto = ?",id) as ArrayList<ProdutoCor>

        //CATEGORIAS
        it.listProdutoCategoria = SugarRecord.find(ProdutoCategoria::class.java, "produto = ?", id) as ArrayList<ProdutoCategoria>

    }

    return produto
}

fun listarProdutosPorCategoria(id_categoria: Long): ArrayList<Produto>{
    val listCategorias = SugarRecord.find(ProdutoCategoria::class.java, id_categoria!!.toString())
    val listProduto = ArrayList<Produto>()
    listCategorias.forEach {
        val p = primeiroProdutoImagem(it.produto)
        p.listProdutoImagem = SugarRecord.find(ProdutoImagem::class.java, it.produto!!.toString()) as ArrayList<ProdutoImagem>
        listProduto.add(p)
    }
    return listProduto
}

fun listarProdUnicoCompleto(id_produto: Long): Produto? {
    var produto = SugarRecord.findById(Produto::class.java, id_produto)
    val id = id_produto.toString()
    produto.listProdutoImagem = SugarRecord.find(ProdutoImagem::class.java, "produto = ?", id) as ArrayList<ProdutoImagem>
    produto.listProdutoCor = SugarRecord.find(ProdutoCor::class.java, "produto = ?",id) as ArrayList<ProdutoCor>
    produto.listProdutoCategoria = SugarRecord.find(ProdutoCategoria::class.java, "produto = ?", id) as ArrayList<ProdutoCategoria>

    return produto
}

fun deletarProduto(id_produto: Long?): String {
    try{
        var p = SugarRecord.findById(Produto::class.java, id_produto)
        p.registro = "C"
        p.alteracao = Timestamp(System.currentTimeMillis())
        p.save()

        return "Excluído com sucesso."
    }catch (ex: Exception){
        return "Erro ao excluir o produto."
    }
}

fun saldoEstoqueProduto(id_produto: Long): Boolean{
    val p = SugarRecord.findById(Produto::class.java, id_produto!!)
    if (p == null)
        return false
    else if (p.quantidade!! > 0)
        return true
    else
        return false
}



