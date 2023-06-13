package ufc.smd.esqueleto_placar.data.game

class CustomGame(
    nome_partida: String,
    nome_equipe1: String,
    nome_equipe2: String,
    seconds: Int,
    halfs: Int
) : Game(
    nome_partida,
    nome_equipe1,
    nome_equipe2,
    seconds,
    halfs,
) {
    companion object {
        @JvmStatic private val serialVersionUID: Long = 6529685098267757690L
    }
    override fun getNome():String {
        return nome_partida;
    }
}