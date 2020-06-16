package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class Favorito : SugarRecord {

    var usuario : Long? = null
    var produto : Long? = null

    constructor(){}

    constructor(usuario: Long?, produto: Long?) : super() {
        this.usuario = usuario
        this.produto = produto
    }

}