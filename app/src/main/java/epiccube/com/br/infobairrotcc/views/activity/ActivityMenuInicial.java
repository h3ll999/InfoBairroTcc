package epiccube.com.br.infobairrotcc.views.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.models.entities.Usuario;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;
import epiccube.com.br.infobairrotcc.utils.AsyncHTTP;
import epiccube.com.br.infobairrotcc.utils.LocationUtils;
import epiccube.com.br.infobairrotcc.utils.MyGPS;
import epiccube.com.br.infobairrotcc.utils.MyUtils;
import epiccube.com.br.infobairrotcc.utils.Permissions;
import epiccube.com.br.infobairrotcc.utils.ViewUtil;
import epiccube.com.br.infobairrotcc.views.adapter.AdapterPostagens;
import epiccube.com.br.infobairrotcc.views.asynctask.AsyncTaskLocation;
import epiccube.com.br.infobairrotcc.views.asynctask.AsynkTaskMockPostagem;
import epiccube.com.br.infobairrotcc.views.dialogs.DialogPostagem;

public class ActivityMenuInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ProgressBar p;
    private RecyclerView recyclerView;
    private ViewFlipper viewFlipper;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        Toast.makeText(this, "Bem vindo, "+ UsuarioLogado.getInstancia().getUsuario().getNome(), Toast.LENGTH_SHORT).show();

        EventBus.getDefault().register(this);

        setFlipper();
        setToolbar();
        setFAB();
        setLoading();
        setDrawer();
        setNavView();

        //se user se cadastrou mas afirmou que não estava no local de origem, pergunta novamente.
        if(UsuarioLogado.getInstancia().getUsuario().getEstadoOrigemId() == null){
            pergunta();
        } else {
            // caso contrário, segue fluxo.
            checagemInicial();
        }
    }

    void checagemInicial(){
        if(UsuarioLogado.getInstancia().getUsuario().getLatitudeLongitude() == null){
            // TODO ver melhor modo de verificar isso (INTENT)
            //se não pegou a posicao do GPS no cadastro, pega no login
            Log.e("onCreate", "veio do Login"); // login não roda o GPS, por isso precisei fazer assim.
            setProgressBar();
            initGps();
        } else {
            Log.e("onCreate", "veio do Cadastro"); // cadastro roda o GPS de qualquer forma, então ele já vai pra cá...

            getDataFromFirebase(Constantes.POSTAGENS_EVENTOS());
        }
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
                if(UsuarioLogado.getInstancia().getUsuario().getPermissaoPostagem()){
                    Intent intent = new Intent(ActivityMenuInicial.this, ActivityPostagem.class);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.anim_up_primeiro, R.anim.anim_up_segundo);
                } else {
                    ViewUtil.init(ActivityMenuInicial.this).showDialog(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }, getString(R.string.location_nao_bairro_origem));
                }

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
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
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

    private void setFlipper() {
        viewFlipper = (ViewFlipper) findViewById(R.id.activity_menu_inicial_flipper);
        viewFlipper.setDisplayedChild(0);
    }

    private void initGps() {
        MyGPS myGPS = new MyGPS(this);
        myGPS.init();
    }

    private void setProgressBar() {
        progressDialog = ProgressDialog.show(this, getString(R.string.progress_search),
                getString(R.string.progress_wait), true, false);
    }

    public void getDataFromFirebase(final String filtro) {
        Log.e("getDataFromFirebase",filtro);
        startLoading();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        // pega os dados com base no no estado+cidade+bairro+categoria
        ref.child(filtro).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {// se clicou em qualquer outra categoria, filtra normalmente
                    List<Postagem> listagemPostagens = new ArrayList<Postagem>();

                    if(dataSnapshot.getChildrenCount() != 0){//se NÃO está vazio o banco
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Postagem p = postSnapshot.getValue(Postagem.class);
                            listagemPostagens.add(p);
                        }
                        dismissLoading();
                        setRecyclerView(listagemPostagens);
                    } else {
                        dismissLoading();
                        //setRecyclerView(listagemPostagens);
                        viewFlipper.setDisplayedChild(1);// BANCO VAZIO MOSTRA LAYOUT COM MENSAGEM
                    }

                    // verificação para permissão de postagens...
                    verificaPermissao();
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(ActivityMenuInicial.this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificaPermissao() {
        // SE ATUAL == ORIGEM...
        if(MyUtils.concatenaCaminhoAtual().equals(MyUtils.concatenaCaminhoOrigem())){
            UsuarioLogado.getInstancia().getUsuario().setPermissaoPostagem(true);
        } else {
            UsuarioLogado.getInstancia().getUsuario().setPermissaoPostagem(false);
        }
    }

    private void updateUsuario(){
        progressDialog = ProgressDialog.show(this,"Atualizando", "Aguarde...", true, false);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Usuario u = UsuarioLogado.getInstancia().getUsuario();
        ref.child(Constantes.USUARIO).child(user.getUid()).child(Constantes.PERFIL).setValue(u)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        //Toast.makeText(ActivityMenuInicial.this, "Atualizado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // se der erro, RESETA como estava antes...
                        //TODO melhor tratamento a isso

                        UsuarioLogado.getInstancia().getUsuario().setPermissaoPostagem(false);
                        UsuarioLogado.getInstancia()
                                .getUsuario().setEstadoOrigemId(null);
                        UsuarioLogado.getInstancia()
                                .getUsuario().setCidadeOrigemId(null);
                        UsuarioLogado.getInstancia()
                                .getUsuario().setBairroOrigemId(null);

                        progressDialog.dismiss();
                        Toast.makeText(ActivityMenuInicial.this, "Erro: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    void setRecyclerView(List<Postagem> listagemPostagem){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Collections.reverse(listagemPostagem);

        recyclerView.setAdapter(new AdapterPostagens(listagemPostagem, this));

    }

    void setLoading(){
        recyclerView = (RecyclerView) findViewById(R.id.activity_menu_inicial_recycler_view); // recycler view castado antes...
        p = (ProgressBar) findViewById(R.id.progressBar1);
        p.setVisibility(View.INVISIBLE);
        startLoading();
    }

    void startLoading(){
        if(fab.isEnabled()){
            fab.setEnabled(false);
            recyclerView.setVisibility(View.INVISIBLE);
            p.setVisibility(View.VISIBLE);
        }
    }

    void dismissLoading(){
        fab.setEnabled(true);
        recyclerView.setVisibility(View.VISIBLE);
        p.setVisibility(View.INVISIBLE);
    }

    void pergunta(){
        // pergunta se é o bairro residencial
        ViewUtil.init(this).showDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UsuarioLogado.getInstancia().getUsuario().setPermissaoPostagem(true);
                //seta o estado/cidade/bairro de origem o mesmo que o atual...
                UsuarioLogado.getInstancia()
                        .getUsuario().setEstadoOrigemId(UsuarioLogado.getInstancia().getUsuario().getEstadoAtualId());
                UsuarioLogado.getInstancia()
                        .getUsuario().setCidadeOrigemId(UsuarioLogado.getInstancia().getUsuario().getCidadeAtualId());
                UsuarioLogado.getInstancia()
                        .getUsuario().setBairroOrigemId(UsuarioLogado.getInstancia().getUsuario().getBairroAtualId());

                dialog.dismiss();

                // banco pra atualizar com TRUE o usuário...
                updateUsuario();

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    public void locais(){

        Log.e("Activity", "locais");
        // já está salvo as coords nesse momento...
        Double[] coords = UsuarioLogado.getInstancia().getUsuario().getLatitudeLongitude();

        try {
            progressDialog.dismiss();

            //LocationUtils l = new LocationUtils(this, coords);
            //l.getLocais();

            // TODO Chama o asynchttp

            /*AsyncTaskLocation asyncTaskLocation = new AsyncTaskLocation(this, coords);
            asyncTaskLocation.execute();*/

            AsyncHTTP.get(Constantes.GOOGLE_API(coords[0], coords[1]), null, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {

                        JSONObject results = response.getJSONArray("results").getJSONObject(0);

                        JSONObject bairroJson = results.getJSONArray("address_components").getJSONObject(2);
                        String bairro = bairroJson.getString("long_name");

                        JSONObject cidadeJson = results.getJSONArray("address_components").getJSONObject(3);
                        String cidade = cidadeJson.getString("long_name");

                        JSONObject estadoJson = results.getJSONArray("address_components").getJSONObject(5);
                        String estado = estadoJson.getString("long_name");

                        //Log.e("onSuccess",estado+" | "+cidade+" | "+bairro);

                        UsuarioLogado.getInstancia().getUsuario().setEstadoAtualId(estado);
                        UsuarioLogado.getInstancia().getUsuario().setCidadeAtualId(cidade);
                        UsuarioLogado.getInstancia().getUsuario().setBairroAtualId(bairro);

                        // Este método está sendo chamado quando entra no app e quando clica no trocar localidade...
                        getDataFromFirebase(Constantes.POSTAGENS_EVENTOS());

                    } catch (JSONException e) {
                        Log.e("Exception", "FUDEU");
                        // tem que mostrar uma msg que deu pau na google...
                    }
                }
            });

        } catch (Exception e) {
            locais(); // chama ele mesmo em caso de erro...
        }
    }

    public void setaEndereco(String[] locais){
        UsuarioLogado.getInstancia().getUsuario().setEstadoAtualId(locais[0]);
        UsuarioLogado.getInstancia().getUsuario().setCidadeAtualId(locais[1]);
        UsuarioLogado.getInstancia().getUsuario().setBairroAtualId(locais[2]);

        // Este método está sendo chamado quando entra no app e quando clica no trocar localidade...
        getDataFromFirebase(Constantes.POSTAGENS_EVENTOS());
    }

    void checkMenuButton(){
        //se data do user logado + 3 dias for menos que a data atual, então troca
        // TEORICAMENTE FUNCIONA ESSE NEGÓCIO
        if(UsuarioLogado.getInstancia().getUsuario().getUltimaTrocaLocal() == null){// se nunca tiver trocado, pode trocar
            Log.e("checkMenuButton","se nunca tiver trocado, pode trocar");
            progressDialog = ProgressDialog.show(this, getString(R.string.progress_search),
                    getString(R.string.progress_wait), true, false);
            MyGPS myGPS = new MyGPS(this);
            myGPS.init();

        } else if(UsuarioLogado.getInstancia().getUsuario().getUltimaTrocaLocal().getTime()// lógica normal
                +TimeUnit.DAYS.toMillis(3) < new Date().getTime()) {
            Log.e("checkMenuButton","lógica normal");
            progressDialog = ProgressDialog.show(this, getString(R.string.progress_search),
                    getString(R.string.progress_wait), true, false);
            MyGPS myGPS = new MyGPS(this);
            myGPS.init();

        } else {
            ViewUtil.init(this).showCancelDialogLocation();// caso contrário, nega a troca
            Log.e("checkMenuButton","else");
        }
    }

    @Subscribe
    public void pegaEndereco(Eventos.PegaEndereco endereco){
        Log.e("Activity", "pegaEndereco");
        setaEndereco(endereco.getLocais());
    }

    @Subscribe
    public void pegaCoordenada(Eventos.PegaCoordenada coordenada){
        Double [] coord = coordenada.getCood();

        // já está salvo as coords nesse momento...
        UsuarioLogado.getInstancia().getUsuario().setLatitudeLongitude(coord);
        //UsuarioLogado.getInstancia().getUsuario().setUltimaTrocaLocal(new Date());
        locais();
    }

    @Subscribe
    public void pegaNovaCoordenada(Eventos.PegaNovaCoordenada novaCoordenada){
        Double [] coord = novaCoordenada.getCood();

        UsuarioLogado.getInstancia().getUsuario().setLatitudeLongitude(coord);
        UsuarioLogado.getInstancia().getUsuario().setUltimaTrocaLocal(new Date());

        locais();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else if(dialog!=null){
            EventBus.getDefault().post(new Eventos.FechaDialogoPostagem());
        }*/ else {
            FirebaseAuth.getInstance().signOut();
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

        switch (id){
            case R.id.action_settings:
                UsuarioLogado.getInstancia().clear();
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            case R.id.action_location:
                //TODO PERMISSÃO DA LOCALIZAÇÃO
                /*if(!Permissions.FINE_LOCATION.temPermissao(this)){
                    Permissions.FINE_LOCATION.solicitaPermissao(this);
                }*/
                checkMenuButton();
                break;
            case R.id.action_new_loc:
                MyGPS gps = new MyGPS(this);
                gps.init();
                break;
            default:
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.menu_categoria_todas) {
            if(viewFlipper.getDisplayedChild()==1){
                viewFlipper.setDisplayedChild(0);
            }
            getDataFromFirebase(Constantes.POSTAGENS_SEM_FILTRO);
        } else */

        if (id == R.id.menu_categoria_evento) {
            if(viewFlipper.getDisplayedChild()==1){
                viewFlipper.setDisplayedChild(0);
            }
            getDataFromFirebase(Constantes.POSTAGENS_EVENTOS());

        } else if (id == R.id.menu_categoria_noticia) {
            if(viewFlipper.getDisplayedChild()==1){
                viewFlipper.setDisplayedChild(0);
            }
            getDataFromFirebase(Constantes.POSTAGENS_NOTICIAS());

        } else if (id == R.id.menu_categoria_servico) {
            if(viewFlipper.getDisplayedChild()==1){
                viewFlipper.setDisplayedChild(0);
            }
            getDataFromFirebase(Constantes.POSTAGENS_SERVICOS());

        } else if(id == R.id.menu_categoria_origem){
            if(viewFlipper.getDisplayedChild()==1){
                viewFlipper.setDisplayedChild(0);
            }
            getDataFromFirebase(Constantes.POSTAGENS_ORIGEM());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        Toast.makeText(this, getString(R.string.exiting), Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }
}
