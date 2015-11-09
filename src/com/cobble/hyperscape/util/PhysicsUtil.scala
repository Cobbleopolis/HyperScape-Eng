package com.cobble.hyperscape.util

import com.cobble.hyperscape.physics.AxisAlignedBB
import org.lwjgl.util.vector.Vector3f

object PhysicsUtil {

	/**
	 * Detects if two bounding boxes are colliding
	 * @param boundingBoxA The first bounding box
	 * @param boundingBoxB The second bounding box
	 * @return The vector from the center of boundingBoxA pointing at the center of boundingBoxB if the two bounding boxes are colliding, null otherwise.
	 */
	def areBoundingBoxesColliding(boundingBoxA: AxisAlignedBB, boundingBoxB: AxisAlignedBB): Vector3f = {
		//        if (boundingBoxA.getXMax > boundingBoxB.getXMin && boundingBoxB.getXMax > boundingBoxA.getXMin && boundingBoxA.getYMax > boundingBoxB.getYMin && boundingBoxB.getYMax > boundingBoxA.getYMin && boundingBoxA.getZMax > boundingBoxB.getZMin && boundingBoxB.getZMax > boundingBoxA.getZMin) {
		if (boundingBoxA.intersects(boundingBoxB)) {
			val (centerAX, centerAY, centerAZ) = boundingBoxA.getCenter
			val (centerBX, centerBY, centerBZ) = boundingBoxB.getCenter
			new Vector3f(centerAX - centerBX, centerAY - centerBY, centerAZ - centerBZ)
		} else {
			null
		}
	}

	/**
	 * Detects if two bounding boxes are touching or colliding
	 * @param boundingBoxA The first bounding box
	 * @param boundingBoxB The second bounding box
	 * @return The vector from the center of boundingBoxA pointing at the center of boundingBoxB if the two bounding boxes are touching or colliding, null otherwise.
	 */
	def areBoundingBoxesTouching(boundingBoxA: AxisAlignedBB, boundingBoxB: AxisAlignedBB): Vector3f = {
		//        if (boundingBoxA.getXMax >= boundingBoxB.getXMin && boundingBoxB.getXMax >= boundingBoxA.getXMin && boundingBoxA.getYMax >= boundingBoxB.getYMin && boundingBoxB.getYMax >= boundingBoxA.getYMin && boundingBoxA.getZMax >= boundingBoxB.getZMin && boundingBoxB.getZMax >= boundingBoxA.getZMin) {
		if (boundingBoxA.isTouching(boundingBoxB)) {
			val (centerAX, centerAY, centerAZ) = boundingBoxA.getCenter
			val (centerBX, centerBY, centerBZ) = boundingBoxB.getCenter
			new Vector3f(centerAX - centerBX, centerAY - centerBY, centerAZ - centerBZ)
		} else {
			null
		}
	}

	/**
	 * Finds the largest component from a vector
	 * @param vec The vector to parse
	 * @return The largest component from a vector
	 */
	def largestVectorComponent(vec: Vector3f): Float = {
		var largest: Float = 0
		if (Math.abs(vec.getX) > Math.abs(largest)) {
			largest = vec.getX
		}
		if (Math.abs(vec.getY) > Math.abs(largest)) {
			largest = vec.getY
		}
		if (Math.abs(vec.getZ) > Math.abs(largest)) {
			largest = vec.getZ
		}
		largest
	}

	/**
	 * Finds the smallest component from a vector
	 * @param vec The vector to parse
	 * @return The smallest component from a vector
	 */
	def smallestVectorComponent(vec: Vector3f): Float = {
		var smallest: Float = 0
		if (Math.abs(vec.getX) < Math.abs(smallest)) {
			smallest = vec.getX
		}
		if (Math.abs(vec.getY) < Math.abs(smallest)) {
			smallest = vec.getY
		}
		if (Math.abs(vec.getZ) < Math.abs(smallest)) {
			smallest = vec.getZ
		}
		smallest
	}
}
