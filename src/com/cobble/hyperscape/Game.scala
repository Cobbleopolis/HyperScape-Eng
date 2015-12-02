package com.cobble.hyperscape

import java.io._

import com.cobble.hyperscape.core.{HyperScape, Init}
import com.cobble.hyperscape.registry.{ShaderRegistry, TextureRegistry}
import com.cobble.hyperscape.util.GLUtil
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl._
import org.lwjgl.{LWJGLException, Sys}

object Game {
    val WINDOW_TITLE = "HyperScape - Indev"
    val WIDTH = 1080
    val HEIGHT = 720

    var hyperScape: HyperScape = null

    var lastFrame: Long = 0

    var isFullscreen: Boolean = false

    var prevWidth: Int = WIDTH
    var prevHeight: Int = HEIGHT
    
    var resize: Boolean = false

    var fullscreen: Boolean = false

    //    var firstRender: Boolean = true

    /**
     * Main method of the game
     * @param args Arguments passed to the game
     */
    def main(args: Array[String]): Unit = {
        setNatives()
	    HyperScape.debug = args.contains("--debug")
	    fullscreen = args.contains("--fullscreen")
        initGL()
        Init.loadAssets()
        ShaderRegistry.bindShader("gui")
        //        TextureRegistry.bindTexture("terrain")

        lastFrame = getTime

        hyperScape = new HyperScape
        hyperScape.init()
        //        hyperScape.changeState("mainMenu")

        Keyboard.enableRepeatEvents(true)

        //        GL11.glViewport(0, 0, Display.getWidth, Display.getHeight)
        //        HyperScape.mainCamera.updatePerspective()
        //        HyperScape.mainCamera.uploadPerspective()

        while (!Display.isCloseRequested && !HyperScape.isCloseRequested) {
            //            if (firstRender) {
            //                println("First Render")
            //                GL11.glViewport(0, 0, Display.getWidth, Display.getHeight)
            //                HyperScape.mainCamera.updatePerspective()
            //                HyperScape.mainCamera.uploadPerspective()
            //                firstRender = false
            //            }
            if (Display.wasResized || resize) {
                GL11.glViewport(0, 0, Display.getWidth, Display.getHeight)
                HyperScape.mainCamera.updatePerspective()
                HyperScape.mainCamera.uploadPerspective()
                resize = false
            }
            hyperScape.tick()
            //            HyperScape.mainCamera.uploadPerspective()
            // Map the internal OpenGL coordinate system to the entire screen
            hyperScape.render()
            //            val err = GL11.glGetError()
            //            if (err != 0) {
            //                println("Error in shader" + ShaderRegistry.getCurrentShader + " | " + err)
            //                System.exit(1)
            //            }
            Display.sync(60)
            Display.update()
        }
        hyperScape.destroy()
        ShaderRegistry.destroyAllShaders()
        TextureRegistry.destroyAllTextures()
        Display.destroy()

    }

    /**
     * Sets the natives based on the operating system
     */
    def setNatives(): Unit = {
        val os = System.getProperty("os.name").toLowerCase
        var suffix = ""
        if (os.contains("win")) {
            suffix = "windows"
        } else if (os.contains("mac")) {
            suffix = "macosx"
        } else {
            suffix = "linux"
        }
        //        val nativePath = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "lwjgl" + File.separator + "native" + File.separator + suffix
        val nativePath = System.getProperty("user.dir") + File.separator + "native" + File.separator + suffix
        System.setProperty("org.lwjgl.librarypath", nativePath)
    }

    /**
     * Initializes OpenGL
     */
    def initGL(): Unit = {
        // Setup an OpenGL context with API version 3.3
        try {
            val pixelFormat = new PixelFormat()
            val contextAtrributes = new ContextAttribs(3, 3)
                .withForwardCompatible(true)
                .withProfileCore(true)

            //			Display.getAvailableDisplayModes.foreach(dM => {
            //				if (dM.getWidth == WIDTH && dM.getHeight == HEIGHT && dM.isFullscreenCapable) {
            //					println("Mode")
            //					displayMode = dM
            //					return
            //				}
            //			})

            Display.setDisplayModeAndFullscreen(if (fullscreen) Display.getDesktopDisplayMode else new DisplayMode(WIDTH, HEIGHT))
            Display.setTitle(WINDOW_TITLE)

            Display.setResizable(true)
            Display.create(pixelFormat, contextAtrributes)
        } catch {
            case e: LWJGLException => e.printStackTrace(); System.exit(-1)
        }
//        GL11.glViewport(0, 0, WIDTH, HEIGHT)
        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glClearColor(0.67058823529411764705882352941176f, 0.8078431372549019607843137254902f, 1f, 1f)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glViewport(0, 0, Display.getWidth, Display.getHeight)
        HyperScape.mainCamera.updatePerspective()

        //        GL11.glEnable(GL11.GL_BLEND)
        //        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    }

    def toggleFullScreen(): Unit = {
        if (Display.isFullscreen == fullscreen) {
            fullscreen = !fullscreen
            if (fullscreen) {
                //				displayModeNormal = Display.getDisplayMode
                prevWidth = Display.getWidth
                prevHeight = Display.getHeight
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode)
//                Display.setFullscreen(true)
            } else {
	            Display.setFullscreen(false)
	            Display.setDisplayModeAndFullscreen(new DisplayMode(prevWidth, prevHeight))
	            Display.setResizable(true)
            }

            //			Display.setFullscreen(true)
            //			Display.setDisplayModeAndFullscreen(if (fullscreen) displayModeFullScreen else new DisplayMode(WIDTH, HEIGHT))
            GLUtil.checkGLError("Change Fullscreen")
        }
    }
    
    def requestResize(): Unit = {
        resize = true
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     * @return milliseconds passed since last frame
     */
    def getDelta: Int = {
        val time = getTime
        val delta = (time - lastFrame).toInt
        lastFrame = time
        delta
    }

    /**
     * Get the time in milliseconds
     * @return The system time in milliseconds
     */
    def getTime: Long = {
        (Sys.getTime * 1000) / Sys.getTimerResolution
    }
}
