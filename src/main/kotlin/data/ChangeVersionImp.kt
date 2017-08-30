package data

import io.reactivex.Completable
import okio.GzipSink
import okio.GzipSource
import okio.Okio
import org.jdom2.input.SAXBuilder
import org.jdom2.output.XMLOutputter
import java.io.File
import javax.inject.Inject

/**
 * Created on 26/08/2017.
 *
 * Does the core processing of the xml file in the gzipped .prproj file
 *
 * All .prproj files - so far - are just gzipped xml. They are usually megabytes in size but so far
 * the Dom style approach of loading the whole xml file seems to handle the size perfectly fine.
 * Original implementation by Sean Bright
 *
 * @property xmlOutputter an injected [XMLOutputter] used to save the changed inputStream
 *
 * @See 'https://stackoverflow.com/a/37283543/1649917'
 */
class ChangeVersionImp
@Inject constructor(private val xmlOutputter: XMLOutputter) : ChangeVersion {

    /**
     * Simply changes version to '1'
     *
     * Initially the program changed version to a user inputted version number but since changing the version number has no
     * actual effect on premiere, it's easier and more user friendly to change it to '1' than the specific version
     * of premiere the user is using. Premier will upgrade the file just fine, either way.
     *
     * This method works by using Okio's Gzip sink to manipulate the file as an inputStream and outputStream
     * that Jdom can handle. By using Okio, we never need to write temporary files, everything is handled in memory
     *
     * The version element Jdom searches for reads:
     * <Project ObjectID="1" ClassID="62ad66dd-0dcd-42da-a660-6d8fbde94876" Version="29">
     *
     * @param fileToChange a File created in the MainViewController from the filepath that leads to the premiere file to
     * be created
     * @return Completable that can be subscribed to on an Computation thread
     * */
    override fun To1(fileToChange: File): Completable {
        return Completable.fromCallable {
            val source = Okio.buffer(GzipSource(Okio.source(fileToChange)))
            val output = File(fileToChange.parent + "\\${fileToChange.nameWithoutExtension}_downgraded.prproj")
            val sink = Okio.buffer(GzipSink(Okio.sink(output)))

            val xmlDocument = SAXBuilder().build(source.inputStream())
            xmlDocument.rootElement
                    .getChildren("Project")[1]
                    .getAttribute("Version").value = "1"
            xmlOutputter.output(xmlDocument, sink.outputStream())
            sink.close()
            source.close()
        }
    }
}

