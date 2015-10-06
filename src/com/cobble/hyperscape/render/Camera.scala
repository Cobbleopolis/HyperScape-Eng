package com.cobble.hyperscape.render

import com.cobble.hyperscape.core.HyperScape
import com.cobble.hyperscape.reference.Reference
import com.cobble.hyperscape.registry.ShaderRegistry
import com.cobble.hyperscape.util.MathUtil
import org.lwjgl.opengl.{Display, GL20}
import org.lwjgl.util.vector.{Vector3f, Matrix4f}

class Camera {
    var mode: Int = Reference.Camera.PERSPECTIVE_MODE
    var fov: Float = 70
    var orthographicFov: Float = 160
    var nearClip: Float = 0.1f
    var farClip: Float = 30000
    var perspective: Matrix4f = perspective(fov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
    var view = new Matrix4f()
    var pos = new Vector3f()

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
        new Matrix4f(dest)
    }

    def orthographicFrustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix4f = {
        val width = Display.getWidth.asInstanceOf[Float]
        val height = Display.getHeight.asInstanceOf[Float]
        val length = far - near
        val dest = new Matrix4f()
//        println(width + " | " + height + " | " + length)
//        dest.m00 = 2 / width
//        dest.m03 = -((right + left) / width)
//        dest.m11 = 2 / height
//        dest.m13 = -((top + bottom) / height)
//        dest.m22 = -2 / length
//        dest.m23 = -((far + near) / length)
//        dest.m33 = 1


        //Custom Orhto
//        dest.m00 = 1 / width
//        dest.m03 = -(right + left) / width
//        dest.m11 = 1 / height
//        dest.m13 = -(top + bottom) / height
//        dest.m22 = -1 / length
//        dest.m23 = near/ length
//        dest.m33 = 1

        //Real Ortho
        dest.m00 = 2f / (width / VirtualResolution.getScale)
        dest.m11 = 2f / (height / VirtualResolution.getScale)
        dest.m22 = -2f / length
        dest.m32 = -near
        dest.m33 = 1f

        new Matrix4f(dest)
    }

    /**
     * Updates the camera's perspective matrix. Used when the window is resized.
     */
    def updatePerspective(): Unit = {
//        if(mode == Reference.Camera.PERSPECTIVE_MODE) {
            perspective = perspective(if (mode == Reference.Camera.PERSPECTIVE_MODE) fov else orthographicFov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
//        } else {
//            perspective = perspective(orthographicFov, Display.getWidth.toFloat / Display.getHeight.toFloat, nearClip, farClip)
//        }
    }


    private def perspective(fovInDegrees: Float, aspectRatio: Float, near: Float, far: Float): Matrix4f = {
        val top = (near * Math.tan(fovInDegrees * MathUtil.PI360)).toFloat
        val right = top * aspectRatio
        if(mode == Reference.Camera.PERSPECTIVE_MODE) {
            frustum(-right, right, -top, top, near, far)
        } else {
            val width = Display.getWidth
            val height = Display.getHeight
            orthographicFrustum(-right, right, -top, top, near, far)
        }
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
