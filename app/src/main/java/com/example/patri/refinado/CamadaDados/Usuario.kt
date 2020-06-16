package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import com.orm.dsl.Ignore
import java.sql.Timestamp

class Usuario : SugarRecord {
    var usuario: String? = null
    var email: String? = null
    var senha: String? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var pessoa: Long? = null

    @Ignore
    var pessoaP: Pessoa? = null
    @Ignore
    var endereco: Endereco? = null
    @Ignore
    var estado: Estado? = null
    @Ignore
    var pais: Pais? = null


    constructor(){}

    constructor(usuario: String?, email: String?, senha: String?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?, pessoa: Long?) : super() {
        this.usuario = usuario
        this.email = email
        this.senha = senha
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
        this.pessoa = pessoa
    }
}