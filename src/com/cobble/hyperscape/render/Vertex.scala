package com.cobble.hyperscape.render

object Vertex {

    /** Amount of bytes in a float */
    val BYTES_IN_FLOAT = 4

    /** The amount of elements in one vertex */
    val ELEMENT_COUNT = 8

    /** The amount of elements in the x, y, z location */
    val VERTEX_SIZE = 3

    /** The amount of elements in one uv element */
    val UV_SIZE = 2

    /** The amount of elements in one normal element */
    val NORMAL_SIZE = 3

    /** The offset for the x, y, z elements in bytes */
    val VERTEX_OFFSET = 0

    /** The offset for the UV elements in bytes */
    val UV_OFFSET_IN_BYTES = 3 * BYTES_IN_FLOAT

    /** The offset for the normal in bytes */
    val NORMAL_OFFSET_IN_BYTES = 5 * BYTES_IN_FLOAT

    /** The size of one vertex in bytes */
    val SIZE_IN_BYTES = ELEMENT_COUNT * BYTES_IN_FLOAT

    /** The offset for color in bytes */
    val COLOR_OFFSET = 3 * BYTES_IN_FLOAT

    /** The amount of elements in one color element */
    val COLOR_SIZE = 4

    /** The amount of bytes in a color vertex */
    val COLOR_VERTEX_SIZE_IN_BYTES = 7 * BYTES_IN_FLOAT
}
