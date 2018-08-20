package br.org.cesar.discordtime.stickysessions.injectors.modules;

import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.ViewStarter;
import dagger.Module;
import dagger.Provides;

@Module
public class ViewStarterModule {

    @Provides
    public IViewStarter providesViewStarter(){
        return new ViewStarter();
    }

}
