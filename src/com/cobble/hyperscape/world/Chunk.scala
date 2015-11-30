package com.cobble.hyperscape.world

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.{ShaderRegistry, ModelRegistry, BlockRegistry}
import com.cobble.hyperscape.render.ChunkModel
import org.lwjgl.opengl.GL20
import org.lwjgl.util.vector.Vector3f

class Chunk(xCoord: Int, zCoord: Int) {

    val blocks: Array[Int] = new Array[Int](Reference.World.CHUNK_SIZE)

    val chunkModel = new ChunkModel()
    chunkModel.modelMatrix.translate(new Vector3f(xCoord.toFloat * 16f, 0f, zCoord.toFloat * 16f))

    var isDirty: Boolean = false

    def getBlockIndexFromXYZ(x: Int, y: Int, z: Int): Int = {
        y << 8 | x << 4 | z
    }

    def getBlockXYZFromIndex(index: Int): (Int, Int, Int) = {
        ((index >> 4) & 15, (index >> 8) & 255, index & 15)
    }

    def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
        blocks(getBlockIndexFromXYZ(x, y, z)) = block.blockID
        isDirty = true
    }

    def getBlock(x: Int, y: Int, z: Int): Block = {
        BlockRegistry.getBlock(blocks(getBlockIndexFromXYZ(x, y, z)))
    }

    def generateModel(): Unit = {
        var i = 0

        chunkModel.clearVerts()


        blocks.foreach(blockID => {
            if (BlockRegistry.getBlock(blockID) != null) {

                val model = ModelRegistry.getModel("cube")

                val (x: Int, y: Int, z: Int) = getBlockXYZFromIndex(i)
                val (u: Float, v: Float) = BlockRegistry.getBlock(blockID).uv

                println("(" + x + ", " + y + ", " + z + ")")

                model.translate(x, y, z)
                model.translateUV(u * 0.0625f, v * 0.0625f)

                chunkModel.addVerts(model.getVertices)
            }
            i += 1
        })

        chunkModel.uploadVerts()
        isDirty = false
    }

    def render(): Unit = {
        if (isDirty)
            generateModel()

        ShaderRegistry.bindShader("terrain")
        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("chunkColor")
        val (r: Float, g: Float, b: Float) = {
            if ((xCoord + zCoord) % 2 == 0)
                (1f, 0f, .3f)
            else
                (0f, 1f, .3f)
        }
        GL20.glUniform4f(loc, r, g, b, 1f)

        chunkModel.render()
    }

    def destroy(): Unit = {
        chunkModel.destroy()
    }
}
