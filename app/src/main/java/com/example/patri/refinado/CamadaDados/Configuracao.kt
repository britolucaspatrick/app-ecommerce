package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord

class Configuracao : SugarRecord {

    var usuario: Long? = null
    var logarautomaticamente: String? = null

    constructor(){}

    constructor(usuario: Long?, st_logarautomaticamente: String?) : super() {
        this.logarautomaticamente = st_logarautomaticamente
    }
}