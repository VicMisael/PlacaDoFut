package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ufc.smd.esqueleto_placar.data.Placar
import ufc.smd.esqueleto_placar.data.game.Game
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets

class PlacarActivity : AppCompatActivity() {
    lateinit var game: Game
    lateinit var tvResultadoJogo: TextView
    lateinit var chronometer: Chronometer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placar)
        game = getIntent().getExtras()?.getSerializable("game") as Game
        tvResultadoJogo = findViewById(R.id.tvPlacar)
        chronometer = findViewById(R.id.c_meter)
        val goalTeam1:FloatingActionButton = findViewById(R.id.goalTeam1);
        val goalTeam2:FloatingActionButton = findViewById(R.id.goalTeam2);
        val rollback:FloatingActionButton =findViewById(R.id.rollback);
        rollback.setOnClickListener {
            game.rollback()
            update()
        }

        goalTeam2.setOnClickListener {
            game.scoreTeamTwo();
            update()
        }

        goalTeam1.setOnClickListener {
            game.scoreTeamOne();
            update()
        }

        chronometer.setOnChronometerTickListener {
            game.pastSeconds++
        }

        chronometer.start()
    }

    private fun update() {
        val placarEs: TextView = findViewById(R.id.tvPlacar2)
        val placarDi: TextView = findViewById(R.id.tvPlacar3)
        placarEs.text = game.team1.gols.toString()
        placarDi.text = game.team2.gols.toString()

    }


    fun saveGame(v: View) {

        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        val edShared = sp.edit()
        //Salvar o número de jogos já armazenados
        val numMatches = sp.getInt("numberMatch", 0) + 1
        edShared.putInt("numberMatch", numMatches)

        //Escrita em Bytes de Um objeto Serializável
        val dt = ByteArrayOutputStream()
        val oos = ObjectOutputStream(dt);
        oos.writeObject(game);

        //Salvar como "match"
        edShared.putString("match" + numMatches, dt.toString(StandardCharsets.ISO_8859_1.name()))
        edShared.apply() //Não esqueçam de comitar!!!

    }

    fun lerUltimosJogos(v: View) {
        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)

        var meuObjString: String = sp.getString("match1", "").toString()
        if (meuObjString.length >= 1) {
            var dis = ByteArrayInputStream(meuObjString.toByteArray(Charsets.ISO_8859_1))
            var oos = ObjectInputStream(dis)
            var placarAntigo: Game = oos.readObject() as Game
            Log.v("SMD26", game.nome_equipe1)
        }
    }

}