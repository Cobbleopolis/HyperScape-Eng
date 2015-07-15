package com.cobble.hyperscape.core

import com.cobble.hyperscape.render.Camera
import org.lwjgl.BufferUtils

class HyperScape {
    
}

object HyperScape {
    /** The buffer used to upload to the GPU. Max is 64000000 floats */
    val uploadBuffer = BufferUtils.createFloatBuffer(64000000)

    var shaderSelector = 0

    var lines: Boolean = false

    /** The Camera that renders they game */
    val mainCamera = new Camera

    var debug = false
}
