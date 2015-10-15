package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.util.vector.Matrix4f

/**
 * The model useed in rendering GUI's. These models can not be manipulated after initialization
 * @param verts an array of verts used to define the model for a GUI must be in format of (x, y, z, r, g, b, a)
 */
class GuiModel(verts: Array[Float]) {
    println("Creating Gui Model...")
    val modelMatrix = new Matrix4f()

    HyperScape.uploadBuffer.clear()
    HyperScape.uploadBuffer.put(verts)
    HyperScape.uploadBuffer.flip()

    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, HyperScape.uploadBuffer, GL15.GL_STATIC_DRAW)

    GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.COLOR_VERTEX_SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
    GL20.glVertexAttribPointer(1, Vertex.COLOR_SIZE, GL11.GL_FLOAT, false, Vertex.COLOR_VERTEX_SIZE_IN_BYTES, Vertex.COLOR_OFFSET)

    GL30.glBindVertexArray(0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
//    println(verts.grouped(7).map(x => x.take(3).mkString(", ")).mkString("\n"))
    println("Created Gui Model")

    /**
     * Renders the model
     */
    def render(isHilighted: Boolean = false): Unit = {
        // Bind the gui shader
        ShaderRegistry.bindShader("gui")

        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("elementColor")
        if (isHilighted) {
            GL20.glUniform4f(loc, .25f, .25f, .25f, 1f)
        } else {
            GL20.glUniform4f(loc, 0f, 0f, 0f, 1f)
//            HyperScape.uploadBuffer.put(Array(0f, 0f, 0f, 1f))
        }


        // Bind to the VAO that has all the information about the quad vertices
        GL30.glBindVertexArray(vao)
        GL20.glEnableVertexAttribArray(0)
        GL20.glEnableVertexAttribArray(1)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

        // Draw the vertices
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)
//        GL11.glDrawArrays(if (drawLines) GL11.GL_LINES else GL11.GL_TRIANGLES, 0, verts.length / Vertex.GUI_ELEMENT_COUNT)

        // Put everything back to default (deselect)
        GL20.glDisableVertexAttribArray(0)
        GL20.glDisableVertexAttribArray(1)
        GL30.glBindVertexArray(0)
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

    /**
     * Gets a copy of the model
     * @return A copy of the model
     */
    def copy: GuiModel = {
        new GuiModel(verts)
    }
}