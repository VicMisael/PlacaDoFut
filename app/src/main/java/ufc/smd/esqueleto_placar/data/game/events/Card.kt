package ufc.smd.esqueleto_placar.data.game.events

data class Card(val teamCode:Int ,val cardColor: CardColor,val currentHalf:Int,val pastTime:Int): EventType(teamCode,currentHalf,pastTime) {
    override fun getName(): String {
        return "Cartão"
    }
}