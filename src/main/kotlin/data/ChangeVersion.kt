package data

import io.reactivex.Completable
import java.io.File

/**
 * Created on 29/08/2017.
 */
interface ChangeVersion {
    fun To1(fileToChange: File): Completable
}