package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord

class ProdutoCor : SugarRecord{
    var cor: Long? = null
    var produto: Long? = null
    var posicao: Int? = null

    constructor(){}

    constructor(cor: Long?, produto: Long?, posicao: Int?) {
        this.cor = cor
        this.produto = produto
        this.posicao = posicao
    }
}