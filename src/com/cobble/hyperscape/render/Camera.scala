package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.util.MathUtil
import org.lwjgl.opengl.{Display, GL20}
import org.lwjgl.util.vector.{Matrix4f, Vector3f}

class Camera {
    var mode: Int = Reference.Camera.PERSPECTIVE_MODE
    var fov: Float = 70
    var orthographicFov: Float = 160
    var nearClip: Float = 0.1f
    var farClip: Float = 30000
    var perspective: Matrix4f = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    var view = new Matrix4f()
    var pos = new Vector3f()

    /**
     * Updates the camera's perspective matrix. Used when the window is resized.
     */
    def updatePerspective(): Unit = {
        perspective = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    }

    private def perspective(fovInDegrees: Float, aspectRatio: Float, near: Float, far: Float): Matrix4f = {
        val top = (near * Math.tan(fovInDegrees * MathUtil.PI360)).toFloat
        val right = top * aspectRatio
        if (mode == Reference.Camera.PERSPECTIVE_MODE) {
            frustum(-right, right, -top, top, near, far)
        } else {
            val width = Display.getWidth / VirtualResolution.getScale
            val height = Display.getHeight / VirtualResolution.getScale
            orthographicFrustum(-width / 2, width / 2, -height / 2, height / 2, -2f, 25f)
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
}
