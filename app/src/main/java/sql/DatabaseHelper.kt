package sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.Cameriere
import model.Ordine
import model.Tavolo

/**
 * Scopi :
 * >Interazioni con il database
 *
 *@authors Gianluca Giacometti, Melissa Palazzo, Marco Bonavoglia
 *@version : 1.0
 *
 */

class DatabaseHelper (context: Context): SQLiteOpenHelper(context, "kAPPeritivo.db", null, 1) {

    //creazione del cameriere con i suoi dati
    private val CREATE_TABLE_CAMERIERE = "CREATE TABLE if not exists Cameriere (\n"+
            "username varchar(100) not null primary key, \n" +
            "nome varchar(50) not null, \n" +
            "cognome varchar(50) not null, \n" +
            "num_telefono varchar(10) not null\n"+
    ")"


    //inserimento tavoli
    private val CREATE_TABLE_TAVOLO = "CREATE TABLE if not exists tavolo(" +
            "  numero int not null primary key" +
            ")"

    //tabella degli ordini
    private val CREATE_TABLE_ORDINE = "CREATE TABLE if not exists ordine(\n" +
            "  codice  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  tavolo int references tavolo(numero)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  cameriere not null references cameriere(username)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            " conto float\n" +
            ")"

    //inserimento della pietanza per ogni ordine
    private val CREATE_TABLE_COMPOSTO = "create table if not exists composto(\n" +
            "  pietanza varchar(50) not null references pietanza(nome)\n" +
            "  on delete no action\n" +
            "  on update cascade,\n" +
            "  ordine int not null references ordine(codice)\n" +
            "  on update cascade\n" +
            "  on delete no action,\n" +
            "  quantita_pietanza int not null,\n " +
            "  modifica varchar(100),\n" +
            "  primary key(pietanza,ordine)\n" +
            ")"


    //nome della pietanza del menù con costo e descrizione
    private val CREATE_TABLE_PIETANZA = "create table if not exists pietanza(\n" +
            "  nome varchar(50) not null primary key,\n" +
            "  categoria varchar(50) not null,\n" +
            "  costo float not null,\n" +
            "  descrizione varchar(200) not null\n" +
            ")"


    override fun onCreate(db : SQLiteDatabase)
    {
        //creo tutte le tabelle del database
        db.execSQL(CREATE_TABLE_PIETANZA)
        db.execSQL(CREATE_TABLE_TAVOLO)
        db.execSQL(CREATE_TABLE_ORDINE)
        db.execSQL(CREATE_TABLE_COMPOSTO)
        db.execSQL(CREATE_TABLE_CAMERIERE)
    }



    private val DROP_TABLE = ""

    override fun onUpgrade(db : SQLiteDatabase, oldVersion : Int, newVersion : Int)
    {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }


    //aggiunge una tupla alla tabella cameriere
    fun addCameriere(cameriere : Cameriere)
    {
        var db : SQLiteDatabase = this@DatabaseHelper.writableDatabase
        var values= ContentValues()

        values.put("username", cameriere.getUsername())
        values.put("nome", cameriere.getNome())
        values.put("cognome", cameriere.getCognome())
        values.put("num_telefono", cameriere.getNumTel())

        db.insert("Cameriere", null, values)

    }

    //verifico se il cameriere con un dato username è registrato
    fun checkCameriere(username: String): Boolean
    {
        //query di ricerca
        var columns= arrayOf("username")

        var db: SQLiteDatabase= this.writableDatabase
        var selection: String= "username =?"

        var selectionArgs=arrayOf(username)
        var cursor: Cursor= db.query("Cameriere", columns, selection, selectionArgs, null, null, null)

        //conto quanti camerieri ho trovato
        var camerieri_trovati: Int= cursor.count
        cursor.close()

        //dice se ha trovato o meno il cameriere cercato
        return camerieri_trovati > 0

    }



    //inserisco una pietanza tramite stringa SQL nel database
    fun inserisciPietanze(pietanze: String)
    {
        var db: SQLiteDatabase= this.writableDatabase
        db.execSQL(pietanze)
    }


    //inserisco un ordine nella tabella
    fun addOrdine(ordine: Ordine): Int
    {
        var db: SQLiteDatabase= this.writableDatabase
        var values= ContentValues()
        values.put("tavolo", ordine.getTavolo())
        values.put("cameriere", ordine.getCameriere())
        values.put("conto",ordine.getConto())

        return Integer.parseInt(""+db.insert("ordine", null, values))
    }


    //aggiungo un tavolo
    fun addTavolo(tavolo: Tavolo)
    {
        var db: SQLiteDatabase= this.writableDatabase
        var values= ContentValues()
        var columns= arrayOf("numero")
        var selection= "numero= ?"
        var numero: String= "" + tavolo.getNumero()
        var selectionArgs= arrayOf(numero)
        var cursor: Cursor= db.query("tavolo", columns, selection, selectionArgs, null, null, null)

        //se il numero del tavolo non è presente -> lo aggiungo
        if(cursor.count == 0)
        {
            values.put("numero", tavolo.getNumero())
            db.insert("tavolo", null, values)
        }

    }


