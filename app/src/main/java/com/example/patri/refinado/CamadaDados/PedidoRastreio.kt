package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class PedidoRastreio : SugarRecord {

    var codigo: String? = null
    var valor: Float? = null
    var status: String? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var transportadora: Long? = null

    constructor(){}

    constructor(transportadora: Long?, codigo: String?, valor: Float?, status: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.transportadora = transportadora
        this.codigo = codigo
        this.valor = valor
        this.status = status
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}