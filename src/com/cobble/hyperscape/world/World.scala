package com.cobble.hyperscape.world

import java.security.InvalidParameterException

import com.cobble.hyperscape.block.Block
import com.cobble.hyperscape.entity.EntityPlayer
import com.cobble.hyperscape.reference.Reference
import org.lwjgl.util.vector.Vector3f

import scala.collection.mutable

abstract class World {

	var player: EntityPlayer = new EntityPlayer(this)
	player.rotateEntity(0f, 0f, Math.toRadians(180).asInstanceOf[Float])

	var chunks = new mutable.HashMap[Int, Chunk]

	val grav: Float

	var activeChunks: Array[Int] = Array[Int]()


	def setBlock(x: Int, y: Int, z: Int, block: Block): Unit = {
		if (x < 0 || x > 255 || y < 0 || y > 255 || z < 0 || z > 255) {
			println("X, Y, and Z must all be between 0 and 255. Not setting Block | Attempted location: (" + x + ", " + y + ", " + z + ")")
			return
		}
		val index: Int = getChunkIndexFromXZ(x, z)
		if (!chunks.contains(index)) {
			val (chunkX, chunkY) = getChunkXZFromIndex(index)
			chunks.put(index, new Chunk(chunkX, chunkY))
		}
		chunks(getChunkIndexFromXZ(x, z)).setBlock(x & 15, y, z & 15, block)
//		println(getChunkIndexFromXZ(x, z))
	}

	def getBlock(x: Int, y: Int, z: Int): Block = {
		if (x < 0 || x > 255 || y < 0 || y > 255 || z < 0 || z > 255) {
			println("X, Y, and Z must all be between 0 and 255. Returning null | Attempted location: (" + x + ", " + y + ", " + z + ")")
			return null
		}
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
		((x / 16) & 15) << 4 | ((z / 16) & 15)
	}

	/**
	 * Converts a chunk's index into it's x, z location
	 * @param index Index of the chunk
	 * @return The x, z location of the chunk
	 */
	def getChunkXZFromIndex(index: Int): (Int, Int) = {
		((index >> 4) & 15, index & 15)
	}

	/**
	 * Gets the chunk at the coordinate x, z
	 * @param x X world coordinate
	 * @param z Z world coordinate
	 * @return Chunk at x, z null if the chunk at x, z does not exist
	 */
	def getChunk(x: Int, z: Int): Chunk = {
		chunks(getChunkIndexFromXZ(x, z))
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
		chunks.foreach(chunk => chunk._2.destroy())
	}

}
