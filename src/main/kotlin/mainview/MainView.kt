package mainview

import PremiereDowngraderApp
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.input.DragEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.VBox
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import tornadofx.*
import java.util.*
import javax.inject.Inject


/**
 * Created on 25/08/2017.
 *
 * View for the whole app
 * Drag and Drop from https://github.com/edvin/tornadofx/blob/fb68403fd7d7d0ab331176cac400c74c5d2de439/src/main/java/tornadofx/osgi/OSGIConsole.kt
 *
 * @property [svgs][strings][values] are all resource bundles that provide strings, svgs paths and color values
 * @property viewController should control logic for the view
 */
class MainView : View("Premiere Downgrader") {

    @Inject lateinit var svgs: ResourceBundle

    @Inject lateinit var viewController: MainViewController

    lateinit var defaultCenter: VBox
    lateinit var defaultEngine: WebEngine
    lateinit var webview: WebView
    private val defaultSvg by lazy { svgs["defaultSvg"] }
    private val compatibleFileSvg by lazy { svgs["compatibleFileSvg"] }
    private val incompatibleFileSvg by lazy { svgs["incompatibleFileSvg"] }
    val indexUrl by lazy { MainView::class.java.getResource("/index.html").toString() }
    private val iconImage by lazy { Image(MainView::class.java.getResource("/icon.png").toString()) }

    /**
     * Starts injection, for some reason constructor injection doesn't work on this class
     * */
    init {
        PremiereDowngraderApp().appComponent.inject(this)
    }

    override fun onDock() {
        super.onDock()
        currentStage?.isResizable = false
    }

    override val root = borderpane {
        addStageIcon(iconImage)


        val svgView = svgpath(defaultSvg) {
            scaleX = 10.0
            scaleY = 10.0
            fill = c("#fff")
        }

        defaultCenter = vbox(100) {
            useMaxSize = true
            alignment = Pos.CENTER
            label {
                text = "Or drag and drop a \uD83D\uDCC4"
            }
            this += svgView
        }

        val defaultTop = hbox {
            useMaxSize = true
            alignment = Pos.CENTER
            button {
                setPrefSize(200.0, 80.0)
                text = "Browse"
                setOnAction {
                    viewController.onButtonAction()
                }
            }
        }

        /**
         * Set the [WebEngine] url to null so that it doesn't render in the background, if you set the
         * url to the proper [indexUrl] here the animation immediately starts playing invisibly
         * */
        webview = webview {
            defaultEngine = engine
            defaultEngine.load(null)
        }

        /**
         * After all the views [webview], [svgView], [defaultTop] have been initilised, they are set to components in
         * the [borderpane]
         * */
        this.center = defaultCenter
        this.top = defaultTop

        /**
         * Listens for an onDragOver [DragEvent], when one happens it checks it if the file is acceptable input,
         * sets the copy icon on the cursor and sets the svg portion of the screen to the [compatibleFileSvg] path
         * */
        setOnDragOver { event ->
            if (viewController.isAcceptableFile(event)) {
                event.acceptTransferModes(TransferMode.COPY)
                event.consume()
                style { svgView.content = compatibleFileSvg }
            } else {
                svgView.content = incompatibleFileSvg
            }
        }

        /**
         * When a user's mouse exits the drag area the svg view is set back to the [defaultSvg] path
         *
         * This is also called when the user drops the file successfully, after onDragDropped
         * */
        setOnDragExited {
            style { svgView.content = defaultSvg }
        }

        /**
         * Calls [viewController.onDragDropped] method which starts the version downgrade
         *
         * [defaultEngine] now loads the actual 'URI' to the index where a bodyMovin animation is shown
         * */
        setOnDragDropped { event ->
            viewController.onDragDropped(event)
        }

    }

}
