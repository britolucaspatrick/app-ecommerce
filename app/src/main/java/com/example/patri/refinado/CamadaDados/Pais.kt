package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class Pais : SugarRecord {

    var prefixo: Int? = null
    var nome: String? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null

    constructor(){}

    constructor(prefixo: Int?, nome: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.prefixo = prefixo
        this.nome = nome
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }

}