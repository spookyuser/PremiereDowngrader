package cli

import data.ChangeVersion
import de.jupf.staticlog.Log
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * Created on 30/08/2017.
 */
class CliApp @Inject constructor(private val changeVersion: ChangeVersion) {

    fun startCli(filepath: String) {
        val fileToChange = File(filepath)

        if (!fileToChange.exists() || fileToChange.extension != "prproj") {
            println("Bad file, \n Usage: PremiereDowngrader <file to change>")
            System.exit(1)
        }

        changeVersion.To1(fileToChange)
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                        onComplete = {
                            println("`${fileToChange.name}` Downgraded successfully")
                            System.exit(1)
                        },
                        onError = { throwable ->
                            Log.error(throwable.toString())
                            System.exit(1)
                        })
    }
}