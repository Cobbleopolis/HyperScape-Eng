package com.cobble.hyperscape.gui

/**
 * Used by various GUI elements so that they can be used by GUIs
 */
abstract class GuiElement {

    def render(): Unit

    def destroy(): Unit
}
