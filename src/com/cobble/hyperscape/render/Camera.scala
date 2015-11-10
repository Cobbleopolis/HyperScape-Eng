package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.reference.CameraRef.CameraMode
import com.cobble.hyperscape.reference.CameraRef.CameraMode.CameraMode
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.util.MathUtil
import org.lwjgl.opengl.{Display, GL20}
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

class Camera {
    var mode: CameraMode = CameraMode.PERSPECTIVE_MODE
    var fov: Float = 70
    var orthographicFov: Float = 160
    var nearClip: Float = 0.1f
    var farClip: Float = 30000
    var perspective: Matrix4f = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    var view = new Matrix4f()
    var pos = new Vector3f()

    private var edges: (Float, Float, Float, Float) = (0f, 0f, 0f, 0f)
    private var dimension: (Float, Float) = (0f, 0f)

    /**
     * Updates the camera's perspective matrix. Used when the window is resized.
     */
    def updatePerspective(): Unit = {
        perspective = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    }

    private def perspective(fovInDegrees: Float, aspectRatio: Float, near: Float, far: Float): Matrix4f = {
        val top = (near * Math.tan(fovInDegrees * MathUtil.PI360)).toFloat
        val right = top * aspectRatio
        if (mode == CameraMode.PERSPECTIVE_MODE) {
            edges = (-right, right, -top, top)
            dimension = (right * 2f, top * 2f)
            frustum(-right, right, -top, top, near, far)
        } else {
            val width: Float = Display.getWidth / VirtualResolution.getScale
            val height: Float = Display.getHeight / VirtualResolution.getScale
            edges = (0f, width, 0f, height)
            dimension = (width, height)
            orthographicFrustum(0f, width, 0f, height, -2f, 25f)
        }
    }

    def frustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4f = {
        val width = right - left
        val height = top - bottom
        val length = far - near
        val dest = new Matrix4f()
        dest.m00 = (near * 2) / width
        dest.m02 = (left + right) / width
        dest.m11 = (near * 2) / height
        dest.m12 = (top + bottom) / height
        dest.m22 = -(far + near) / length
        dest.m32 = -(far * near * 2) / length
        dest.m23 = -1
        dest
    }

    def orthographicFrustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4f = {
        val w = right - left
        val h = top - bottom
        val l = far - near

        val dest = new Matrix4f()
        dest.m00 = 2 / w
        dest.m11 = 2 / h
        dest.m22 = -2 / l
        dest.m33 = 1
        dest.m30 = -(right + left) / w
        dest.m31 = -(top + bottom) / h
        dest.m32 = -(far + near) / l
        dest
    }

    /**
     * Uploads the perspective matrix to the GPU
     */
    def uploadPerspective(): Unit = {
        HyperScape.uploadBuffer.clear()
        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("projectionMatrix")
        perspective.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        GL20.glUniformMatrix4(loc, false, HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.clear()
    }

    /**
     * Uploads the view matrix to the GPU
     */
    def uploadView(): Unit = {
        HyperScape.uploadBuffer.clear()
        val loc = ShaderRegistry.getCurrentShader.getUniformLocation("viewMatrix")
        view.store(HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.flip()
        GL20.glUniformMatrix4(loc, false, HyperScape.uploadBuffer)
        HyperScape.uploadBuffer.clear()
    }

    /**
     * Gets the current location of the left edge of the camera
     * @return The current location of the left edge of the camera
     */
    def getLeftEdge: Float = edges._1

    /**
     * Gets the current location of the right edge of the camera
     * @return The current location of the right edge of the camera
     */
    def getRightEdge: Float = edges._2

    /**
     * Gets the current location of the bottom edge of the camera
     * @return The current location of the bottom edge of the camera
     */
    def getBottomEdge: Float = edges._3

    /**
     * Gets the current location of the top edge of the camera
     * @return The current location of the top edge of the camera
     */
    def getTopEdge: Float = edges._4

    /**
     * Gets the current locations of all the edges of the camera.
     * Ordered: Left, Right, Bottom, Top
     * @return The current locations of all the edges of the camera.
     */
    def getAllEdges: (Float, Float, Float, Float) = edges


    /**
     * Gets the camera's current width
     * @return The camera's current width
     */
    def getCameraWidth: Float = dimension._1

    /**
     * Gets the camera's current height
     * @return The camera's current height
     */
    def getCameraHeight: Float = dimension._2

    /**
     * Gets the current dimensions of the camera.
     * Ordered: Width, Height
     * @return The current dimensions of the camera
     */
    def getCameraDimension: (Float, Float) = dimension
}