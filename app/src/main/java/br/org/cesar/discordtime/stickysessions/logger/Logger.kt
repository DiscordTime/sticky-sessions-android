package br.org.cesar.discordtime.stickysessions.logger;

interface Logger {
    fun d(tag: String, message: String)
    fun w(tag: String, message: String)
    fun w(tag: String, message: String, e: Throwable?)
    fun e(tag: String, message: String)
    fun e(tag: String, message: String, e: Throwable?)
    fun v(tag: String, message: String)
    fun i(tag: String, message: String)
}
