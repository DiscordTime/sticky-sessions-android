package br.org.cesar.discordtime.stickysessions.navigation.wrapper;

import android.os.Bundle;

public class BundleFactory implements IBundleFactory {
    @Override
    public BundleWrapper create() {
        return new BundleWrapper();
    }
}
