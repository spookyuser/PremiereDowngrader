
import dagger.Module
import dagger.Provides
import org.jdom2.output.XMLOutputter
import java.util.*
import javax.inject.Singleton

/**
 * Created on 27/08/2017.
 *
 * All Dagger app level dependencies are declared here
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideXMLOutputter() = XMLOutputter()

    @Provides
    @Singleton
    fun provideSvgs(): ResourceBundle = ResourceBundle.getBundle("svgs", Locale.ROOT)
}