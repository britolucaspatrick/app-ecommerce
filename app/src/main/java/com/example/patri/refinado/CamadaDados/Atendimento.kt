package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class Atendimento : SugarRecord {

    var observacao: String? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var usuario: Long? = null

    constructor(){}

    constructor(usuario: Long?, observacao: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.usuario = usuario
        this.observacao = observacao
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}