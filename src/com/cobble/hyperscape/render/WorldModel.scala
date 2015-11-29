package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.{ShaderRegistry, TextureRegistry}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

class WorldModel {

	val modelMatrix: Matrix4f = new Matrix4f

	val shader: String = "terrain"

	var verts: Array[Float] = Array[Float]()

	var vao: Int = -1

	var vbo: Int = -1

//	modelMatrix.rotate(Math.toRadians(180).asInstanceOf[Float], new Vector3f(0, 1, 0))
//	modelMatrix.translate(new Vector3f(0, -3, 0))

	def addVerts(newVerts: Array[Float]): Unit = {
		verts = verts ++ newVerts
	}

	def clearModel(): Unit = {
//		destroy()
		verts = Array[Float]()
//		vao = -1
//		vbo = -1
	}

	def uploadVerts(destroyCurrentModel: Boolean = true): Unit = {
		println("Uploading...")
        destroy()
        HyperScape.uploadBuffer.clear()
        HyperScape.uploadBuffer.put(verts)
        HyperScape.uploadBuffer.flip()

        vao = GL30.glGenVertexArrays()
        GL30.glBindVertexArray(vao)

        vbo = GL15.glGenBuffers()
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, HyperScape.uploadBuffer, GL15.GL_DYNAMIC_DRAW)

        GL20.glVertexAttribPointer(0, Vertex.VERTEX_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.VERTEX_OFFSET)
        GL20.glVertexAttribPointer(1, Vertex.UV_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.UV_OFFSET_IN_BYTES)
        GL20.glVertexAttribPointer(2, Vertex.NORMAL_SIZE, GL11.GL_FLOAT, false, Vertex.SIZE_IN_BYTES, Vertex.NORMAL_OFFSET_IN_BYTES)

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
        GL30.glBindVertexArray(0)
		println("Uploaded")
	}

	def render(): Unit = {
		if (!verts.isEmpty && vao != -1 && vbo != -1) {
			ShaderRegistry.bindShader(shader)
            HyperScape.mainCamera.uploadPerspective()
            HyperScape.mainCamera.uploadView()
            GLUtil.uploadModelMatrix(modelMatrix)
            TextureRegistry.bindTexture("terrain")

            GL30.glBindVertexArray(vao)
            ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glEnableVertexAttribArray(input._1))
//            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verts.length / Vertex.VERTEX_SIZE)

            ShaderRegistry.getCurrentShader.inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))
            GL30.glBindVertexArray(0)
		}
	}

	/**
	 * Destroys the model
	 */
	def destroy(): Unit = {
		if (vbo != -1 && vao != -1) {
			GL30.glBindVertexArray(vao)
			// Disable the VBO index from the VAO attributes list
			ShaderRegistry.getShader(shader).inputs.foreach(input => GL20.glDisableVertexAttribArray(input._1))

			// Delete the VBO
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
			GL15.glDeleteBuffers(vbo)

			// Delete the VAO
			GL30.glBindVertexArray(0)
			GL30.glDeleteVertexArrays(vao)
		}
	}

}
