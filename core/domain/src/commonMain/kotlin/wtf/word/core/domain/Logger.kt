package wtf.word.core.domain

interface Logger {
    fun debug(msg: String, tag: String)

    fun verbose(msg: String, tag: String)

    fun info(msg: String, tag: String)

    fun error(throwable: Throwable, msg: String, tag: String)
}
