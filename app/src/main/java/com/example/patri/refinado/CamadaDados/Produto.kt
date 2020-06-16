package com.example.patri.refinado.CamadaDados

import android.support.annotation.FloatRange
import com.orm.SugarRecord
import com.orm.dsl.Ignore
import java.sql.Timestamp

class Produto : SugarRecord {

    var nome: String? = null
    var descricao: String? = null
    var ean: String? = null
    var referencia: String? = null
    var quantidade: Int? = null
    var preco: Float? = null
    var unidade: String? = null
    var largura: Float? = null
    var altura: Float? = null
    var profundidade: Float? = null
    var peso: Float? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null

    @Ignore
    var listProdutoImagem: ArrayList<ProdutoImagem>? = null
    @Ignore
    var listProdutoCor: ArrayList<ProdutoCor>? = null
    @Ignore
    var listProdutoCategoria: ArrayList<ProdutoCategoria>? = null

    constructor(){}

    constructor(nome: String?, descricao: String?, ean: String?, referencia: String?, quantidade: Int?, preco: Float?, largura: Float?, altura: Float?, profundidade: Float?, peso: Float?, unidade: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.nome = nome
        this.descricao = descricao
        this.ean = ean
        this.referencia = referencia
        this.quantidade = quantidade
        this.preco = preco
        this.largura = largura
        this.altura = altura
        this.profundidade = profundidade
        this.peso = peso
        this.unidade = unidade
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}