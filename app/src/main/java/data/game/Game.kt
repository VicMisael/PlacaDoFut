package data.game

import data.game.events.EventType
import data.game.events.Score
import java.io.Serializable

abstract class Game(
    var nome_partida: String,
    var nome_equipe1: String,
    var nome_equipe2: String,
    var has_timer: Boolean,
    var seconds: Int,
    var halfs: Int,
    var scoresTeamOne: Int,
    var scoreTeamTwo: Int,
    var events: MutableList<EventType> = mutableListOf()
) : Serializable {
    fun addEvent(event: EventType) {
        events.add(event);
    }

    fun scoreTeamOne(){
        addEvent(Score(nome_equipe1));
        scoresTeamOne++;
    }

    fun scoreTeamTwo(){
        addEvent(Score(nome_equipe2))
        scoreTeamTwo++;
    }
}