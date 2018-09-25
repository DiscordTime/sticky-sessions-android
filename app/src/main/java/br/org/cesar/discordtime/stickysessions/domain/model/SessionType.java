package br.org.cesar.discordtime.stickysessions.domain.model;

public enum SessionType {
    GAIN_PLEASURE("Gain pleasure"),
    STARFISH("Starfish"),
    CUSTOM("custom");

    private final String mType;

    SessionType(String type){
        mType = type;
    }

    @Override
    public String toString() {
        return mType;
    }
}
