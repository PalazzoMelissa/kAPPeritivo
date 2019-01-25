package model

import java.io.Serializable
//Utlizzo Serializable per poter passare tale oggetto attraverso Intent
//tipo di oggetto complesso che può essere passato tra un'activity e un'altra
class EditPietanzaModel: Serializable {
    private var quantita= ""
    private var nomePietanza= ""
    private var prezzo: Double= 0.0
    private var descrizione= ""

    fun getQuantita(): String {return quantita}

    fun getNomePietanza(): String {return nomePietanza}

    fun getPrezzo(): Double {return prezzo}

    fun getDescrizione(): String {return descrizione}

    fun setQuantita(quantita: String) {this.quantita = quantita}

    fun setNomePietanza(nomePietanza: String) {this.nomePietanza = nomePietanza}

    fun setPrezzo(prezzo: Double) {this.prezzo = prezzo}

    fun setDescrizione(descrizione: String) {this.descrizione = descrizione}
}