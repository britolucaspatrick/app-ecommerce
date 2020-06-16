package com.example.patri.refinado.CamadaDados

import com.orm.SugarRecord
import com.orm.dsl.Ignore
import java.sql.Timestamp

class Categoria : SugarRecord {

    var nome: String? = null
    var root: Boolean? = null
    var posicao: Int? = null
    var registro: String? = null
    var criacao: Timestamp? = null
    var alteracao: Timestamp? = null
    var categoria: Long? = null

    //utilizado para guardar em memoria a ordem de seleção (Cadastro de produto)
    @Ignore
    var index:Int? = null

    constructor(){}

    constructor(categoria: Long?, root: Boolean?, nome: String?, posicao: Int?, registro: String?, criacao: Timestamp?, alteracao: Timestamp?) {
        this.categoria = categoria
        this.root = root
        this.nome = nome
        this.posicao = posicao
        this.registro = registro
        this.criacao = criacao
        this.alteracao = alteracao
    }
}