package epiccube.com.br.infobairrotcc.views.adapter;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;

/**
 * Created by abadari on 20/10/2016.
 */

public class AdapterPostagemFotos extends BaseAdapter {

    private List<Uri> listagemFotos;
    private AppCompatActivity context;

    public AdapterPostagemFotos(List<Uri> listagemFotos, AppCompatActivity context) {
        this.listagemFotos = listagemFotos;
        this.context = context;
        //adicionaAdicionar();
    }

    @Override
    public int getCount() {
        return listagemFotos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ImageView imageView;

        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 130));
        }
        else
        {
            imageView = (ImageView) view;
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(i == listagemFotos.size()-1){
                    //abre galeria...
                    Intent intent = new Intent();
                    intent.setType("image*//*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    context.startActivityForResult(Intent.createChooser(intent, "Selecionar IMAGEM"), 1);
                } else {*/
                    AdapterPostagemFotos.this.listagemFotos.remove(i);
                    AdapterPostagemFotos.this.notifyDataSetChanged();
                    if(listagemFotos.size()==0){
                        EventBus.getDefault().post(new Eventos.AparecerImagem());
                    }
                //}

            }
        });

        /*if(i == listagemFotos.size()-1){
            Glide.with(context)
                    .load(R.drawable.mais)
                    .centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
                    //.placeholder(R.drawable.placeholder_img_vazia).into(imageView);
        } else {*/
            Glide.with(context)
                    .load(listagemFotos.get(i))
                    .centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    //.thumbnail(0.1f)
                    .placeholder(R.drawable.placeholder_img_vazia).into(imageView);
        //}
        return imageView;
    }

    public void adicionaAdicionar(){
        Uri u = Uri.parse("android.resource://"+context.getPackageName()+"/" + R.drawable.mais);
        listagemFotos.add(u);
    }

}
