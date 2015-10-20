package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.{TextureRegistry, ShaderRegistry}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.{GL11, GL20, GL30, GL15}
import org.lwjgl.util.vector.{Matrix4f, Vector2f}

class DropShadowModel(x: Float, y: Float, width: Float, height: Float, zIndex: Float) {

    val z = -1f

    var verts: Array[Float] = Array[Float]()

    val modelMatrix = new Matrix4f()

    val PIX: Float = 0.1171875f
    val localVerts: Array[Array[Float]] = Array(
        Array(30    , 30     , z , PIX, 1 - PIX, zIndex), //Center Bottom Left
        Array(30    , height - 30, z , PIX, PIX, zIndex), //Center Top Left
        Array(width - 30, height - 30, z , 1 - PIX, PIX, zIndex), //Center Top Right
        Array(width - 30, 30     , z , 1 - PIX, 1 - PIX, zIndex),  //Center Bottom Right

        Array(0    , height - 30     , z , 0f, PIX, zIndex), //Left Top Bottom Left
        Array(0    , height, z , 0f, 0f, zIndex), //Left Top Top Left
        Array(30, height, z , PIX, 0f, zIndex), //Left Top Top Right
        Array(30, height - 30     , z , PIX, PIX, zIndex),  //Left Top Bottom Right

        Array(30    , height - 30     , z , PIX, 1 - PIX, zIndex), //Top Center Bottom Left
        Array(30    , height, z , PIX, PIX, zIndex), //Top Center Top Left
        Array(width - 30, height - 30, z , 1 - PIX, PIX, zIndex), //Top Center Top Right
        Array(width - 30, 30     , z , 1 - PIX, 1 - PIX, zIndex)  //Top Center Bottom Right

    )

    val order: Array[Int] = Array(
        0, 2, 1, 0, 3, 2,
        4, 6, 5, 4, 7, 6)

    order.foreach(index => verts = verts ++ localVerts(index))

    verts.grouped(Vertex.DROP_SHADOW_COUNT).foreach(o => println(o.mkString(", ")))

    val vbo = GL15.glGenBuffers()
    val vao = GL30.glGenVertexArrays()

    modelMatrix.translate(new Vector2f(x, y - 30))

    HyperScape.uploadBuffer.clear()
    HyperScape.uploadBuffer.put(verts)
    HyperScape.uploadBuffer.flip()
    GL30.glBindVertexArray(vao)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, HyperScape.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.DROP_SHADOW_SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.DROP_SHADOW_SIZE_IN_BYTES, Vertex.TEXTURE_OFFSET)
    GL20.glVertexAttribPointer(2, Vertex.DROP_SHADOW_Z_INDEX_SIZE, GL11.GL_FLOAT, false, Vertex.DROP_SHADOW_SIZE_IN_BYTES, Vertex.DROP_SHADOW_Z_INDEX_OFFSET)

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
        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)
        GL20.glEnableVertexAttribArray(2)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.DROP_SHADOW_COUNT)
        //        GL11.glDrawArrays(if (drawLines) GL11.GL_LINES else GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)

        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0)
        GL20.glDisableVertexAttribArray(1)
        GL20.glDisableVertexAttribArray(2)
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
