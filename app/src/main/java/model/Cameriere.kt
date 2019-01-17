package model

/**
 * Created by melissa on 09/01/19.
 */
class Cameriere {

    private lateinit var username: String
    private lateinit var nome: String
    private lateinit var cognome: String
    private lateinit var num_telefono: String


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