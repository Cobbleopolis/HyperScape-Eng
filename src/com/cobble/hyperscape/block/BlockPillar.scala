package com.cobble.hyperscape.block

import com.cobble.hyperscape.physics.AxisAlignedBB
import com.cobble.hyperscape.reference.{RenderTypes, BlockID}
import com.cobble.hyperscape.registry.ModelRegistry

class BlockPillar extends Block {
	blockID = BlockID.PILLAR
	renderType = RenderTypes.NON_FULL_BLOCK
	gameModel = ModelRegistry.getModel("pillar")
	boundingBox = new AxisAlignedBB(0, .5f, 0, 1, 0, .5f)
	texCoord = (4, 0)
}
