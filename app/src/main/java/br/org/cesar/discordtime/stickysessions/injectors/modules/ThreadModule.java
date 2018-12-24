package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.executor.JobExecutor;
import br.org.cesar.discordtime.stickysessions.executor.MainThread;
import br.org.cesar.discordtime.stickysessions.executor.PostExecutionThread;
import br.org.cesar.discordtime.stickysessions.executor.ThreadExecutor;
import dagger.Module;
import dagger.Provides;


@Module
public class ThreadModule {

    @Provides
    public PostExecutionThread providePostExecutionThread() {
        return new MainThread();
    }

    @Provides
    public ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }


}
