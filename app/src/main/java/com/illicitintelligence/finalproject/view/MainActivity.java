package com.illicitintelligence.finalproject.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.adapter.RepoAdapter;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.util.Constants;
import com.illicitintelligence.finalproject.util.Logger;
import com.illicitintelligence.finalproject.viewmodel.RepoViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements RepoAdapter.RepoDelegate, NavigationView.OnNavigationItemSelectedListener {

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

    private String currentUser = "";
    private String avatarUrl = "";

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

    ImageView drawerAvatar;
    DrawerLayout drawerLayout;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private StringBuilder sb = new StringBuilder();
    private String tempUsers;
    private SharedPreferences userSharedPref;
    private SharedPreferences.Editor editor;

    public void saveToSharedPreference(String key, String users) {
        editor = userSharedPref.edit();
        editor.putString(key, users);
        editor.apply();
    }

    // this method is to be called if we're within 24hr of the last api call
    public String fetchFromSharedPreference(String key) {
        if(userSharedPref.contains(key))
            return "";
        return userSharedPref.getString(key, getString(R.string.default_user_value));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();

        userSharedPref = this.getSharedPreferences(Constants.USERNAME_SHARED_PREFS, Context.MODE_PRIVATE);

        if (!userSharedPref.contains(Constants.USER_PREF_KEY)) {
            translateArrayList();
        }
        else {
            tempUsers = fetchFromSharedPreference(Constants.USER_PREF_KEY);
            populateMenuOptions(tempUsers);
        }

        //TODO ADD LOGIN IMPLEMENTATION

        viewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        currentUser = users.get(3);

        getRepositories(currentUser);
    }

    private void setAvatar() {
        //TODO: set the avatar
        //curently has an error where the drawer id is null
        try {
            Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(avatarUrl)
                    .into(drawerAvatar);
        } catch (Exception e) {
            Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(Constants.DEFAULT_ICON)
                    .into(drawerAvatar);
            Log.d("TAG_X", "onBindViewHolder: " + e.getMessage());
        }
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.my_custom_toolbar);
        setSupportActionBar(toolbar);
        setUpNavigationView();
    }

    //set up the "homepage" navigation view
    private void setUpNavigationView() {
        navigationView = findViewById(R.id.navi_view);

        // TODO: here we have to make sure we already dynamically add in the users.
        navigationView.setNavigationItemSelectedListener(this);
        setUpDrawer();
    }

    private void setUpDrawer() {
        drawerLayout = findViewById(R.id.drawer);
        drawerAvatar = findViewById(R.id.drawer_imageView);
        //setAvatar();// for use with setting avatar

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

    }
    private void translateArrayList() {
        //TODO: check if the sharepreferences is null
        for (int i = 0; i < users.size() ; i++) {
            //TODO: add users from arrayList to sharedpreferences
            sb.append(users.get(i)).append(",");
        }
        tempUsers = sb.toString();

        saveToSharedPreference(Constants.USER_PREF_KEY, tempUsers);
        Logger.logIt(tempUsers);

        // TODO: Before populating the menu list we need to check if it's
        populateMenuOptions(tempUsers);
    }
    private void setRV(List<RepoResult> repoResults) {
        RepoAdapter repoAdapter = new RepoAdapter(repoResults, this);
        repoRecyclerView.setAdapter(repoAdapter);
        repoRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    public void clickRepo(String repo) {
        Toast.makeText(this, repo + "clicked", Toast.LENGTH_SHORT).show();

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
            Log.d("TAG_X", "getRepositories: " + throwable.getMessage());
            Toast.makeText(this, "Repos not available for " + user, Toast.LENGTH_SHORT).show();
        }));
    }

    public void populateMenuOptions(String myUsers){
        Menu menu = navigationView.getMenu();
        Menu submenu = menu.addSubMenu("Current Users:");

        // reference the names in sharedpreferences
        String[] userList = myUsers.split(",");

        // reference the names in sharedpreferences
        for(String user: userList){
            submenu.add(user);
            Logger.logIt("Added User: " + user);
        }

        navigationView.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.logIt("MenuItem: " + item.toString());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //TODO: refresh the UI based on the user selected.
        return false;
    }
}
