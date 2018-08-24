package br.org.cesar.discordtime.stickysessions.ui.lobby;

import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;

class SessionTypeViewModel {

    int titleRes;
    int descriptionRes;
    int mediaImageRes;
    SessionType type;

    SessionTypeViewModel(
            int titleRes, int descriptionRes, int mediaImageRes, SessionType type) {
        this.titleRes = titleRes;
        this.descriptionRes = descriptionRes;
        this.mediaImageRes = mediaImageRes;
        this.type = type;
    }
}
