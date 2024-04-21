package wtf.word.core.domain.tools

infix fun <T> Set<T>.xor(that: Set<T>): Set<T> = (this - that)
