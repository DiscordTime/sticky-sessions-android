package br.org.cesar.discordtime.stickysessions.ui.lobby;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
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
import br.org.cesar.discordtime.stickysessions.ui.lobby.indicator.PageIndicator;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LobbyActivity extends AppCompatActivity implements LobbyContract.View {

    private Context mContext;
    private Disposable mDisposable;
    private ProgressBar mLoading;

    @Inject
    public LobbyContract.Presenter mPresenter;

    @Inject
    public IViewStarter mViewStarter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        ((StickySessionApplication) getApplication()).inject(this);

        mContext = this;

        TextView versionText = findViewById(R.id.version_text);
        versionText.setText(String.format("v%s", BuildConfig.VERSION_NAME));

        mPresenter.attachView(this);
        mLoading = findViewById(R.id.progressBar);
        configureRecyclerView();
    }

    @Override
    public void startLoading() {
        mLoading.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void stopLoading() {
        mLoading.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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

        List<LobbyItemViewModel> sessionTypes = loadItems();

        LobbyItemAdapter adapter = new LobbyItemAdapter(this, sessionTypes);

        mDisposable = adapter.clickEvent.subscribe(new Consumer<LobbyContract.ActionType>() {
            @Override
            public void accept(LobbyContract.ActionType actionType) throws Exception {
                mPresenter.onClickSessionOption(actionType);
            }
        });

        SessionTypeLayoutManager layoutManager = new SessionTypeLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false);

        PageIndicator pageIndicator = new PageIndicator(sessionTypes.size());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(pageIndicator);
        recyclerView.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        scrollToFirstRealItemOnInfiniteList(recyclerView, sessionTypes.size());
    }

    private List<LobbyItemViewModel> loadItems() {
        List<LobbyItemViewModel> items = new ArrayList<>();
        items.add(
                new LobbyItemViewModel(
                        R.string.list,
                        R.string.list_description,
                        R.drawable.brainstorm,
                        R.string.join,
                        LobbyContract.ActionType.LIST_SESSIONS)
        );

        items.add(
                new LobbyItemViewModel(
                        R.string.starfish,
                        R.string.starfish_description,
                        R.drawable.starfish,
                        R.string.start,
                        LobbyContract.ActionType.CREATE_STARFISH_SESSION)
        );

        items.add(
                new LobbyItemViewModel(
                        R.string.gain,
                        R.string.gain_description,
                        R.drawable.gain,
                        R.string.start,
                        LobbyContract.ActionType.CREATE_GAIN_N_PLEASURE_SESSION)
        );

        items.add(
                new LobbyItemViewModel(
                        R.string.custom,
                        R.string.custom_description,
                        R.drawable.custom,
                        R.string.create,
                        null)
        );
        return items;
    }

    private void scrollToFirstRealItemOnInfiniteList(RecyclerView recyclerView, int itemCount) {
        int middle = Integer.MAX_VALUE / 2;
        int position = middle % itemCount;
        if (position != 0) {
            middle += (itemCount-position);
        }
        recyclerView.getLayoutManager().scrollToPosition(middle);
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
