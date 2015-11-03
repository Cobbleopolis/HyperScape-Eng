package com.cobble.hyperscape.entity

import org.lwjgl.util.vector.Vector3f

abstract class Entity {

    val position: Vector3f = new Vector3f()

    val rotation: Vector3f = new Vector3f()

}
