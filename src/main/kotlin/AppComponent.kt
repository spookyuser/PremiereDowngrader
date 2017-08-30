import dagger.Component
import injection.BindingModule
import mainview.MainView
import javax.inject.Singleton

/**
 * Created on 27/08/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, BindingModule::class))
interface AppComponent {
    fun inject(premiereDowngraderApp: PremiereDowngraderApp)
    fun inject(mainView: MainView)
}