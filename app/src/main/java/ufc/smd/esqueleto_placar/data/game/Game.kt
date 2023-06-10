package ufc.smd.esqueleto_placar.data.game

import android.util.Log
import ufc.smd.esqueleto_placar.data.game.events.Card
import ufc.smd.esqueleto_placar.data.game.events.CardColor
import ufc.smd.esqueleto_placar.data.game.events.EventType
import ufc.smd.esqueleto_placar.data.game.events.Score
import java.io.Serializable

abstract class Game(
    var nome_partida: String,
    var nome_equipe1: String,
    var nome_equipe2: String,
    var has_timer: Boolean,
    var seconds: Int,
    var halfs: Int,
    var events: ArrayDeque<EventType> = ArrayDeque()
) : Serializable {

    var team1: Team = Team(nome_equipe1);
    var team2: Team = Team(nome_equipe2);


    val scoreTeamOne get() = team1.gols;
    val scoreTeamTwo get() = team2.gols;


    private fun addEvent(event: EventType) {
        events.addLast(event);
    }

    fun rollback() {
        val event = events.removeLast()
        val selTeam: Team = when (event.teamNumber) {
            1 -> team1;
            2 -> team2;
            else -> {
                Log.e("VemProFut", "Equipe Invalida");
                return
            }
        }
        when(event){
            is Score->selTeam.gols--;
            is Card -> {
                when(event.cardColor){
                    CardColor.YELLOW -> selTeam.yellowCards--;
                    CardColor.RED -> selTeam.redCards--
                    CardColor.BLUE -> selTeam.yellowCards--
                }
            }
        }

    }

    fun scoreTeamOne() {
        addEvent(Score(1));
        team1.gols++;
    }

    fun scoreTeamTwo() {
        addEvent(Score(2))
        team2.gols++;
    }

    fun cardTeamOne(color: CardColor) {
        addEvent(Card(1, color))
    }

    fun cardTeamTwo(color: CardColor) {
        addEvent(Card(2, color))
    }

    abstract fun getNome(): String;
    override fun toString(): String {
        return getNome();
    }
}