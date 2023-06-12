package ufc.smd.esqueleto_placar.data.game.events

import java.io.Serializable

open class EventType(val teamNumber:Int,val half:Int,val second:Int): Serializable
{
}