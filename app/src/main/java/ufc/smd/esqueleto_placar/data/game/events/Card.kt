package ufc.smd.esqueleto_placar.data.game.events

data class Card(val teamCode:Int ,val cardColor: CardColor): EventType(teamCode) {
}