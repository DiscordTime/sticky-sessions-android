package br.org.cesar.discordtime.stickysessions.logger;

public interface Logger {
    void d(String tag, String message);
    void w(String tag, String message);
    void e(String tag, String message);
    void v(String tag, String message);
    void i(String tag, String message);
}
