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
    public fun getUsername(): String {return username}

    public fun getNome(): String {return nome}

    public fun getCognome(): String {return cognome}

    public fun getNumTel(): String {return num_telefono}


    //setters
    public fun setUsername(username: String) {this.username}

    public fun setNome(nome: String) {this.nome}

    public fun setCognome(cognome: String) {this.cognome}

    public fun setNumTel(num_telefono: String) {this.num_telefono}






}