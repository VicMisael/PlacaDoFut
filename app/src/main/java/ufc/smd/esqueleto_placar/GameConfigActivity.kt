package ufc.smd.esqueleto_placar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import ufc.smd.esqueleto_placar.data.game.Cup
import ufc.smd.esqueleto_placar.data.game.CustomGame
import ufc.smd.esqueleto_placar.data.game.Game

class GameConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_config)

        val time: EditText = findViewById(R.id.time)
        val halfs: EditText = findViewById(R.id.manyHalf)
//        val extraTime: EditText = findViewById(R.id.extraTime)
        val bt: Button = findViewById(R.id.button)

        bt.setOnClickListener {
            val gamename: String = intent.getStringExtra("gamename").toString()
            val eqp1: String = intent.getStringExtra("eqp1").toString()
            val eqp2: String = intent.getStringExtra("eqp2").toString()
            Log.v("PDM", "gamename $gamename")
            Log.v("PDM", "eqp1 $eqp1")
            Log.v("PDM", "eqp2 $eqp2")
            Log.v("PDM", "time ${time.text}")
            Log.v("PDM", "halfs ${halfs.text}")
            val game: Game = CustomGame(gamename,eqp1,eqp2, 60 * time.text.toString().toInt(), halfs.text.toString().toInt())

            val intent = Intent(this, PlacarActivity::class.java).apply{
                putExtra("game",game)
            }
            startActivity(intent)
        }
    }
}