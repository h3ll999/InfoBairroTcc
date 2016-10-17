package epiccube.com.br.infobairrotcc.views.helper;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;

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


    public HelperVisualizaPostagem(AppCompatActivity context, Postagem postagem) {
        this.context = context;
        this.postagem = postagem;
    }

    public HelperVisualizaPostagem cast(){

        perfil = (CircleImageView) context.findViewById(R.id.activity_visualiza_postagem_img_usuario_foto);
        nome = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_usuario_nome);
        //titulo = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_postagem_titulo);
        conteudo = (TextView) context.findViewById(R.id.activity_visualiza_postagem_txv_postagem_conteudo);

        return this;
    }

    public HelperVisualizaPostagem onClick(){



        return this;
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
