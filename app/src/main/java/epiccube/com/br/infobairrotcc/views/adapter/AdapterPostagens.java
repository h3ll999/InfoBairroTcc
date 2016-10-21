package epiccube.com.br.infobairrotcc.views.adapter;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;
import epiccube.com.br.infobairrotcc.models.contantes.Constantes;
import epiccube.com.br.infobairrotcc.models.entities.Postagem;
import epiccube.com.br.infobairrotcc.utils.MyUtils;
import epiccube.com.br.infobairrotcc.views.activity.ActivityVisualizaPostagem;
import epiccube.com.br.infobairrotcc.views.activity.ActivityVisualizarFotos;

/**
 * Created by ivanc on 11/10/2016.
 */

public class AdapterPostagens extends RecyclerView.Adapter<AdapterPostagens.AdapterPostagensViewHolder>{

    private List<Postagem> listaPostagem;
    private AppCompatActivity context;

    public AdapterPostagens(List<Postagem> listaPostagem, AppCompatActivity context) {
        this.listaPostagem = listaPostagem;
        this.context = context;
        EventBus.getDefault().register(this);
    }


    @Override
    public AdapterPostagensViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_postagens, parent, false);
        return new AdapterPostagensViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AdapterPostagensViewHolder holder, int position) {

        Glide.with(context)
                .load(listaPostagem.get(position).getUsuario().getPerfilUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imagemPerfil);

        holder.titulo.setText(listaPostagem.get(position).getTitulo());
        holder.conteudo.setText(MyUtils.verificaFormatacaoPostagem(listaPostagem.get(position).getConteudo()));
        holder.categoria.setText(listaPostagem.get(position).getCategoria());
        holder.nome.setText(listaPostagem.get(position).getUsuario().getNome());

        if(listaPostagem.get(position).getUrlFotosPostagem() == null
                || listaPostagem.get(position).getUrlFotosPostagem().size() == 0 ){

            holder.verMaisImg.setVisibility(View.GONE);
            holder.imagemPost.setVisibility(View.GONE);

        } else if(listaPostagem.get(position).getUrlFotosPostagem().size() == 1){

            holder.verMaisImg.setVisibility(View.GONE);
            holder.imagemPost.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(/*listaPostagem.get(position).getUrlFotosPostagem().get(0)*/R.drawable.wallpaper2)
                    .centerCrop()
                    .thumbnail(0.3f)
                    .placeholder(R.drawable.placeholder_img_vazia)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.imagemPost);

        } else {

            holder.verMaisImg.setVisibility(View.VISIBLE); //TODO FDP
            holder.imagemPost.setVisibility(View.VISIBLE); //TODO FDP
            holder.verMaisImg.setText("[Mais "+(listaPostagem.get(position).getUrlFotosPostagem().size()-1)+" fotos]");

            Glide.with(context)
                    .load(/*listaPostagem.get(position).getUrlFotosPostagem().get(0)*/R.drawable.wallpaper)
                    .centerCrop()
                    .thumbnail(0.3f)
                    .placeholder(R.drawable.placeholder_img_vazia)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.imagemPost);
        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityVisualizaPostagem.class);
                intent.putExtra(Constantes.INTENT_POSTAGEM, listaPostagem.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });



        holder.imagemPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityVisualizarFotos.class);
                intent.putExtra("LISTA_FOTOS", (Serializable) listaPostagem.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

    }

    @Subscribe
    public void onEventClick(){

    }

    @Override
    public int getItemCount() {
        return listaPostagem.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AdapterPostagensViewHolder extends RecyclerView.ViewHolder {

        //DECLARA VARIAVEIS DO POSTAGEM
        private RelativeLayout layout;
        private CircleImageView imagemPerfil;
        private TextView titulo;
        private TextView conteudo;
        private TextView categoria;
        private TextView nome;
        private ImageView imagemPost;
        private TextView verMaisImg;

        public AdapterPostagensViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.menu_inicial_layout_adapter);
            imagemPerfil = (CircleImageView) itemView.findViewById(R.id.menu_inicial_img_foto_usuario);
            titulo = (TextView) itemView.findViewById(R.id.menu_inicial_txv_titulo_postagem);
            conteudo = (TextView) itemView.findViewById(R.id.menu_inicial_txv_conteudo_postagem);
            categoria = (TextView) itemView.findViewById(R.id.menu_inicial_txv_categoria_postagem);
            nome = (TextView) itemView.findViewById(R.id.menu_inicial_txv_nome_usuario_postagem);
            imagemPost = (ImageView) itemView.findViewById(R.id.menu_inicial_img_foto_postagem);
            verMaisImg = (TextView) itemView.findViewById(R.id.menu_inicial_txv_foto_postagem);
        }
    }


    // PARA FINS DE TESTE UNICAMENTE
    @Subscribe
    public void onEventInseriuPostagem(Eventos.InseriuPostagemMockPostagem postagem){
        this.listaPostagem.add(0, postagem.getP());
        this.notifyDataSetChanged();
    }
}

// TODO MAIS IMPORTANTE AGORA: DEIFNIÇÃO DAS CATEGORIAS
// TODO FILTRAGEM PELOS BAIRROS
// TODO Capturar o BAIRRO através do google api do maps...saber como que captura.




