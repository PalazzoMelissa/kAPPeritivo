package model

/**
 * Created by melissa on 09/01/19.
 */
class Pietanza {
    private var nome= ""
    private var descrizione= ""
    private var prezzo: Double = 0.0
    private var categoria= ""

    fun setNome(nome: String) {this.nome = nome}

    fun setDescrizione(descrizione: String) {this.descrizione = descrizione}

    fun setPrezzo(prezzo: Double) {this.prezzo = prezzo}

    fun setCategoria(categoria: String) {this.categoria = categoria}


    fun getNome(): String {return nome}

    fun getDescrizione(): String {return descrizione}

    fun getPrezzo(): Double {return prezzo}

    fun getCategoria(): String {return categoria}
}