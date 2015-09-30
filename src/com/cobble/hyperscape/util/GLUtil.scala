package com.cobble.hyperscape.util

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.Matrix4f

object GLUtil {

    def uploadModelMatrix(matrix: Matrix4f): Unit = {
        HyperScape.uploadBuffer.clear()
        matrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
    }

}
