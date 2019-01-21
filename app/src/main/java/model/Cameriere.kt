package model

/**
 * Created by melissa on 09/01/19.
 */
class Cameriere {

    private var username= ""
    private var nome= ""
    private var cognome= ""
    private var num_telefono= ""


    //getters
    fun getUsername(): String {return username}

    fun getNome(): String {return nome}

    fun getCognome(): String {return cognome}

    fun getNumTel(): String {return num_telefono}


    //setters
    fun setUsername(username: String) {this.username= username}

    fun setNome(nome: String) {this.nome= nome}

    fun setCognome(cognome: String) {this.cognome= cognome}

    fun setNumTel(num_telefono: String) {this.num_telefono= num_telefono}






}