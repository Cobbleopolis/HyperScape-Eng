package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.util.MathUtil
import org.lwjgl.opengl.{Display, GL20}
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class Camera {
    var fov: Float = 70
    var nearClip: Float = 0.1f
    var farClip: Float = 30000
    var perspective: Matrix4f = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    var view = new Matrix4f()
    var pos = new Vector3f()

    private def frustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4f = {
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
        new Matrix4f(dest)
    }

    /**
     * Updates the camera's perspective matrix. Used when the window is resized.
     */
    def updatePerspective(): Unit = {
        perspective = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    }


    private def perspective(fovInDegrees: Float, aspectRatio: Float, near: Float, far: Float): Matrix4f = {
        val top = (near * Math.tan(fovInDegrees * MathUtil.PI360)).toFloat
        val right = top * aspectRatio
        frustum(-right, right, -top, top, near, far)
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
