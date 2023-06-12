package ufc.smd.esqueleto_placar.data.game

class CustomGame(
    val nome_modo: String,
    nome_partida: String,
    nome_equipe1: String,
    nome_equipe2: String,
    has_timer: Boolean,
    seconds: Int,
    halfs: Int
) : Game(
    nome_partida,
    nome_equipe1,
    nome_equipe2,
    has_timer,
    seconds,
    halfs,
) {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 6529685098267757690L
    }
    override fun getNome():String {
        return nome_modo;
    }
}