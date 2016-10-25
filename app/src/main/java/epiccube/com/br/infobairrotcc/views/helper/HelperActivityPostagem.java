package epiccube.com.br.infobairrotcc.views.helper;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.EventoInseriuPostagemMockPostagem;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.eventos.EventoPegarCategoriaPostagem;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.models.singleton.UsuarioLogado;
import epiccube.com.br.infobairrotcc.utils.MyUtils;
import epiccube.com.br.infobairrotcc.views.adapter.AdapterPostagemFotos;
import epiccube.com.br.infobairrotcc.views.dialogs.DialogoSelecionarCategoria;

/**
 * Created by abadari on 14/10/2016.
 */

public class HelperActivityPostagem {

    private AppCompatActivity context;

    private EditText titulo;
    private EditText conteudo;
    private ImageView inserirFotos;
    private Button postar;
    //private Spinner categorias;
    private GridView fotos;
    private RelativeLayout layoutGrid;
    private ArrayList<String> imagens;

    private Postagem p;

    // TODO SEGUIR ESSE PADRÃO DE DESIGN....LOUCO DEMAIS
    // http://www.cssauthor.com/wp-content/uploads/2015/04/Facebook-Material-Design-GUI-Kit-PSD.jpg

    public HelperActivityPostagem(AppCompatActivity context){
        this.context = context;
        this.context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EventBus.getDefault().register(this); //registra a classe para ele ouvir...
    }

    public static HelperActivityPostagem init(AppCompatActivity context){
        return new HelperActivityPostagem(context);
    }


    public HelperActivityPostagem cast(){

        titulo = (EditText) context.findViewById(R.id.activity_postar_edt_titulo);
        conteudo = (EditText) context.findViewById(R.id.activity_postar_edt_conteudo);
        postar = (Button) context.findViewById(R.id.activity_postar_btn_concluir);
        postar.getBackground().setColorFilter(Color.parseColor("#00ffffff"), PorterDuff.Mode.SRC_ATOP);
        inserirFotos = (ImageView) context.findViewById(R.id.activity_postar_img_abrir_galeria);
        //categorias = (Spinner) context.findViewById(R.id.activity_postar_spn_selecionar_categoria);
        fotos = (GridView) context.findViewById(R.id.activity_postar_grid_view);
        layoutGrid = (RelativeLayout) context.findViewById(R.id.activity_postar_relative2);
        imagens = new ArrayList<>();

        return this;
    }

    public HelperActivityPostagem onClick(){

        postar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Fragmento
                FragmentManager fm = context.getSupportFragmentManager();
                DialogoSelecionarCategoria a =  new DialogoSelecionarCategoria();
                a.show(fm, "DIALOG_FRAGMENT_POSTAGEM");

            }
        });

        inserirFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                context.startActivityForResult(Intent.createChooser(intent, "Selecionar IMAGEM"), 1);
                //context.startActivityForResult(intentGaleria, 1);
            }
        });

        return this;
    }

    /*public void configureSpn(){
        String [] categorias = context.getResources().getStringArray(R.array.activity_postar_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, categorias);
        this.categorias.setAdapter(adapter);
    }*/

    private void getData(String categoria){
        p = new Postagem();
        p.setTitulo(titulo.getText().toString().trim());
        p.setConteudo(conteudo.getText().toString().trim());
        p.setCategoria(categoria);
        p.setUsuario(UsuarioLogado.getInstancia().getUsuario());
        p.setUrlFotosPostagem(imagens);
        Log.e("POSTAGEM", "getData");
    }

    public void unregister(){
        EventBus.getDefault().unregister(this);
    }


    // Chamado após selecionar as imagens...Cria o adapter.
    @Subscribe
    public void onEventSelecionarFotos(Eventos.PostagemMultiplasImagens imagens){

        //para fins de teste...
        for(Uri u: imagens.getFotos()){
            Log.e("CHEGOU A FOTO", u.getPath());
        }

        for(Uri u: imagens.getFotos()){
            this.imagens.add(u.getPath());
        }

        //mata a foto de chamar a galeria
        inserirFotos.setVisibility(View.GONE);

        //Chamar um adaptador para preencher uma mini-lista
        fotos.setAdapter(new AdapterPostagemFotos(imagens.getFotos(), context));

    }

    // Chamado para aparecer a imagem de add imagens após deselecionar todas
    @Subscribe
    public void onEventApareceImagem(Eventos.AparecerImagem img){
        //se a lista chegar a zero, reaparece a imagem de chamar a galeria
        inserirFotos.setVisibility(View.VISIBLE);
    }

    // Chamado quando seleciona a categoria no popup
    @Subscribe
    public void onEventSelecionouCategoria(EventoPegarCategoriaPostagem categoriaPostagem){
        getData(categoriaPostagem.getCategoria());

        Log.e("POSTAGEM", "formata categoria");
        //formata categoria...
        String categoria = MyUtils.formatCategoria(categoriaPostagem.getCategoria());

        // firebase...
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String primaryKey = ref.child(categoria).push().getKey();

        ref.child(categoria).child(primaryKey).setValue(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Compartilhado", Toast.LENGTH_SHORT).show();
                        Log.e("POSTAGEM", "Compartilhado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erro "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        context.finish();
    }

}
