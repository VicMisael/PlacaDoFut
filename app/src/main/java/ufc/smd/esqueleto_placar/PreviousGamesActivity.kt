package ufc.smd.esqueleto_placar

import adapters.CustomAdapter
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ufc.smd.esqueleto_placar.data.Placar
import ufc.smd.esqueleto_placar.data.game.Game
import ufc.smd.esqueleto_placar.data.game.Normal
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PreviousGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_games)

        // Trazendo o Recycler View
        val recyclerview = findViewById<RecyclerView>(R.id.rcPreviousGames)

        // Tipo de Layout Manager será Linear
        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = readPLacarDataSharedPreferences()


        // ArrayList enviado ao Adapter
        val adapter = CustomAdapter(data)

        // Setando o Adapter no Recyclerview
        recyclerview.adapter = adapter

    }

    fun readPLacarDataSharedPreferences(): ArrayList<Game> {
        Log.v("PDM", "Lendo o Shared Preferences")
        val data = ArrayList<Game>()
        val sharedFileName = "PreviousGames"
        var aux: String
        var sp: SharedPreferences = getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        if (sp != null) {
            var numMatches = sp.getInt("numberMatch", 0)
            Log.v("PDM", "numMatchs:" + numMatches)
            for (i in 1..numMatches) {
                aux = sp.getString("match" + i, "vazio")!!
                if (!aux.equals("vazio")) {

                    var bis: ByteArrayInputStream
                    bis = ByteArrayInputStream(aux.toByteArray(Charsets.ISO_8859_1))
                    var obi: ObjectInputStream
                    obi = ObjectInputStream(bis)

                    var placar:Game = obi.readObject() as Game
                    data.add(placar)
                    //Log.v("PDM", "match"+i+" :"+aux)
                    Log.v("PDM", "Placar: $placar");
                }
            }
        }
        return data
    }

    fun readPLacarData() {
        Log.v("PDM", "Lendo o Shared Preferences")
        val sharedFileName = "PreviousGames"
        var aux: String
        val sp: SharedPreferences = getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        if (sp != null) {
            val numMatches = sp.getInt("numberMatch", 0)
            Log.v("PDM", "numMatchs:" + numMatches)
            for (i in 1..numMatches) {
                aux = sp.getString("match" + i, "vazio")!!
                if (!aux.equals("vazio")) {

                    var bis: ByteArrayInputStream
                    bis = ByteArrayInputStream(aux.toByteArray(Charsets.ISO_8859_1))
                    var obi: ObjectInputStream
                    obi = ObjectInputStream(bis)

                    val placar: Game = obi.readObject() as Game

                    //Log.v("PDM", "match"+i+" :"+aux)
                    Log.v("PDM", "Placar: " + placar.nome_partida + " Res:" + placar.toString())
                }
            }
        }

    }
}
