package com.illicitintelligence.finalproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.adapter.RepoAdapter;
import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.viewmodel.RepoViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements RepoAdapter.RepoDelegate{

    private static final String TAG = "MainActivity";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RepoViewModel viewModel;
    private CommitsFragment commitsFragment = new CommitsFragment();

    ArrayList<String> users = new ArrayList<String>() {{
        add("GermL");
        add("Joel-Jacob");
        add("marco-neves");
        add("moniqueberry88");
    }};

    @BindView(R.id.recyclerView_repos)
    RecyclerView repoRecyclerView;

    @BindView(R.id.add_user_editText)
    EditText addUserEditText;

    @BindView(R.id.add_user_button)
    Button addUserButton;

    @BindView(R.id.add_user_close_button)
    ImageView addUserCloseButton;

    @BindView(R.id.add_user_constraintLayout)
    ConstraintLayout addUserConstraintLayout;

    @BindView(R.id.delete_user_button)
    Button deleteUserButton;

    @BindView(R.id.delete_user_close_button)
    ImageView deleteUserCloseButton;

    @BindView(R.id.delete_user_constraintLayout)
    ConstraintLayout deleteUserConstraintLayout;

    @BindView(R.id.commit_framelayout)
    FrameLayout commits_fragment_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_activity_layout);

        ButterKnife.bind(this);

        //TODO ADD LOGIN IMPLEMENTATION

        viewModel = ViewModelProviders.of( this ).get( RepoViewModel.class );
        getRepositories(users.get(0));
        //getMyCommits();
    }

    private void setRV(List<RepoResult> repoResults){
        RepoAdapter repoAdapter = new RepoAdapter(repoResults, this);
        repoRecyclerView.setAdapter(repoAdapter);
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

    }

    @Override
    public void clickRepo(String repo) {
        Toast.makeText(this, repo+"clicked", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.commit_framelayout, commitsFragment)
                .addToBackStack(commitsFragment.getTag())
                .commit();

        //TODO move to next fragment
    }


    private void getRepositories(String user) {
        compositeDisposable.add( viewModel.getMyRepo( user ).subscribe(
                new Consumer<List<RepoResult>>() {
                    @Override
                    public void accept(List<RepoResult> repoResults) throws Exception {

                        for (int i = 0; i < repoResults.size(); i++) {
                            Log.d( TAG, "repo: " + repoResults.get( i ).getName() );
                        }

                        setRV(repoResults);
                    }
                }
                )
        );
    }

}
