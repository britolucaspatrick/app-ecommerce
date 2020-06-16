package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.Favorito
import com.orm.SugarRecord
import java.lang.Exception

fun salvarFavorito(usuario: Long?, produto: Long): String {

    try{

        val validacao = validarFavorito(produto)
        if (!validacao.equals(""))
            throw Exception (validacao)

        val favorito = Favorito(usuario, produto)
        favorito.save()

        return "Produto adicionado aos favoritos."
    }catch (exception: Exception){

        return "Erro: ${exception.message}"
    }
}

fun validarFavorito(produto: Long): String {
    if (existsFavorito(produto))
        return "Não é possível adicionar produtos iguais a listagem de favoritos."

    return ""
}

fun existsFavorito(produto: Long): Boolean {
    val param = produto
    var parar = false
    val f = SugarRecord.listAll(Favorito::class.java)
    f.forEach {
        if (it.produto == param)
        {
            parar = true
        }
    }

    return parar
}
