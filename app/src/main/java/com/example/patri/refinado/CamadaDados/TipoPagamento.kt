package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord

class TipoPagamento : SugarRecord {

    var forma: String? = null

    constructor(){}

    constructor(forma: String?) {
        this.forma = forma
    }
}