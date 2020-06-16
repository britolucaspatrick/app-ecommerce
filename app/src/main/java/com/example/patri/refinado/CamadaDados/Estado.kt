package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class Estado : SugarRecord {

    var nome: String? = null
    var sigla: String? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var pais: Long? = null

    constructor(){}

    constructor(nome: String?, sigla: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?, pais: Long?) : super() {
        this.nome = nome
        this.sigla = sigla
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
        this.pais = pais
    }
}