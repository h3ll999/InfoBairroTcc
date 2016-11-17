package epiccube.com.br.infobairrotcc.views.helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.views.activity.ActivityVisualizarFotos;

/**
 * Created by abadari on 17/10/2016.
 */

public class HelperVisualizaPostagem {

    private AppCompatActivity context;
    private Postagem postagem;

    private CircleImageView perfil;
    private TextView nome;
    //private TextView titulo;
    private TextView conteudo;
    private ImageView imagensPostagens;
    private TextView verMais;

    private EditText estado;
    private EditText cidade;
    private EditText bairro;


    public HelperVisualizaPostagem(AppCompatActivity context, Postagem postagem) {
        this.context = context;
        this.postagem = postagem;
    }

    public HelperVisualizaPostagem cast(){

        perfil = (CircleImageView) context.findViewById(R.id.activity_visualiza_postagem_img_usuario_foto);
        nome = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_usuario_nome);
        //titulo = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_postagem_titulo);
        conteudo = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_postagem_conteudo);
        imagensPostagens = (ImageView) context.findViewById(R.id.activity_visualiza_postagem_img_foto_postagem);
        verMais = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_foto_postagem);

        return this;
    }

    public HelperVisualizaPostagem onClick(){
        configImg();

        imagensPostagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityVisualizarFotos.class);
                intent.putExtra("LISTA_FOTOS", (Serializable) postagem);
                context.startActivity(intent);
            }
        });

        return this;
    }

    private void configImg(){
        if(postagem.getUrlFotosPostagem()==null || postagem.getUrlFotosPostagem().size() == 0){
            imagensPostagens.setVisibility(View.GONE);
            verMais.setVisibility(View.GONE);
        } else if(postagem.getUrlFotosPostagem().size() == 1){
            Glide.with(context)
                    .load(postagem.getUrlFotosPostagem().get(0))
                    .centerCrop()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.placeholder_img_vazia)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imagensPostagens);
            verMais.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(postagem.getUrlFotosPostagem().get(0))
                    .centerCrop()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.placeholder_img_vazia)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imagensPostagens);
            verMais.setText("[Mais "+(postagem.getUrlFotosPostagem().size()-1)+" foto(s)]");
        }
    }

    public void configure(){

        Glide.with(context).load(postagem.getUsuario().getPerfilUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .into(perfil);

        nome.setText(postagem.getUsuario().getNome());
        //titulo.setText(postagem.getTitulo());
        conteudo.setText(postagem.getConteudo());

    }
}
