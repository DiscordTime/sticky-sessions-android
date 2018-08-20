package br.org.cesar.discordtime.stickysessions.navigation.router;

public class Route {

    public String from;
    public String event;
    public String to;
    public boolean shouldClearStack;

    public Route(String from, String event) {
        this(from, event, null, false);
    }

    public Route(String from, String event, String to, boolean shouldClearStack) {
        this.from = from;
        this.event = event;
        this.to = to;
        this.shouldClearStack = shouldClearStack;
    }

}
