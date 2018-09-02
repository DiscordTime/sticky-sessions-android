package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.logger.AppLog;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import dagger.Module;
import dagger.Provides;

@Module
public class LoggerModule {

    @Provides
    public Logger provideLogger() {
        return new AppLog();
    }
}
