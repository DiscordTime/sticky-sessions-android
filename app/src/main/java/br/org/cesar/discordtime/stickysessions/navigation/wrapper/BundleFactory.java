package br.org.cesar.discordtime.stickysessions.navigation.wrapper;

public class BundleFactory implements IBundleFactory {
    @Override
    public IBundle create() {
        return new BundleWrapper();
    }
}
