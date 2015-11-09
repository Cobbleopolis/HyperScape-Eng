package com.cobble.hyperscape.world

import java.security.InvalidParameterException

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.entity.EntityPlayer
import org.lwjgl.util.vector.Vector3f

abstract class World {

	var player: EntityPlayer = new EntityPlayer(this)
	player.rotateEntity(0f, 0f, Math.toRadians(180).asInstanceOf[Float])

//	var chunks = new mutable.HashMap[Int, Chunk]
	var chunks = new Array[Chunk](526)
	for(i <- 0 until 526) {
		val (x, z) = getChunkXZFromIndex(i)
		println("Adding chunk: " + "(" + x + ", " + z + ") | " + i)
		chunks(i) = new Chunk(x, z)
	}

	val grav: Float

	var activeChunks: Array[Int] = Array[Int]()


	def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
		chunks(getChunkIndexFromXZ(x, z)).setBlock(x & 15, y, z & 15, block)
//		println(getChunkIndexFromXZ(x, z))
	}

	def getBlock(x: Int, y: Int, z: Int): Block = {
		val chunk = getChunk(x, z)
		if (chunk != null)
			chunks(getChunkIndexFromXZ(x, z)).getBlock(x & 15, y, z & 15)
		else
			null
	}

	/**
	 * Converts a chunk's x, z coordinates into it's index
	 * @param x X world coordinate
	 * @param z Z world coordinate of the chunk
	 * @return The index of the chunk at x, z
	 */
	def getChunkIndexFromXZ(x: Int, z: Int): Int = {
		(x / 16) << 4 | (z / 16)
	}

	/**
	 * Converts a chunk's index into it's x, z location
	 * @param index Index of the chunk
	 * @return The x, z location of the chunk
	 */
	def getChunkXZFromIndex(index: Int): (Int, Int) = {
		(index >> 4, (index & 16).toShort)
	}

	/**
	 * Gets the chunk at the coordinate x, z
	 * @param x X world coordinate
	 * @param z Z world coordinate
	 * @return Chunk at x, z null if the chunk at x, z does not exist
	 */
	def getChunk(x: Int, z: Int): Chunk = {
		if (x > 255 || z > 255) throw new InvalidParameterException("X must be between 0 and 255")
		chunks(getChunkIndexFromXZ(x, z))
	}

	def generateModel(): Unit = {
		activeChunks.filter(index => chunks(index).isDirty).foreach(index => {println("Gen"); chunks(index).generateModel()})
	}

	def getActiveChunks(position: Vector3f, radius: Int): Array[Int] = {
		var chunks = Array[Int]()
		for (x <- -radius to radius) {
			for (z <- -radius to radius) {
				val cappedX = Math.min(Math.max(position.x.toInt + (x * 16), 0), 255)
				val cappedZ = Math.min(Math.max(position.z.toInt + (z * 16), 0), 255)
//				val cappedX: Int = (position.getX + (x * 16)).toInt
//				val cappedZ: Int = (position.getZ + (z * 16)).toInt
				if (cappedX >= 0 && cappedZ >= 0) {
					val index = getChunkIndexFromXZ(cappedX, cappedZ)
//					println(cappedX + ", " + cappedZ + " | " + x + ", " + z)
					chunks = chunks :+ index
				}
			}
		}
//        println("Center: " + position.getX + ", " + position.getY + ", " + position.getZ)
//		println("Active Size: " + chunks.length + " | " + chunks.mkString(", "))
		chunks
	}

	def tick(): Unit = {
		activeChunks = getActiveChunks(player.position, 5)
	}

	def render(): Unit = {
//		println(activeChunks.length)
//		generateModel()
		activeChunks.foreach(index => chunks(index).render())
//		chunks.foreach(chunk => chunk.render())
	}

	def destroy(): Unit = {
		chunks.foreach(chunk => chunk.destroy())
	}

}
