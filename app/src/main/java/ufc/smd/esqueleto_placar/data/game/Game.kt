package ufc.smd.esqueleto_placar.data.game

import android.util.Log
import ufc.smd.esqueleto_placar.data.game.events.Card
import ufc.smd.esqueleto_placar.data.game.events.CardColor
import ufc.smd.esqueleto_placar.data.game.events.EventType
import ufc.smd.esqueleto_placar.data.game.events.Score
import java.io.Serializable
import java.util.Stack

abstract class Game(
    var nome_partida: String,
    var nome_equipe1: String,
    var nome_equipe2: String,
    var seconds: Int,
    var halfs: Int,
    var half: Int = 0,
    var pastSeconds: Int = 0,
    var events: Stack<EventType> = Stack()
) : Serializable {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 6529685098267757690L
    }
    var team1: Team = Team(nome_equipe1);
    var team2: Team = Team(nome_equipe2);


    val scoreTeamOne get() = team1.gols;
    val scoreTeamTwo get() = team2.gols;

    val description get() = " A partida $nome_partida terminou com ${team1.gols} pra $nome_equipe1 e ${team2.gols} para a $nome_equipe2 "

    private fun addEvent(event: EventType) {
        events.push(event);
    }

    fun rollback() {
        if (!events.empty()) {
            val event = events.pop()
            val selTeam: Team = when (event.teamNumber) {
                1 -> team1;
                2 -> team2;
                else -> {
                    Log.e("VemProFut", "Equipe Invalida");
                    return
                }
            }
            when (event) {
                is Score -> selTeam.gols--;
                is Card -> {
                    when (event.cardColor) {
                        CardColor.YELLOW -> selTeam.yellowCards--;
                        CardColor.RED -> selTeam.redCards--
                        CardColor.BLUE -> selTeam.yellowCards--
                    }
                }
            }

        }
    }

    fun scoreTeamOne() {
        addEvent(Score(1, half, pastSeconds));
        team1.gols++;
    }

    fun scoreTeamTwo() {
        addEvent(Score(2, half, pastSeconds))
        team2.gols++;
    }

    fun cardTeamOne(color: CardColor) {
        addEvent(Card(1, color, half, pastSeconds))
        when (color) {
            CardColor.YELLOW -> team1.yellowCards++
            CardColor.RED -> team1.redCards++
            CardColor.BLUE -> team1.blueCards++
        }
    }

    fun cardTeamTwo(color: CardColor) {

        addEvent(Card(2, color, half, pastSeconds))
        when (color) {
            CardColor.YELLOW -> team2.yellowCards++
            CardColor.RED -> team2.redCards++
            CardColor.BLUE -> team2.blueCards++
        }
    }

    abstract fun getNome(): String;
    override fun toString(): String {
        return this.javaClass.name + " " + getNome() + "Placar:" + team1.toString() + " " + team2.toString();
    }
}