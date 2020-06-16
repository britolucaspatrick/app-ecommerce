package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class FidelidadeCobranca : SugarRecord {
    var RegraCobranca: Long? = null
    var Fidelidade: Long? = null
    var unidade: String? = null
    var valor: Float? = null
    var criacao: Timestamp? = null

    constructor(){}

    constructor(RegraCobranca: Long?, Fidelidade: Long?, unidade: String?, valor: Float?, criacao: Timestamp?) {
        this.RegraCobranca = RegraCobranca
        this.Fidelidade = Fidelidade
        this.unidade = unidade
        this.valor = valor
        this.criacao = criacao
    }
}