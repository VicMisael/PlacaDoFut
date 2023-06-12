package ufc.smd.esqueleto_placar.data.game.events

 data class Score(val team:Int,val currentHalf:Int,val pastTime:Int): EventType(team,currentHalf,pastTime) {
}