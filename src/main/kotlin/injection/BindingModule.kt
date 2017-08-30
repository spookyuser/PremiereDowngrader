package injection

import dagger.Binds
import dagger.Module
import data.ChangeVersion
import data.ChangeVersionImp

/**
 * Created on 29/08/2017.
 *
 * All Dagger interface to implementation Bindings are declared here
 */
@Module
abstract class BindingModule {
    @Binds abstract fun bindChangeVersion(changeVersionImp: ChangeVersionImp): ChangeVersion
}