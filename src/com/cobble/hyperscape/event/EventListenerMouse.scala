package com.cobble.hyperscape.event

trait EventListenerMouse {

    /**
     * Called when the mouse moves
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     */
    def onMouseMove(x: Int, y: Int): Unit = {}

    /**
     * Called when the mouse is clicked
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param mouseButton The button that was pressed
     */
    def onClick(x: Int, y: Int, mouseButton: Int): Unit = {}

    /**
     * Called when the mouse is pressed
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param mouseButton The button that was pressed
     */
    def onMouseDown(x: Int, y: Int, mouseButton: Int): Unit = {}

    /**
     * Called when the mouse button is released
     * @param x The x location of the mouse
     * @param y The y location of the mouse
     * @param mouseButton The button that was pressed
     */
    def onMouseUp(x: Int, y: Int, mouseButton: Int): Unit = {}

}