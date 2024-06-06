
import androidx.room.Room
import com.usmonie.word.features.dictionary.data.db.room.DictionaryDatabase
import com.usmonie.word.features.dictionary.data.db.room.instantiateImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

internal actual val roomModule: Module = module {
    single {
        val dbFilePath = NSHomeDirectory() + "/dictionary.db"
        Room.databaseBuilder<DictionaryDatabase>(
            name = dbFilePath,
            factory = { DictionaryDatabase::class.instantiateImpl() }
        )
    }
}
