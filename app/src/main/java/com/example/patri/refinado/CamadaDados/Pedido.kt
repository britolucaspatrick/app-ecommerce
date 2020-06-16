package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Date
import java.sql.Timestamp

class Pedido : SugarRecord {

    var status: String? = null
    var vlfrete: Float? = null
    var vldesconto: Float? = null
    var vltotalitens: Float? = null
    var vlsubtotal: Float? = null
    var dtpedido: Timestamp? = null
    var dtentrega: Timestamp? = null
    //P - Pendente F - Finalizado
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null

    var usuario: Long? = null
    var tppagamento: Long? = null
    var endereco: Long? = null
    var fidelidade: Long? = null

    constructor(){}

    constructor(id_usuario: Long?, id_tp_pagamento: Long?, id_endereco: Long?, id_fidelidade: Long?, status: String?, vl_frete: Float?, vl_desconto: Float?, vl_totalitens: Float?, vl_subtotal: Float?, dt_pedido: Timestamp?, dt_entrega: Timestamp?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.usuario = id_usuario
        this.tppagamento = id_tp_pagamento
        this.endereco = id_endereco
        this.fidelidade = id_fidelidade
        this.status = status
        this.vlfrete = vl_frete
        this.vldesconto = vl_desconto
        this.vltotalitens = vl_totalitens
        this.vlsubtotal = vl_subtotal
        this.dtpedido = dt_pedido
        this.dtentrega = dt_entrega
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }

    constructor(id_usuario: Long?, registro: String?, dtpedido: Timestamp?, criacao: Timestamp?, alteracao: Timestamp?){
        this.usuario = id_usuario
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
        this.dtpedido = dtpedido
    }
}