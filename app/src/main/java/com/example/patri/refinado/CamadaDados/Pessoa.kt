package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Date
import java.sql.Timestamp

class Pessoa : SugarRecord {
    var nome: String? = null
    var sobrenome: String? = null
    var cpf: String? = null
    var aniversario: Date? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null

    constructor(){}

    constructor(nome: String?, sobrenome: String?, cpf: String?, aniverario: Date?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) : super() {
        this.nome = nome
        this.sobrenome = sobrenome
        this.cpf = cpf
        this.aniversario = aniverario
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }

    constructor(nome: String?, sobrenome: String?, cpf: String?, aniverario: Date?, alteracao: Timestamp?){

    }
}