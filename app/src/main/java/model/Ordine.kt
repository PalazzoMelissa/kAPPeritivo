package model

/**
 * Created by melissa on 09/01/19.
 */
class Ordine {

    private var codice=0
    private var tavolo=0
    private var cameriere= ""
    private var conto= 0.0



    fun setCodice(codice: Int) {this.codice = codice}

    fun setTavolo(tavolo: Int) {this.tavolo = tavolo}

    fun setCameriere(cameriere: String) {this.cameriere = cameriere}


    fun getCodice(): Int {return codice}

    fun getTavolo(): Int? {return tavolo}

    fun getCameriere(): String {return cameriere}

    fun setConto(conto: Double) {
        this.conto = conto
    }

    fun getConto(): Double {
        return conto
    }
}
