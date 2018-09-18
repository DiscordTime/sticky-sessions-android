package br.org.cesar.discordtime.stickysessions.ui.lobby;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import br.org.cesar.discordtime.stickysessions.BuildConfig;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter;
import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyContract;
import br.org.cesar.discordtime.stickysessions.ui.ViewNames;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LobbyActivity extends AppCompatActivity implements LobbyContract.View {

    private Context mContext;
    private ViewGroup parent;
    private Disposable mDisposable;

    @Inject
    public LobbyContract.Presenter mPresenter;

    @Inject
    public IViewStarter mViewStarter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((StickySessionApplication) getApplication()).inject(this);

        mContext = this;
        parent = findViewById(R.id.container);

        TextView versionText = findViewById(R.id.version_text);
        versionText.setText(String.format("v%s", BuildConfig.VERSION_NAME));

        mPresenter.attachView(this);

        configureRecyclerView();
    }

    @Override
    public String getName() {
        return ViewNames.LOBBY_ACTIVITY;
    }

    @Override
    public void goNext(Route route, IBundle bundle) throws InvalidViewNameException{
        mViewStarter.goNext(mContext, route.to, route.shouldClearStack, bundle);
    }

    private void configureRecyclerView() {

        List<SessionTypeViewModel> sessionTypes = new ArrayList<>();
        sessionTypes.add(new SessionTypeViewModel(
                R.string.starfish, R.string.starfish_description,
                R.drawable.starfish, SessionType.STARFISH));
        sessionTypes.add(new SessionTypeViewModel(
                R.string.gain, R.string.gain_description,
                R.drawable.gain, SessionType.GAIN_PLEASURE));
        sessionTypes.add(new SessionTypeViewModel(
                R.string.custom, R.string.custom_description,
                R.drawable.custom, null));

        SessionTypeAdapter adapter = new SessionTypeAdapter(this, sessionTypes);

        mDisposable = adapter.clickEvent.subscribe(new Consumer<SessionType>() {
            @Override
            public void accept(SessionType type) throws Exception {
                mPresenter.onCreateSession(type);
            }
        });

        SessionTypeLayoutManager layoutManager = new SessionTypeLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);
        SessionTypePageIndicator pageIndicator = new SessionTypePageIndicator(sessionTypes.size());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(pageIndicator);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void displaySessionForm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final android.view.View view =
                inflater.inflate(R.layout.dialog_session_form, parent, false);
        builder.setView(view);
        builder.setMessage(getString(R.string.enter_session_id));
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = view.findViewById(R.id.editText);
                String sessionIdString = editText.getText().toString();
                mPresenter.onEnterSession(sessionIdString);
            }
        });
    }

    @Override
    public void displayError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
