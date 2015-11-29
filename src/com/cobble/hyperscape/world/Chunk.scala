package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{ShaderRegistry, ModelRegistry, BlockRegistry}
import com.cobble.hyperscape.render.WorldModel
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector2f, Vector3f, Matrix4f}

class Chunk(xCoord: Int, zCoord: Int) {

	val worldModel: WorldModel = new WorldModel
	worldModel.modelMatrix.translate(new Vector3f(xCoord * 16, 0f,  zCoord * 16))

	val blocks: Array[Int] = new Array[Int](Reference.World.CHUNK_SIZE)

	var isDirty: Boolean = false

	var isEmpty: Boolean = true

	def getXYZFromIndex(index: Int): (Int, Int, Int) = {
		((index >> 4) & 15, (index >> 8) & 255, index & 15)
	}

	def getIndexFromXYZ(x: Int, y: Int, z: Int): Int = {
		(y & 255) << 8 | (x & 15) << 4 | (z & 15)
	}

	def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
		blocks(getIndexFromXYZ(x, y, z)) = block.blockID
//		val model = ModelRegistry.getModel("cube")
//		model.translate(x, y, z)
//		model.translateUV(block.uv._1 * (1f / 16f), block.uv._2 * (1f / 16f))
//		worldModel.addVerts(model.getVertices)
		isDirty = true
		isEmpty = false
	}

	def getBlock(x: Int, y: Int, z: Int): Block = BlockRegistry.getBlock(blocks(getIndexFromXYZ(x, y, z)))

	def generateModel(destroyOldModel: Boolean = true): Unit = {
        worldModel.destroy()
        worldModel.clearModel()
//		GLUtil.checkGLError("(" + zCoord + ", " + zCoord + ")")
		val model = ModelRegistry.getModel("cube")
		var i = 0
		blocks.foreach(blockID => {
			if (blockID != 0) {
				val localModel = model.copy
				val (x, y, z) = getXYZFromIndex(i)
				localModel.translate(x, y, z)
				val (u, v) = BlockRegistry.getBlock(blockID).uv
				localModel.translateUV(u * (1f / 16f), v * (1f / 16f))
				worldModel.addVerts(localModel.getVertices)
			}
			i += 1
		})
		isDirty = false
		worldModel.uploadVerts(isEmpty)
//		GLUtil.checkGLError("(" + zCoord + ", " + zCoord + ")_ | " + worldModel.vao + " | " + worldModel.vbo)
	}

	def render(): Unit = {
		if (!isEmpty) {
			if (isDirty)
				generateModel(true)
            ShaderRegistry.bindShader(worldModel.shader)
            val (r: Float, g: Float, b: Float) =
                if (xCoord % 2 == 0 && zCoord % 2 == 0)
                    (1f, 0f, 0f)
                else
                    (0f, 1f, 0f)
            val loc = ShaderRegistry.getCurrentShader.getUniformLocation("chunkColor")
            GL20.glUniform4f(loc, r, g, b, 1f)
			worldModel.render()
		}
	}

	def destroy(): Unit = {worldModel.destroy()}

}
