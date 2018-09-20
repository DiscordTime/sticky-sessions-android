package br.org.cesar.discordtime.stickysessions.ui.list;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;
import br.org.cesar.discordtime.stickysessions.presentation.list.ListSessionsContract;
import br.org.cesar.discordtime.stickysessions.ui.ViewNames;
import br.org.cesar.discordtime.stickysessions.ui.adapters.SessionAdapter;

public class ListSessionsActivity extends AppCompatActivity implements ListSessionsContract.View {

    @Inject
    public ListSessionsContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private SessionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((StickySessionApplication)getApplication()).inject(this);

        setContentView(R.layout.list_session);
        mRecyclerView = findViewById(R.id.session_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SessionAdapter();

        mRecyclerView.setAdapter(mAdapter);
        mPresenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.detachView();
    }

    @Override
    public void showSessions(List<Session> sessions) {
        mAdapter.setSessions(sessions);
    }

    @Override
    public void onClickSessionWidgetItem(Session session) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public String getName() {
        return ViewNames.LIST_ACTIVITY;
    }

    @Override
    public void goNext(Route route, IBundle bundle) {

    }
}
