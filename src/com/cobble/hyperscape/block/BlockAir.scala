package com.cobble.hyperscape.block

import com.cobble.hyperscape.reference.{RenderTypes, BlockID}

class BlockAir extends Block {
	blockID = BlockID.AIR
	renderType = RenderTypes.DOES_NOT_RENDER
	hasCollision = false
}
