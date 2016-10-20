package epiccube.com.br.infobairrotcc.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.mock.Mock;
import epiccube.com.br.infobairrotcc.models.singleton.SingletonUsuario;
import epiccube.com.br.infobairrotcc.views.adapter.AdapterPostagens;
import epiccube.com.br.infobairrotcc.views.dialogs.DialogPostagem;

public class ActivityMenuInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;

    private DialogPostagem dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        setToolbar();
        setFAB();
        setDrawer();
        setNavView();
        setRecyclerView();

    }

    void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // app:elevation="0dp" no app bar main
    }

    void setFAB(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentManager fm = getSupportFragmentManager();
                //dialog = new DialogPostagem(ActivityMenuInicial.this);
                //dialog.show(fm, "DIALOG_FRAGMENT_POSTAGEM");


                Intent intent = new Intent(ActivityMenuInicial.this, ActivityPostagem.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_up_primeiro, R.anim.anim_up_segundo);
            }
        });
    }

    void setDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    void setNavView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHeaderDetalhes(navigationView);
    }

    void setHeaderDetalhes(NavigationView nv){

        //Pega
        View v = nv.getHeaderView(0);

        // Chama
        CircleImageView imgUser = (CircleImageView) v.findViewById(R.id.activity_menuinicial_img_perfil);
        TextView nomeUser = (TextView) v.findViewById(R.id.activity_menuinicial_txv_nome_usuario);

        //Valoriza
        Glide.with(this).load(SingletonUsuario.getInstancia().getUsuario().getPerfilUrl())
                //.bitmapTransform(new CropCircleTransformation(this))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .into(imgUser);

        nomeUser.setText(SingletonUsuario.getInstancia().getUsuario().getNome());

        //Cliques
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityMenuInicial.this,
                        SingletonUsuario.getInstancia().getUsuario().getNome(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    void setRecyclerView(){ // TODO vai ir para um fragmento?!?!?!?!?
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_menu_inicial_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new AdapterPostagens(Mock.postagens(), this));
        //recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(dialog!=null){
            EventBus.getDefault().post(new Eventos.FechaDialogoPostagem());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
