package com.cobble.hyperscape.entity

import org.lwjgl.util.vector.Vector3f

abstract class Entity {

    val position: Vector3f = new Vector3f()

    val rotation: Vector3f = new Vector3f()

    var maxLookDown: Float = Math.toRadians(90).toFloat

    var maxLookUp: Float = Math.toRadians(-90).toFloat

    val speed: Float = 0.5f

    def moveEntityAlongAxis(x: Float, y: Float, z: Float): Unit = {
        position.translate(x, y, z)
    }

    def moveEntityAlongAxis(translationVector: Vector3f): Unit = {
        moveEntityAlongAxis(translationVector.getX, translationVector.getY, translationVector.getZ)
    }

    def moveEntity(x: Float, y: Float, z: Float): Unit = {
        val direction = new Vector3f()
        val yaw = rotation.getY
        val sin = Math.sin(yaw).toFloat
        val cos = Math.cos(yaw).toFloat
        direction.translate(x * sin, y, x * cos)
        direction.translate(z * -cos, 0, z * sin)
        moveEntityAlongAxis(direction)
    }

    def rotateEntity(pitch: Float, yaw: Float, roll: Float): Unit = {
        rotation.translate(pitch, yaw, roll)
    }

    def rotateEntity(rotationVector: Vector3f): Unit = {
        rotateEntity(rotationVector.getX, rotationVector.getY, rotationVector.getZ)
    }

}
