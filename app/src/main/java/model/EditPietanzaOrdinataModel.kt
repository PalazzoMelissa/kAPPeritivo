package model

import java.io.Serializable
//Utlizzo Serializable per poter passare tale oggetto attraverso Intent
class EditPietanzaOrdinataModel: Serializable {
    private var nomePietanza= ""
    private var quantita: Int = 0
    private var costo: Double = 0.0
    private var modifica= ""

    fun getNomePietanza(): String? {
        return nomePietanza
    }

    fun setNomePietanza(nomePietanza: String) {this.nomePietanza = nomePietanza}

    fun setQuantita(quantita: Int) {this.quantita = quantita}

    fun setCosto(costo: Double) {this.costo = costo}

    fun getModifica(): String? {return modifica}

    fun setModifica(modifica: String) {this.modifica = modifica}

    fun getQuantita(): Int {return quantita}

    fun getCosto(): Double {return costo}
}