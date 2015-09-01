package com.cobble.hyperscape.render

import org.lwjgl.opengl.{GL11, GL20}

/**
 * Creates a shader object
 * @param programId The id of the shader program
 * @param vertexId The id of the vertex shader program
 * @param fragmentId The id of the fragment shader program
 */
class Shader(programId: Int, vertexId: Int, fragmentId: Int)  {
    var uniformLocations: Map[String, Int] = Map() /** Map of the uniform locations for the shader*/

    /**
     * Returns the location for the uniform location
     * @param name Name of the uniform variable
     * @return Location of the uniform variable
     */
    def getUniformLocation(name: String): Int = {
        var location: Int = 0
        if (uniformLocations.contains(name)) {
            location = uniformLocations(name)
        } else {
            location = GL20.glGetUniformLocation(programId, name)
            uniformLocations += (name -> location)
        }
        location
    }


    def getProgramId: Int = programId

    def getVertexId: Int = vertexId

    def getFragmentId: Int = fragmentId


    /**
     * Binds the shader
     */
    def bind(): Unit = {
        GL20.glUseProgram(programId)
        val err = GL11.glGetError()
        if (err != 0) {
            println("In Shader | " + err)
            System.exit(1)
        }
    }

    /**
     * Destroies the shader
     */
    def destroy(): Unit = {
        GL20.glUseProgram(0)
        GL20.glDetachShader(programId, vertexId)
        GL20.glDetachShader(programId, fragmentId)

        GL20.glDeleteShader(fragmentId)
        GL20.glDeleteShader(vertexId)
        GL20.glDeleteProgram(programId)
    }
}
