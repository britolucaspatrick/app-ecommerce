package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord

class Cor : SugarRecord {
    var nome: String? = null
    var hexadecimal: String? = null

    constructor(){}

    constructor(nome: String?, hexadecimal: String?) {
        this.nome = nome
        this.hexadecimal = hexadecimal
    }
}