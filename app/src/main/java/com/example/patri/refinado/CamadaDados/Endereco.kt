package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import java.sql.Timestamp

class Endereco : SugarRecord {

    var cidade: String? = null
    var telefone: String? = null
    var bairro: String? = null
    var numero: String? = null
    var complemento: String? = null
    var enderedo: String? = null
    var cep: String? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var pessoa: Long? = null
    var estado: Long? = null

    constructor(){}

    constructor(pessoa: Long?, estado: Long?, cidade: String?, telefone: String?, bairro: String?, numero: String?, complemento: String?, enderedo: String?, cep: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.pessoa = pessoa
        this.cidade = cidade
        this.telefone = telefone
        this.bairro = bairro
        this.numero = numero
        this.complemento = complemento
        this.enderedo = enderedo
        this.cep = cep
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }

    constructor(estado: Long?, cidade: String?, bairro: String?, numero: String?, complemento: String?, enderedo: String?, cep: String?, criacao: Timestamp?){
        this.estado = estado
        this.cidade = cidade
        this.bairro = bairro
        this.numero = numero
        this.complemento = complemento
        this.enderedo = enderedo
        this.cep = cep
        this.criacao = criacao
    }
}