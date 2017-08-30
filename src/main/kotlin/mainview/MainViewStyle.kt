package mainview

import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*


/**
 * Created on 29/08/2017.
 *
 * Stylesheet for the [MainView]
 */
class MainViewStyle : Stylesheet() {


    init {
        button {
            fontFamily = "Helvetica"
            fontWeight = FontWeight.EXTRA_BOLD
            fontSize = 30.px
            backgroundColor += c("#3B3B3B")
            textFill = Color.WHITE
            and(hover) {
                backgroundColor += c("#005599")
                borderColor += box(
                        all = c("#2c312e")
                )
                borderStyle += BorderStrokeStyle.DOTTED
            }
            and(pressed) {
                backgroundColor += c("#2f2f2f")
            }
        }
        label {
            textFill = Color.WHITE
            fontFamily = "Helvetica"
            fontSize = 30.px
            fontWeight = FontWeight.BOLD
        }
        root {
            backgroundColor += c("2B2B2B")
            padding = box(50.px, 10.px, 10.px, 10.px)
        }


    }
}