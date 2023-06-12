package ufc.smd.esqueleto_placar.data.game

class Normal(nome_partida: String,nome_equipe1:String,
             nome_equipe2: String, has_timer: Boolean ) :
    Game(nome_partida,nome_equipe1,nome_equipe2, has_timer,
    seconds=45*60, halfs=2) {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 6529685098267757690L
    }
    override fun getNome():String {
        return "Normal"
    }
}