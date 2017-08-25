import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*

/**
 * Created on 25/08/2017.
 */


class MainView : View("My View") {
    override val root = VBox()

    init {
        root += Button("Press Me")
        root += Label("")
    }
}
