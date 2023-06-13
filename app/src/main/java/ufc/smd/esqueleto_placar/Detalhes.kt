package ufc.smd.esqueleto_placar

import adapters.CustomAdapter
import adapters.EventsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ufc.smd.esqueleto_placar.data.game.Game

class Detalhes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        val game = getIntent().getExtras()?.getSerializable("game") as Game
        val recyclerview = findViewById<RecyclerView>(R.id.rcEvents)

        recyclerview.layoutManager = LinearLayoutManager(this)


        // ArrayList enviado ao Adapter
        val adapter = EventsAdapter(game.events)

        // Setando o Adapter no Recyclerview
        recyclerview.adapter = adapter

        findViewById<TextView>(R.id.nmEquipe1).text = game.nome_equipe1
        findViewById<TextView>(R.id.nmEquipe2).text = game.nome_equipe2
        findViewById<TextView>(R.id.team1Goal).text = game.team1.gols.toString()
        findViewById<TextView>(R.id.team2Goal).text = game.team2.gols.toString()

        findViewById<TextView>(R.id.nmJogo).text = game.nome_partida
        findViewById<TextView>(R.id.nmModo).text = game.getNome()

    }
}