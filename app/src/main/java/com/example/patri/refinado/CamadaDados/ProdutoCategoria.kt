package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord

class ProdutoCategoria : SugarRecord {
    var categoria: Long? = null
    var produto: Long? = null
    var posicao: Int? = null

    constructor(){}

    constructor(categoria: Long?, produto: Long?, posicao: Int?) {
        this.categoria = categoria
        this.produto = produto
        this.posicao = posicao
    }
}