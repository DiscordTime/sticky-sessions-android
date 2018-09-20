package br.org.cesar.discordtime.stickysessions.ui.lobby;

import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyContract;

class LobbyItemViewModel {

    int titleRes;
    int descriptionRes;
    int mediaImageRes;
    int buttonText;
    LobbyContract.ActionType type;

    LobbyItemViewModel(
            int titleRes, int descriptionRes, int mediaImageRes, int buttonText,
            LobbyContract.ActionType type) {
        this.titleRes = titleRes;
        this.descriptionRes = descriptionRes;
        this.mediaImageRes = mediaImageRes;
        this.buttonText = buttonText;
        this.type = type;
    }
}
