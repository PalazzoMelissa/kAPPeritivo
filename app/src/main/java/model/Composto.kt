package model

class Composto {
    private var pietanza= ""
    private var quantita= 0
    private var modifica= ""
    private var codiceOrdine= 0

    fun getPietanza(): String {
        return pietanza
    }

    fun setPietanza(pietanza: String) {
        this.pietanza = pietanza
    }

    fun getQuantita(): Int {
        return quantita
    }

    fun setQuantita(quantita: Int) {
        this.quantita = quantita
    }

    fun getModifica(): String {
        return modifica
    }

    fun setModifica(modifica: String) {
        this.modifica = modifica
    }

    fun getCodiceOrdine(): Int {
        return codiceOrdine
    }

    fun setCodiceOrdine(codiceOrdine: Int) {
        this.codiceOrdine = codiceOrdine
    }
}