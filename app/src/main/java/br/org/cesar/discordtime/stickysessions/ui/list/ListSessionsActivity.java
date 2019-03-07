package br.org.cesar.discordtime.stickysessions.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.app.StickySessionApplication;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.navigation.exception.InvalidViewNameException;
import br.org.cesar.discordtime.stickysessions.navigation.router.Route;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IBundle;
import br.org.cesar.discordtime.stickysessions.navigation.wrapper.IViewStarter;
import br.org.cesar.discordtime.stickysessions.presentation.list.ListSessionsContract;
import br.org.cesar.discordtime.stickysessions.ui.ViewNames;
import io.reactivex.disposables.Disposable;

public class ListSessionsActivity extends AppCompatActivity implements ListSessionsContract.View {

    @Inject
    public ListSessionsContract.Presenter mPresenter;
    @Inject
    public IViewStarter mViewStarter;
    private Disposable mDisposable;
    protected RecyclerView mRecyclerView;
    private SessionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private TextView mToolbarTitle;
    private Button mRetryButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((StickySessionApplication)getApplication()).inject(this);
        setContentView(R.layout.list_session);
        mContext = this;
        //Loading
        mProgressBar = findViewById(R.id.progressbar);
        mRetryButton = findViewById(R.id.retry_button);
        configureToolbar();
        configureRecycleView();
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.include);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(R.string.toolbar_title_list_session);
    }

    private void configureRecycleView() {
        mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView = findViewById(R.id.session_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new SessionAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mDisposable = mAdapter.clickEvent.subscribe(session -> mPresenter.enterOnSession(session));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        mPresenter.onLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
    @Override
    public void startLoadingData() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.INVISIBLE);
    }
    @Override
    public void stopLoadingData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSessions(List<Session> sessions) {
        mAdapter.setSessions(sessions);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void showRetryOption() {
        mRetryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public String getName() {
        return ViewNames.LIST_ACTIVITY;
    }

    @Override
    public void goNext(Route route, IBundle bundle) throws InvalidViewNameException {
        mViewStarter.goNext(this, route.to, route.shouldClearStack, bundle);
    }

    public void onRetryClick(View view) {
        mPresenter.onLoad();
    }
}
