package com.cobble.hyperscape.gui

trait GuiScreen {



    /** A list of the buttons in the Gui **/
    val buttonList: List[GuiButton] = List()

    def initGui(): Unit = {

    }

    /**
     * Renders the screen
     */
    def render(): Unit = {

        buttonList.foreach(button => {
            button.render()
        })

    }
    
}
