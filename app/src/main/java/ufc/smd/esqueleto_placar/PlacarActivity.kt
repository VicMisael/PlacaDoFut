package ufc.smd.esqueleto_placar

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock

import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ufc.smd.esqueleto_placar.data.game.Game
import ufc.smd.esqueleto_placar.data.game.events.CardColor
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets

class PlacarActivity : AppCompatActivity() {
    lateinit var game: Game
    lateinit var tvResultadoJogo: TextView
    lateinit var chronometer: Chronometer
    lateinit var textViewAcrescimo: TextView
    lateinit var pause: Button;
    lateinit var txtViewNumCardYellow1: TextView;
    lateinit var txtViewNumCardYellow2: TextView;
    lateinit var txtViewNumCardRed1: TextView;
    lateinit var txtViewNumCardRed2: TextView;
    var acrescimoVal = 0;
    var isPaused = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_placar)
        game = getIntent().getExtras()?.getSerializable("game") as Game
        tvResultadoJogo = findViewById(R.id.tvPlacar)
        chronometer = findViewById(R.id.c_meter)
        val goalTeam1: FloatingActionButton = findViewById(R.id.goalTeam2);
        val goalTeam2: FloatingActionButton = findViewById(R.id.goalTeam1);
        val rollback: ImageButton = findViewById(R.id.rollback);
        val acrescimo: FloatingActionButton = findViewById(R.id.acrescimo);

        val textViewTime1: TextView = findViewById(R.id.time1);
        val textViewTime2: TextView = findViewById(R.id.time2);

        val yellowBtn1: Button = findViewById(R.id.yellowTeam1);
        val yellowBtn2: Button = findViewById(R.id.yellowTeam2);
        val redBtn1: Button = findViewById(R.id.redTeam1);
        val redBtn2: Button = findViewById(R.id.redTeam2);

        txtViewNumCardYellow1 = findViewById(R.id.numYellowsT1)
        txtViewNumCardYellow2 = findViewById(R.id.numYellowsT2)

        txtViewNumCardRed1 = findViewById(R.id.numRedsT1);
        txtViewNumCardRed2 = findViewById(R.id.numRedsT2);

        yellowBtn1.setOnClickListener {
            game.cardTeamOne(CardColor.YELLOW)
            update()
        }
        yellowBtn2.setOnClickListener {
            game.cardTeamTwo(CardColor.YELLOW)
            update()
        }

        redBtn1.setOnClickListener {
            game.cardTeamOne(CardColor.RED)
            update()
        }
        redBtn2.setOnClickListener {
            game.cardTeamTwo(CardColor.RED)
            update()
        }

        textViewAcrescimo = findViewById(R.id.acrescimoTxt);
        textViewAcrescimo.text = "";

        textViewTime1.text = game.nome_equipe1
        textViewTime2.text = game.nome_equipe2

        pause = findViewById(R.id.pausar);
        pause.setOnClickListener {
            pauseContinueAction();
        }

        acrescimo.hide();
        pauseContinueAction();
        acrescimo.setOnClickListener {
            if (!isPaused) {
                acrescimoVal++;
                update();
            }
        }
        rollback.setOnClickListener {
            if (!isPaused) {
                game.rollback()
                update()
            }
        }

        goalTeam2.setOnClickListener {
            if (!isPaused) {
                game.scoreTeamTwo();
                update()
            }
        }

        goalTeam1.setOnClickListener {
            if (!isPaused) {
                game.scoreTeamOne();
                update()
            }
        }

        chronometer.setOnChronometerTickListener {
            if (!isPaused) {
                game.pastSeconds++;
                if (game.pastSeconds >= game.seconds * 0.95) {
                    acrescimo.show()
                }
                if (game.pastSeconds >= acrescimoVal + game.seconds) {
                    chronometer.base = SystemClock.elapsedRealtime();
                    acrescimoVal = 0;
                    chronometer.stop();
                    game.pastSeconds = 0;
                    game.half++;
                    if (game.half > game.halfs) {
                        saveGame();
                    }
                    pauseContinueAction();
                }

            }
        }

        chronometer.start()
    }

    private fun pauseContinueAction() {
        if (isPaused) {
            isPaused = false;
            pause.text = "Pausar"
            chronometer.start();

        } else {
            isPaused = true;
            pause.text = "Continuar"
            chronometer.stop();

        }
    }

    private fun update() {
        val placarEs: TextView = findViewById(R.id.tvPlacar2)
        val placarDi: TextView = findViewById(R.id.tvPlacar)
        placarEs.text = game.team1.gols.toString()
        placarDi.text = game.team2.gols.toString()
        txtViewNumCardYellow1.text = game.yellowCardsTeam1.toString();
        txtViewNumCardYellow2.text = game.yellowCardsTeam2.toString();

        txtViewNumCardRed1.text = game.redCardsTeam1.toString();
        txtViewNumCardRed2.text = game.redCardsTeam2.toString();

        if (acrescimoVal > 1) {
            acrescimoVal
            textViewAcrescimo.text = "+ $acrescimoVal";
        }

    }


    fun saveGame() {

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

//    fun lerUltimosJogos(v: View) {
//        val sharedFilename = "PreviousGames"
//        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
//
//        var meuObjString: String = sp.getString("match1", "").toString()
//        if (meuObjString.length >= 1) {
//            var dis = ByteArrayInputStream(meuObjString.toByteArray(Charsets.ISO_8859_1))
//            var oos = ObjectInputStream(dis)
//            var placarAntigo = oos.readObject()
//            Log.v("SMD26", game.nome_equipe1)
//        }
//    }

}