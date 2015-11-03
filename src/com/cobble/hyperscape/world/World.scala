package com.cobble.hyperscape.world

import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.ModelRegistry
import com.cobble.hyperscape.render.WorldModel

abstract class World {

    val blocks: Array[Int] = new Array[Int](Reference.World.WORLD_SIZE)

    var isDirty: Boolean = false

    val grav: Float

    val worldModel: WorldModel = new WorldModel
    worldModel.uploadVerts(false)

    def setBlock(x: Byte, y: Byte, z: Byte, blockID: Int): Unit = {
        blocks(x << 16 | y << 8 | z) = blockID
        val model = ModelRegistry.getModel("cube")
        model.translate(x, y, z)
        worldModel.addVerts(model.getVertices)
        isDirty = true
    }

    def getBlock(x: Byte, y: Byte, z: Byte): Int = blocks(x << 16 | y << 8 | z)

    def generateModel(): Unit = {
        worldModel.uploadVerts()
        isDirty = false
    }

    def render(): Unit = {
        if (isDirty) generateModel()
        worldModel.render()
    }

    def destroy(): Unit = {
        worldModel.destroy()
    }

}
