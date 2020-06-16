package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class PedidoProdutos : SugarRecord {

    var vlunitario: Float? = null
    var preco: Float? = null
    var quantidade: Int? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var pedido: Long? = null
    var produto: Long? = null
    var rastreio: Long? = null

    constructor(){}

    constructor(pedido: Long?, produto: Long?, rastreio: Long?, vl_unitario: Float?, preco: Float?, quantidade: Int?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.pedido = pedido
        this.produto = produto
        this.rastreio = rastreio
        this.vlunitario = vl_unitario
        this.preco = preco
        this.quantidade = quantidade
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}