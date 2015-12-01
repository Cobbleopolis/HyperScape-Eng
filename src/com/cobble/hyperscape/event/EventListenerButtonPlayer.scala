package com.cobble.hyperscape.event

import com.cobble.hyperscape.core.HyperScape
import org.lwjgl.input.Keyboard

class EventListenerButtonPlayer extends EventListenerButton {

    val rotate: Float = 5

    override def onTick(): Unit = {
        val player = HyperScape.currentGameState.world.player
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            player.moveEntity(0, 0, player.speed)

        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            player.moveEntity(0, 0, -player.speed)

        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            player.moveEntity(-player.speed, 0, 0)

        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            player.moveEntity(player.speed, 0, 0)

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            player.moveEntity(0, player.speed, 0)

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            player.moveEntity(0, -player.speed, 0)



        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
            player.rotateEntity(-Math.toRadians(rotate).toFloat, 0.0f, 0.0f)
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            player.rotateEntity(Math.toRadians(rotate).toFloat, 0.0f, 0.0f)
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
            player.rotateEntity(0.0f, -Math.toRadians(rotate).toFloat, 0.0f)
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            player.rotateEntity(0.0f, Math.toRadians(rotate).toFloat, 0.0f)

        player.rotation.setX(Math.min(player.maxLookDown, Math.max(player.maxLookUp, player.rotation.getX)))

        if (Keyboard.isKeyDown(Keyboard.KEY_R))
            println(player.position)

        if (Keyboard.isKeyDown(Keyboard.KEY_E))
            println(player.rotation)
    }
}
