package br.org.cesar.discordtime.stickysessions.navigation.wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.ui.ViewNames;
import br.org.cesar.discordtime.stickysessions.ui.list.ListSessionsActivity;
import br.org.cesar.discordtime.stickysessions.ui.lobby.LobbyActivity;
import br.org.cesar.discordtime.stickysessions.ui.login.LoginActivity;
import br.org.cesar.discordtime.stickysessions.ui.main.MainActivity;
import br.org.cesar.discordtime.stickysessions.ui.session.SessionActivity;

public class ViewStarter implements IViewStarter {

    private final HashMap<String, Class<? extends Activity>> activities =
        new HashMap<String, Class<? extends Activity>> (){{
            put(ViewNames.LOGIN_ACTIVITY, LoginActivity.class);
            put(ViewNames.LOBBY_ACTIVITY, LobbyActivity.class);
            put(ViewNames.SESSION_ACTIVITY, SessionActivity.class);
            put(ViewNames.LIST_ACTIVITY, ListSessionsActivity.class);
            put(ViewNames.MEETING_ACTIVITY, MainActivity.class);
        }};

    public void goNext(Context context, String activityName, boolean shouldClearStack)
            throws InvalidViewNameException{
        goNext(context, activityName, shouldClearStack, null);
    }

    public void goNext(Context context, String activityName, boolean shouldClearStack,
                       IBundle iBundle) throws InvalidViewNameException{
        Class<? extends Activity> className = activities.get(activityName);
        if(className == null){
            throw new InvalidViewNameException(activityName);
        }
        Intent intent = new Intent(context, className);

        if (iBundle != null) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, String> entry : iBundle.getExtras().entrySet()) {
                bundle.putString(entry.getKey(), entry.getValue());
            }
            intent.putExtras(bundle);
        }

        if (shouldClearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        context.startActivity(intent);
    }
}
