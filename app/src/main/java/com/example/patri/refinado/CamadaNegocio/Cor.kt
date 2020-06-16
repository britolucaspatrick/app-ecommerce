package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Cor
import com.example.patri.refinado.CamadaDados.ProdutoCor
import com.orm.SugarRecord

fun listarCor(registro: String): ArrayList<Cor> {
    return SugarRecord.listAll(Cor::class.java) as ArrayList<Cor>
    //return if (registro.isEmpty()) SugarRecord.findAll(Cor::class.java) as ArrayList<Cor>
    //else SugarRecord.find(Cor::class.java, "registro = ?", registro) as ArrayList<Cor>
}

fun salvarCor(nome: String, hexadecimal: String) : String{
    try {
        val validacao = validarCor(nome, hexadecimal)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val cor = Cor(nome, hexadecimal)
        cor.save()

        return "Salvo com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun deleteCor(id: Long?): String{
    try {

        //validar utilização da cor pela produto_cor
        val produtoCor = SugarRecord.findById(ProdutoCor::class.java, id)
        if (produtoCor != null)
            return "Não será possível excluir está cor pois está sendo utilizada produto ${produtoCor.produto}"


        val cor = SugarRecord.findById(Cor::class.java, id)
        SugarRecord.delete(cor)

        return "Deletado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun atualizarCor(id: Long?, nome: String, hexadecimal: String): String {
    try {
        val validacao = validarCor(nome, hexadecimal)
        if (!validacao.equals(""))
            throw Exception (validacao)

        var cor = SugarRecord.findById(Cor::class.java, id)
        cor.nome = nome
        cor.hexadecimal = hexadecimal

        SugarRecord.save(cor)

        return  "Atualizado com sucesso!"
    }catch (ex: Exception){
        return ex.message.toString()
    }
}

fun validarCor(nome: String?, hexadecimal: String?): String {
    if (nome == null || hexadecimal == null)
        return "Obrigatório infomar nome e hexadecimal."
    else if (nome.toString().isEmpty() || hexadecimal.toString().isEmpty())
        return "Obrigatório infomar nome e hexadecimal."
    else if (nome.toString().length < 3 || nome.toString().length > 15)
        return "Campo nome deve estar entre 3 e 15 caracteres."
    else if (existCor(nome.toString()))
        return "Nome já existe cadastrado."
    else if (existHexadecimal(hexadecimal.toString()))
        return "Hexadecimal já existe cadastrado."

    return ""
}

fun existCor(nome: String): Boolean{
    val param = nome
    var a = SugarRecord.find(Cor::class.java, "nome = ?", "param")
    if(a.size.equals(0))
        return false

    return true
}

fun existHexadecimal(nome: String): Boolean{
    val param = nome
    val a = SugarRecord.find(Cor::class.java, "hexadecimal = ?", "param")
    if(a.size.equals(0))
        return false

    return true
}