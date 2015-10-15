package com.cobble.hyperscape.util

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.{GL11, GL20}
import org.lwjgl.util.vector.Matrix4f

object GLUtil {

    def uploadModelMatrix(matrix: Matrix4f): Unit = {
        HyperScape.uploadBuffer.clear()
        matrix.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        val modelMatrixLoc = ShaderRegistry.getCurrentShader.getUniformLocation("modelMatrix")
        GL20.glUniformMatrix4(modelMatrixLoc, false, HyperScape.uploadBuffer)
    }

    def getFontWidth(text: String, scale: Int): Float = text.length.asInstanceOf[Float] * scale.asInstanceOf[Float] * Reference.Font.FONT_WIDTH

    def getFontHeight(scale: Int): Float = scale.asInstanceOf[Float] * Reference.Font.FONT_HEIGHT

    def getFontSize(text: String, scale: Int): (Float, Float) = (getFontWidth(text, scale), getFontHeight(scale))

    def checkGLError(): Unit = {
        val err = GL11.glGetError()
        if (err != 0) {
            println("Error in shader " + ShaderRegistry.getCurrentShader + " | " + err)
            System.exit(1)
        }
    }

}
