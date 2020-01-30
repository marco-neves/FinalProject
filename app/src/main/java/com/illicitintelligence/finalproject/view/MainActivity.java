package com.illicitintelligence.finalproject.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.adapter.RepoAdapter;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.util.Constants;
import com.illicitintelligence.finalproject.viewmodel.RepoViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements RepoAdapter.RepoDelegate, NavigationView.OnNavigationItemSelectedListener{

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

    private String currentUser="";

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

//  @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpNavigationView();
        setUpDrawer();

        for (int i = 0; i < users.size() ; i++) {

        }

//
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        //editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
//        editor.commit();

        //TODO ADD LOGIN IMPLEMENTATION

        viewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        currentUser= users.get(3);

        getRepositories(currentUser);
    }

    private void addMenuItemInNavMenuDrawer() {
        Menu menu = navigationView.getMenu();
        Menu submenu = menu.addSubMenu("New Super SubMenu");

        submenu.add("Super Item1");
        submenu.add("Super Item2");
        submenu.add("Super Item3");

        navigationView.invalidate();
    }

    private void setUpToolbar() {
        Log.d("TAG_X", " 1: " + getSupportActionBar());
        toolbar = findViewById(R.id.my_custom_toolbar);
        setSupportActionBar(toolbar);
        Log.d("TAG_X", " 2: " + getSupportActionBar());
    }

    //set up the "homepage" navigation view
    private void setUpNavigationView() {
        navigationView = findViewById(R.id.navi_view);

        // TODO: here we have to make sure we already dynamically add in the users.
        addMenuItemInNavMenuDrawer();
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setUpDrawer() {
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void setRV(List<RepoResult> repoResults){
        RepoAdapter repoAdapter = new RepoAdapter(repoResults, this);
        repoRecyclerView.setAdapter(repoAdapter);
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));

    }

    @Override
    public void clickRepo(String repo) {
        Toast.makeText(this, repo+"clicked", Toast.LENGTH_SHORT).show();

        Bundle repoBundle = new Bundle();
        repoBundle.putString(Constants.REPO_KEY, repo);
        repoBundle.putString(Constants.AUTHOR_KEY, currentUser);
        commitsFragment.setArguments(repoBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.commit_framelayout, commitsFragment)
                .addToBackStack(commitsFragment.getTag())
                .commit();

    }
    private void getRepositories(String user) {
        compositeDisposable.add(viewModel.getMyRepo(user).subscribe(repoResults -> {
            setRV(repoResults);
        },throwable -> {
            Log.d("TAG_X", "getRepositories: "+throwable.getMessage());
            Toast.makeText(this, "Repos not available for " + user, Toast.LENGTH_SHORT).show();
        }));

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //TODO: refresh the UI based on the user selected.



        return false;
    }
}
