package com.example.patri.refinado.CamadaDados

import android.graphics.Bitmap
import android.widget.ImageView
import com.orm.SugarRecord

class ProdutoImagem : SugarRecord{
    var produto: Long? = null
    var imagem: ByteArray? = null
    var posicao: Int? = null

    constructor(){}

    constructor(produto: Long?, imagem: ByteArray?, posicao: Int?) {
        this.produto = produto
        this.imagem = imagem
        this.posicao = posicao
    }
}