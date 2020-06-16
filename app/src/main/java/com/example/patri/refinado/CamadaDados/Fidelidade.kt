package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class Fidelidade : SugarRecord{
    var regraCobranca: Long? = null
    var pontos: Int? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null

    constructor(){}

    constructor(regraCobranca: Long?, pontos: Int?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.regraCobranca = regraCobranca
        this.pontos = pontos
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}