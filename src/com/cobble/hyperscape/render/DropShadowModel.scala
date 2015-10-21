package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.{GL11, GL20, GL30, GL15}
import org.lwjgl.util.vector.{Vector3f, Matrix4f, Vector2f}

class DropShadowModel(x: Float, y: Float, width: Float, height: Float, zIndex: Float) {

    val z = -1f

    var verts: Array[Float] = Array[Float]()

    val modelMatrix = new Matrix4f()

    val PIX: Float = 0.1171875f
    val fadeSize: Float = 10f

    val localVerts: Array[Array[Float]] = Array(
        Array(fadeSize         , fadeSize          , z , PIX      , 1f - PIX ), //Center Bottom Left
        Array(fadeSize         , height - fadeSize , z , PIX      , PIX      ), //Center Top Left
        Array(width - fadeSize , height - fadeSize , z , 1f - PIX , PIX      ), //Center Top Right
        Array(width - fadeSize , fadeSize          , z , 1f - PIX , 1f - PIX ),  //Center Bottom Right

        Array(0f       , height - fadeSize , z , 0f  , PIX ), //Left Top Bottom Left
        Array(0f       , height            , z , 0f  , 0f  ), //Left Top Top Left
        Array(fadeSize , height            , z , PIX , 0f  ), //Left Top Top Right
        Array(fadeSize , height - fadeSize , z , PIX , PIX ),  //Left Top Bottom Right

        Array(fadeSize         , height - fadeSize , z , PIX      , PIX ), //Top Center Bottom Left
        Array(fadeSize         , height            , z , PIX      , 0f  ), //Top Center Top Left
        Array(width - fadeSize , height            , z , 1f - PIX , 0f  ), //Top Center Top Right
        Array(width - fadeSize , height - fadeSize , z , 1f - PIX , PIX ),  //Top Center Bottom Right

        Array(width - fadeSize , height - fadeSize , z , 1f - PIX , PIX ), //Right Top Bottom Left
        Array(fadeSize         , height            , z , PIX      , 0f  ), //Right Top Top Left
        Array(width            , height            , z , 1f       , 0f  ), //Right Top Top Right
        Array(width            , height - fadeSize , z , 1f       , PIX ),  //Right Top Bottom Right

        Array(width - fadeSize , fadeSize          , z , 1f - PIX , 1f - PIX ), //Right Center Bottom Left
        Array(width - fadeSize , height - fadeSize , z , 1f - PIX , PIX      ), //Right Center Top Left
        Array(width            , height            , z , 1f       , 0f       ), //Right Center Top Right
        Array(width            , fadeSize          , z , 1f       , 1f - PIX ),  //Right Center Bottom Right

        Array(width - fadeSize , 0f       , z , 1f - PIX , 1f       ), //Right Bottom Bottom Left
        Array(width - fadeSize , fadeSize , z , 1f - PIX , 1f - PIX ), //Right Bottom Top Left
        Array(width            , fadeSize , z , 1f       , 1f - PIX ), //Right Bottom Top Right
        Array(width            , 0f       , z , 1f       , 1f       ), //Right Bottom Bottom Right

        Array(fadeSize         , 0f       , z , 1f - PIX , 1f       ), //Center Bottom Bottom Left
        Array(fadeSize         , fadeSize , z , PIX      , 1f - PIX ), //Center Bottom Top Left
        Array(width - fadeSize , fadeSize , z , 1f - PIX , 1f - PIX ), //Center Bottom Top Right
        Array(width - fadeSize , 0f       , z , 1f - PIX , 1f       ),  //Center Bottom Bottom Right

        Array(0f       , 0f       , z , 0f  , 1f       ), //Left Bottom Bottom Left
        Array(0f       , fadeSize , z , 0f  , 1f - PIX ), //Left Bottom Top Left
        Array(fadeSize , fadeSize , z , PIX , 1f - PIX ), //Left Bottom Top Right
        Array(fadeSize , 0f       , z , PIX , 1f       ), //Left Bottom Bottom Right

        Array(0f       , fadeSize          , z , 0f  , 1f - PIX ), //Right Center Bottom Left
        Array(0f       , height - fadeSize , z , 0f  , PIX      ), //Right Center Top Left
        Array(fadeSize , height - fadeSize , z , PIX , PIX      ), //Right Center Top Right
        Array(fadeSize , fadeSize          , z , PIX , PIX      ) //Right Center Bottom Right

    )

    val order: Array[Int] = Array(
        0, 2, 1, 0, 3, 2,
        4, 6, 5, 4, 7, 6,
        8, 10, 9, 8, 11, 10,
        12, 14, 13, 12, 15, 14,
        16, 18, 17, 16, 19, 18,
        20, 22, 21, 20, 23, 22,
        24, 26, 25, 24, 27, 26,
        28, 30, 29, 28, 31, 30,
        32, 34, 33, 32, 35, 34
    )

    order.foreach(index => verts = verts ++ localVerts(index))

    verts.grouped(Vertex.DROP_SHADOW_COUNT).foreach(o => println(o.mkString(", ")))

    val vbo = GL15.glGenBuffers()
    val vao = GL30.glGenVertexArrays()

    modelMatrix.translate(new Vector2f(x, y - Math.max(zIndex, fadeSize)))//.scale(new Vector3f(zIndex))

    HyperScape.uploadBuffer.clear()
    HyperScape.uploadBuffer.put(verts)
    HyperScape.uploadBuffer.flip()
    GL30.glBindVertexArray(vao)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, HyperScape.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.DROP_SHADOW_SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.DROP_SHADOW_SIZE_IN_BYTES, Vertex.TEXTURE_OFFSET)
//    GL20.glVertexAttribPointer(2, Vertex.DROP_SHADOW_Z_INDEX_SIZE, GL11.GL_FLOAT, false, Vertex.DROP_SHADOW_SIZE_IN_BYTES, Vertex.DROP_SHADOW_Z_INDEX_OFFSET)

    GL30.glBindVertexArray(0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)

    /**
     * Renders the model
     */
    def render(drawLines: Boolean = false): Unit = {
        // Bind the font shader
        ShaderRegistry.bindShader("dropShadow")
        HyperScape.mainCamera.uploadPerspective()
        HyperScape.mainCamera.uploadView()
        GLUtil.uploadModelMatrix(modelMatrix)
        TextureRegistry.bindTexture("dropShadow")
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_BLEND)

        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("zIndex")
        GL20.glUniform1f(loc, zIndex)

//        GL11.glDisable(GL11.GL_CULL_FACE)
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)
//        GL20.glEnableVertexAttribArray(2)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.DROP_SHADOW_COUNT)
        //        GL11.glDrawArrays(if (drawLines) GL11.GL_LINES else GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)

        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0)
        GL20.glDisableVertexAttribArray(1)
//        GL20.glDisableVertexAttribArray(2)
        GL30.glBindVertexArray(0)
        GL11.glDisable(GL11.GL_BLEND)
        GLUtil.checkGLError()
    }

    /**
     * Destroys the model
     */
    def destroy(): Unit = {
        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0)

        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        GL15.glDeleteBuffers(vbo)

        // Delete the VAO
        GL30.glBindVertexArray(0)
        GL30.glDeleteVertexArrays(vao)
    }
}