    //aggiungo la pietanza ad un ordine
    fun addComposto(ordine: Ordine, pietanza: String, quantita: Int, modifica: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ordine", ordine.getCodice())
        values.put("pietanza", pietanza)
        values.put("quantita_pietanza", quantita)
        values.put("modifica", modifica)
        return db.insert("composto", null, values).toInt()
    }


    //ricerca tramite query le pietanze in base alla categoria richiesta
    fun vediPietanze(categoria: String): Cursor
    {
        var columns= arrayOf("nome", "costo", "descrizione", "categoria")
        var db: SQLiteDatabase= this.writableDatabase
        var selection= "categoria= ?"
        var selectionArgs: Array<String> = arrayOf(categoria)
        var cursor: Cursor= db.query("pietanza", columns, selection, selectionArgs, null, null, "nome")

        return cursor

    }

    //ricerco tutti gli ordini di un dato cameriere
    fun ordini_cameriere(cameriere: Cameriere): Cursor {
        var columns = arrayOf("codice", "conto")
        var db = this.readableDatabase
        var selection = "cameriere=?"
        var selectionArgs = arrayOf(cameriere.getUsername())
        return db.query("ordine", columns, selection, selectionArgs, null, null, null)
    }


    //inserimento di tutte le pietanze del menù nel database
    fun createMenu() {
        inserisciPietanze("INSERT OR IGNORE INTO pietanza (nome, categoria, descrizione, costo)\n" +
                "VALUES " +
                "" +
                "('Spaghetti alle vongole', 'primo', 'Spaghetti con vongole veraci, prezzemolo, un filo di olio', 9.5),\n" +
                "('Bigoli con anatra', 'primo', 'Bigoli al ragù di anatra con scaglie di formaggio grana', 9.5),\n" +
                "('Gnocchi al ragù', 'primo', 'Gnocchi di patate fatti in casa con ragù di manzo', 9),\n" +
                "('Risotto di pesce', 'primo', 'Risotto con vongole, gamberetti e pescato del giorno', 13),\n" +
                "('Tagliatelle al ragù', 'primo', 'Tagliatelle fatte in casa con ragù di manzo', 9),\n" +
                "('Gnocchi al pomodoro', 'primo', 'Gnocchi di patate fatti in casa con passata di pomodoro', 8),\n" +
                "('Tartare di verdure', 'secondo', 'Tartare con verdure di stagione', 10),\n" +
                "('Grigliata mista di pesce', 'secondo', 'Grigliata di pesce fresco con patate al forno', 18),\n" +
                "('Grigliata mista di carne', 'secondo', 'Ossetti, salsiccia, pancetta e polenta', 16),\n" +
                "('Tagliata di tonno', 'secondo', 'Tagliata di tonno con verdure di stagione', 12),\n" +
                "('Tagliata di manzo', 'secondo', 'Tagliata di manzo con verdure di stagione', 10),\n" +
                "('Misto di formaggi e affettati', 'antipasto', 'Formaggi stagionati, ricotta e affettati freschi della casa', 9),\n" +
                "('Vongole in crostino', 'antipasto', 'Vongole fresche di giornata con crostini', 6),\n" +
                "('Antipasto vegetariano', 'antipasto', 'Mix di verdure cotte e crude di stagione', 6),\n" +
                "('Crostini al salmone', 'antipasto', 'Crostini con burro e salmone selvaggio affumicato', 10),\n" +
                "('Crostata', 'dolce', 'Crostata con marmellata alle fragole fatta in casa', 5),\n" +
                "('Coppa gelato', 'dolce', 'Coppa con gelato alla panna e al cioccolato', 4),\n" +
                "('Profiteroles bianco', 'dolce', 'Profiteroles al cioccolato bianco', 4),\n" +
                "('Profiteroles nero', 'dolce', 'Profiteroles al cioccolato al latte', 4),\n" +
                "('Birra bionda', 'bevanda', 'Birra Moretti bionda da 0.5 l', 5),\n" +
                "('Birra rossa', 'bevanda', 'Birra Moretti rossa da 0.5 l', 5),\n" +
                "('Vino rosso', 'bevanda', 'Bottiglia da 0.75 l di Cabernet franc', 4),\n" +
                "('Vino bianco', 'bevanda', 'Bottiglia da 0.75 l di Verduzzo', 4),\n" +
                "('Acqua naturale', 'bevanda', 'Bottiglia da 0.75 l', 2.50),\n" +
                "('Acqua gassata', 'bevanda', 'Bottiglia da 0.75 l', 2.50),\n" +
                "('Caffè', 'bevanda', 'Caffè espresso', 1.50),\n" +
                "('Grappa', 'bevanda', 'Grappa fatta in casa', 3)\n"
        )
    }



    //tra tutti gli ordini mostra tutte le pietanze con la quantità e modifica con lo stesso numero di ordine
    fun vedi_pietanze_ordine(codiceOrdine: Int): Cursor
    {
        var columns= arrayOf("pietanza", "quantita_pietanza", "modifica")
        var db= this.readableDatabase
        var selection= "ordine=?"
        var selectionArgs= arrayOf(""+codiceOrdine)
        var cursor= db.query("composto", columns, selection, selectionArgs, null, null, null)

        return cursor
    }

}