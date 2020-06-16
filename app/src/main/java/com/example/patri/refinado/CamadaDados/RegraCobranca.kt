package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class RegraCobranca : SugarRecord{

    var unidade: String? = null
    var valor: Float? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null

    constructor(){}

    constructor(unidade: String?, valor: Float?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.unidade = unidade
        this.valor = valor
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}