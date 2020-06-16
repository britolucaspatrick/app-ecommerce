package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import com.orm.dsl.Ignore

class Carrinho : SugarRecord {
    var produto: Long? = null
    var quantidade: Int? = null
    var preco: Float? = null

    constructor(){}

    constructor(produto: Long?, quantidade: Int?, preco: Float?) {
        this.produto = produto
        this.quantidade = quantidade
        this.preco = preco
    }

}