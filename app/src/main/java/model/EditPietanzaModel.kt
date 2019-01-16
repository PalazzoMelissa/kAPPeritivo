package model

class EditPietanzaModel {
    private lateinit var quantita: String
    private lateinit var nomePietanza: String
    private var prezzo: Double= 0.0
    private lateinit var descrizione: String

    fun getQuantita(): String {return quantita}

    fun getNomePietanza(): String {return nomePietanza}

    fun getPrezzo(): Double {return prezzo}

    fun getDescrizione(): String {return descrizione}

    fun setQuantita(quantita: String) {this.quantita = quantita}

    fun setNomePietanza(nomePietanza: String) {this.nomePietanza = nomePietanza}

    fun setPrezzo(prezzo: Double) {this.prezzo = prezzo}

    fun setDescrizione(descrizione: String) {this.descrizione = descrizione}
}