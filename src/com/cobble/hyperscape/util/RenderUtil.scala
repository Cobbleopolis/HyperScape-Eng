package com.cobble.hyperscape.util

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.Matrix4f

object RenderUtil {

    def uploadModelMatrix(modelMatrix: Matrix4f): Unit = {
        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        HyperScape.uploadBuffer.clear()
        modelMatrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        GL20.glUniformMatrix4(loc, false, HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.clear()
    }

}
