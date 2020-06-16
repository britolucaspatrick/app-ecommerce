package com.example.patri.refinado.CamadaNegocio

import com.example.patri.refinado.CamadaDados.*
import com.example.patri.refinado.Util.Constantes.ID_USUARIO
import com.orm.SugarRecord
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun  validarLogin(usuario: String?, senha: String?): String {
    if (usuario.isNullOrEmpty() || senha.isNullOrEmpty())
        return "É necessário informar o nome de usuário e senha para efetuar o login."
    else if (usuario!!.length < 4)
        return "O campo usuário deve possuir no minimo 4 caracteres."
    else if (senha!!.length < 8)
        return "O campo senha deve possuir no minimo 8 caracteres."

    return ""
}

fun autenticidadeSenha(senha: String, confsenha: String): String{
    if (!senha.equals(confsenha))
        return "As senhas devem ser iguais."

    return ""
}

fun existUsuario(usuario: String): Boolean{
    var a = SugarRecord.find(Usuario::class.java, "usuario = ?", usuario)
    if(a.size.equals(0))
        return false

    return true
}

fun existEmail(email: String): Boolean{
    var a = SugarRecord.find(Usuario::class.java, "email = ?", email)
    if(a.size.equals(0))
        return false

    return true
}

fun validacaoEmail(email: String): String {
    if (existEmail(email))
        return "E-mail informado consta como cadastrado no sistema."
    else if (!validarEmail(email))
        return "E-mail inválido."

    return ""
}

fun validarEmail(email: String): Boolean {
    val s = email.split('@')
    if (!s.size.equals(2) || s[0].length < 3 || s[1].length < 3) {
        return false
    }
    return true
}

fun novoUsuario(usuario: String, senha: String, confsenha: String, email: String): String {
    try {
        var validacao = validarLogin(usuario, senha)
        if (!validacao.equals(""))
            throw Exception(validacao)

        validacao = autenticidadeSenha(senha, confsenha)
        if (!validacao.equals(""))
            throw Exception(validacao)

        if (existUsuario(usuario))
            throw Exception("Usuário informado consta como cadastrado no sistema.")

        validacao = validacaoEmail(email)
        if (!validacao.equals(""))
            throw Exception(validacao)

        val usuario = Usuario(usuario, email, senha, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()), null)
        val id = usuario.save()

        val config = Configuracao(id, "N")
        config.save()

        return "Cadastro efetuado com sucesso."
    } catch (ex: Exception) {
        return ex.message.toString()
    }
}

fun autenticidadeLogin(usuario: String, senha: String): Boolean {
    val a = SugarRecord.listAll(Usuario::class.java)
    a.forEach {
        if (it.usuario.equals(usuario) && it.senha.equals(senha)) {
            ID_USUARIO = it.id
            return true
        }
    }

    return false
}

fun logarUsuario(usuario: String, senha: String): String {
    try {
        if (!autenticidadeLogin(usuario, senha))
            throw Exception("Usuário/ Senha inválidos.")

        return ""
    } catch (ex: Exception) {
        return "Erro: ${ex.message}"
    }
}

fun salvarUsuario(iD_USUARIO: Long?, email: String, usuario: String, nome: String, sobrenome: String, senha: String, cpf: String, logradouro: String, complemento: String, bairro: String, numero: String, cep: String, cidade: String, iD_ESTADO: Long?, telefone: String, aniversario: String): String{
    try{
        var validacao = validarUsuario(iD_USUARIO, email, usuario, nome, sobrenome, senha, cpf, logradouro, complemento, bairro, numero, cep, cidade, iD_ESTADO, telefone, aniversario)
        if (!validacao.equals(""))
            throw Exception(validacao)

        val usuario = buscarUsuarioPedido()

        if (usuario!!.pessoa == null){
            //inverter aniversario no momento nao informado
            var pessoa = Pessoa(nome, sobrenome, cpf, formataAniversario(aniversario), "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis()))
            val id = pessoa.save()
            var endereco = Endereco(id, iD_ESTADO, cidade, telefone, bairro, numero, complemento, logradouro, cep, "A", Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())).save()
            usuario!!.pessoa = id
        }else{
            usuario!!.pessoaP!!.nome = nome
            usuario!!.pessoaP!!.sobrenome = sobrenome
            usuario!!.pessoaP!!.cpf = cpf
            usuario!!.pessoaP!!.aniversario = formataAniversario(aniversario)
            usuario!!.pessoaP!!.alteracao = Timestamp(System.currentTimeMillis())
            usuario!!.pessoaP!!.save()

            usuario!!.endereco!!.estado = iD_ESTADO
            usuario!!.endereco!!.cidade = cidade
            usuario!!.endereco!!.telefone = telefone
            usuario!!.endereco!!.bairro = bairro
            usuario!!.endereco!!.numero = numero
            usuario!!.endereco!!.complemento = complemento
            usuario!!.endereco!!.enderedo = logradouro
            usuario!!.endereco!!.cep = cep
            usuario!!.endereco!!.alteracao = Timestamp(System.currentTimeMillis())
            usuario!!.endereco!!.save()
        }

        usuario!!.senha = senha
        usuario!!.alteracao = Timestamp(System.currentTimeMillis())
        usuario!!.save()

        return "Cadastro salvo com sucesso."
    }catch (ex: Exception){
        return "Erro: ${ex.message}"
    }
}

fun formataAniversario(aniversario: String): Date? {
    val s = aniversario.split("/")
    if (!s.size.equals(3))
        return null

    return Date.valueOf(aniversario.replace("/","-"))
}

fun validarUsuario(iD_USUARIO: Long?, email: String, usuario: String, nome: String, sobrenome: String, senha: String, cpf: String, logradouro: String, complemento: String, bairro: String, numero: String, cep: String, cidade: String, iD_ESTADO: Long?, telefone: String, aniversario: String): String{
    if (iD_USUARIO == null || email.isNullOrEmpty() || usuario.isNullOrEmpty() || nome.isNullOrEmpty() || sobrenome.isNullOrEmpty() || senha.isNullOrEmpty() || cpf.isNullOrEmpty() || logradouro.isNullOrEmpty() || complemento.isNullOrEmpty() || bairro.isNullOrEmpty() || numero.isNullOrEmpty() || cep.isNullOrEmpty() || cidade.isNullOrEmpty() || iD_ESTADO == null)
        return "É necessário informar os campos obrigatórios para salvar o cadastro."
    else if (!cpf.length.equals(11))
        return "O campo CPF deve ter 11 caracteres."
    else if (logradouro.length < 3)
        return "O campo logradouro deve ter no mínimo 3 caracteres."
    else if (!validarEmail(email))
        return "O campo e-mail está informado incorretamente."
    else if (!soNumero(cep).length.equals(8))
        return "O campo cep deve ter 8 caracteres."
    else if (bairro.length < 3)
        return "O campo bairro deve ter no mínimo 3 caracteres."
    else if (cidade.length < 3)
        return "O campo cidade deve ter no mínimo 3 caracteres."
    else if (!telefone.length.equals(10))
        return "O campo telefone móvel deve ter 10 caracteres."
    else if (aniversario == null || !aniversario!!.length.equals(10))
        return "A data de nascimento deve ser enquadrada na máscara aaaa/MM/dd."


    return ""
}

fun soNumero(cep: String) : String{
    if (cep.isNullOrEmpty())
        return ""
    var s = ""
    val char = cep.split("")
    char.forEach {
        try{
            s += it.toInt()
        }catch (ex: Exception){

        }
    }
    return s
}




