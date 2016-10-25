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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;
import epiccube.com.br.infobairrotcc.views.adapter.AdapterPostagens;
import epiccube.com.br.infobairrotcc.views.asynctask.AsynkTaskMockPostagem;
import epiccube.com.br.infobairrotcc.views.dialogs.DialogPostagem;

public class ActivityMenuInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ProgressBar p;

    private DialogPostagem dialog;
    private Object dataFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        //EventBus.getDefault().register(this);

        setLoading();
        setToolbar();
        setFAB();
        setDrawer();
        setNavView();
        getDataFromFirebase(Constantes.POSTAGENS_SEM_FILTRO);

        //new AsynkTaskMockPostagem().execute(p);

    }

    // após o fim da requisição dos dados da asynctask, executa o método abaixo...
    /*@Subscribe
    public void onEventMockPostagens(Eventos.ResultadoAsyncTaskMockPostagem postagens){
        setRecyclerView(postagens.getPostagems());
    }*/

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
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    void setNavView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setHeaderDetalhes(navigationView);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    void setHeaderDetalhes(NavigationView nv){

        //Pega
        View v = nv.getHeaderView(0);

        // Chama
        CircleImageView imgUser = (CircleImageView) v.findViewById(R.id.activity_menuinicial_img_perfil);
        TextView nomeUser = (TextView) v.findViewById(R.id.activity_menuinicial_txv_nome_usuario);

        //Valoriza
        Glide.with(this).load(UsuarioLogado.getInstancia().getUsuario().getPerfilUrl())
                //.bitmapTransform(new CropCircleTransformation(this))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .into(imgUser);

        nomeUser.setText(UsuarioLogado.getInstancia().getUsuario().getNome());

        //Cliques
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityMenuInicial.this,
                        UsuarioLogado.getInstancia().getUsuario().getNome(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getDataFromFirebase(String filtro) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child(filtro).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Postagem> listagemPostagens = new ArrayList<Postagem>();
                HashMap<String, String> mapListagemPostagens = (HashMap<String, String>) dataSnapshot.getValue();

                    if(dataSnapshot.getChildrenCount()==0){//se tá vazio o banco

                    }else{
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Postagem p = postSnapshot.getValue(Postagem.class);
                        listagemPostagens.add(p);
                    }

                }

                setRecyclerView(listagemPostagens);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    void setRecyclerView(List<Postagem> listagemPostagem){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_menu_inicial_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dismissLoading();

        Collections.reverse(listagemPostagem);

        recyclerView.setAdapter(new AdapterPostagens(listagemPostagem, this));

        //recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }

    void setLoading(){
        p = (ProgressBar) findViewById(R.id.progressBar1);
    }

    void dismissLoading(){
        p.setVisibility(View.GONE);
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_categoria_todas) {
            getDataFromFirebase(Constantes.POSTAGENS_SEM_FILTRO);
        } else if (id == R.id.menu_categoria_evento) {
            getDataFromFirebase(Constantes.POSTAGENS_EVENTOS);
        } else if (id == R.id.menu_categoria_noticia) {
            getDataFromFirebase(Constantes.POSTAGENS_NOTICIAS);
        } else if (id == R.id.menu_categoria_servico) {
            getDataFromFirebase(Constantes.POSTAGENS_SERVICOS);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
