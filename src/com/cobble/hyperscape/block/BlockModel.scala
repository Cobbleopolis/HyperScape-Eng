package com.cobble.hyperscape.block

import com.cobble.hyperscape.reference.{RenderTypes, BlockID}
import com.cobble.hyperscape.registry.ModelRegistry

class BlockModel extends Block {
	blockID = BlockID.MODEL
	gameModel = ModelRegistry.getModel("model")
	texCoord = (2, 0)
	renderType = RenderTypes.NON_FULL_BLOCK
}
