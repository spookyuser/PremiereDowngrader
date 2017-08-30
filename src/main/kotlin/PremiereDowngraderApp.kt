
import cli.CliApp
import de.jupf.staticlog.Log
import javafx.application.Application
import javafx.scene.Scene
import mainview.MainView
import mainview.MainViewStyle
import tornadofx.*
import javax.inject.Inject


/**
 * Created on 27/08/2017.
 *
 * Main entry point for Dagger and TornadoFx. Either starts the UI or converts the file via command line
 */
class PremiereDowngraderApp : App(MainView::class, MainViewStyle::class) {

    override fun createPrimaryScene(view: UIComponent): Scene = Scene(view.root, 500.0, 500.0)

    val appComponent: AppComponent = DaggerAppComponent.create()

    @Inject lateinit var cliApp: CliApp

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PremiereDowngraderApp().run(args)
        }
    }

    fun run(args: Array<String>) {
        appComponent.inject(this)
        if (args.isNotEmpty()) cliApp.startCli(args.first())
        else startUi()
    }

    private fun startUi() {
        Log.info("Starting...")
        Application.launch(PremiereDowngraderApp::class.java)
    }
}