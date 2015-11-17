package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{ShaderRegistry, ModelRegistry, BlockRegistry}
import com.cobble.hyperscape.render.WorldModel
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.{Vector2f, Vector3f, Matrix4f}

class Chunk(xCoord: Int, zCoord: Int) {

	val worldModel: WorldModel = new WorldModel
	worldModel.modelMatrix.translate(new Vector3f(xCoord * 16, 0f,  zCoord * 16))

	val blocks: Array[Int] = new Array[Int](Reference.World.CHUNK_SIZE)

	var isDirty: Boolean = false

	var isEmpty: Boolean = true

	def getXYZFromIndex(index: Int): (Int, Int, Int) = {
		((index >> 5) & 31, (index >> 10) & 255, index & 31)
	}

	def getIndexFromXYZ(x: Int, y: Int, z: Int): Int = {
		(y & 255) << 10 | (x & 31) << 5 | (z & 31)
	}

	def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
		blocks(getIndexFromXYZ(x, y, z)) = block.blockID
//		val model = ModelRegistry.getModel("cube")
//		model.translate(x, y, z)
//		model.translateUV(block.uv._1 * (1f / 16f), block.uv._2 * (1f / 16f))
//		worldModel.addVerts(model.getVertices)
		isDirty = true
		if (isEmpty) {isEmpty = false; generateModel(false)}
	}

	def getBlock(x: Int, y: Int, z: Int): Block = BlockRegistry.getBlock(blocks(getIndexFromXYZ(x, y, z)))

	def generateModel(destroyOldModel: Boolean = true): Unit = {
		worldModel.clearVerts()
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
		worldModel.uploadVerts(destroyOldModel)
		isDirty = false
	}

	def render(): Unit = {
//		if (!isEmpty) {
			if (isDirty)
				generateModel()
			worldModel.render(xCoord, zCoord)
//		}
	}

	def destroy(): Unit = {worldModel.destroy()}

}
