package mainview

import data.ChangeVersion
import de.jupf.staticlog.Log
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javafx.scene.input.DragEvent
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import javax.inject.Inject

/**
 * Created on 27/08/2017.
 *
 * Should provide all logic needed for the MainView
 *
 * @property changeVersion is an injected interface of the [ChangeVersion] class that changes
 * the premiere xml version number
 */
class MainViewController
@Inject constructor(private val changeVersion: ChangeVersion) : Controller() {

    /**
     * Provides access to the [MainView]
     * */
    private val mainView: MainView by inject()

    /**
     * Called after button press or drag, subscribes to the [ChangeVersion.To1] Completable
     *
     * If the Completable completes successfully, wait for the animation to finish a loop by listening for
     * an alert created by the animation, see index.html
     *
     * @param fileToChange is the premiere [File] to change
     * */
    private fun changeVersion(fileToChange: File) {
        setLoadingScreen()
        changeVersion
                .To1(fileToChange)
                .subscribeOn(Schedulers.computation())
                .observeOn(JavaFxScheduler.platform())
                .subscribeBy(
                        onComplete = {
                            Log.info("Version changed")
                            mainView.defaultEngine.setOnAlert {
                                setDefaultScreen()
                            }
                        },
                        onError = { throwable -> Log.error(throwable.toString()) }
                )
    }

    /**
     * Sets the state of the screen to a spinner
     * */
    private fun setLoadingScreen() = with(mainView) {
        defaultEngine.load(indexUrl)
        root.center = webview
    }

    /**
     * Sets the state of the screen to a the default state (drag and drop or browse)
     * */
    private fun setDefaultScreen() = with(mainView) {
        root.center.opacity = 1.0
        root.center = mainView.defaultCenter
        defaultEngine.load(null)
    }

    /**
     * Calls the local [changeVersion] function
     *
     * @param event a [DragEvent] that contains the file path string and other info, like the file extension
     * */
    fun onDragDropped(event: DragEvent) {
        if (isAcceptableFile(event))
            changeVersion(fileToChange = File(event.dragboard.files.first().path))
    }

    /**
     * Checks if the file is acceptable input, at the moment that is only premiere files (.prproj)
     *
     * @param event a [DragEvent] that contains the file path string and other info, like the file extension
     * */
    fun isAcceptableFile(event: DragEvent): Boolean =
            event.dragboard.hasFiles() && event.dragboard.files.first().name.toLowerCase().endsWith(".prproj")

    /**
     * Sends the premiere file to [changeVersion] by button click
     * */
    fun onButtonAction() {
        val filepath: List<File>? = chooseFile(
                title = "Pick a premiere project",
                filters = arrayOf(FileChooser.ExtensionFilter("Premiere Projects", "*.prproj")),
                mode = FileChooserMode.Single
        )
        filepath?.let { list -> if (list.isNotEmpty()) changeVersion(list.first()) }
    }

}