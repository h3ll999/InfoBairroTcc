package epiccube.com.br.infobairrotcc.views.helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;

/**
 * Created by abadari on 14/10/2016.
 */

public class HelperActivityPostagem {

    private Activity context;

    private EditText titulo;
    private EditText conteudo;
    private ImageView inserirFotos;
    private Button postar;
    private Spinner categorias;

    // TODO SEGUIR ESSE PADRÃO DE DESIGN....LOUCO DEMAIS
    // http://www.cssauthor.com/wp-content/uploads/2015/04/Facebook-Material-Design-GUI-Kit-PSD.jpg

    public HelperActivityPostagem(Activity context){
        this.context = context;
        this.context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EventBus.getDefault().register(this); //registra a classe para ele ouvir...
    }

    public static HelperActivityPostagem init(Activity context){
        return new HelperActivityPostagem(context);
    }


    public HelperActivityPostagem cast(){

        titulo = (EditText) context.findViewById(R.id.activity_postar_edt_titulo); titulo.requestFocus();
        conteudo = (EditText) context.findViewById(R.id.activity_postar_edt_conteudo);
        postar = (Button) context.findViewById(R.id.activity_postar_btn_concluir);
        postar.getBackground().setColorFilter(Color.parseColor("#00ffffff"), PorterDuff.Mode.SRC_ATOP);
        inserirFotos = (ImageView) context.findViewById(R.id.activity_postar_img_abrir_galeria);


        categorias = (Spinner) context.findViewById(R.id.activity_postar_spn_selecionar_categoria);

        // esconde do teclado
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return this;
    }

    public HelperActivityPostagem onClick(){

        postar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,
                        titulo.getText().toString().trim()+"\n\n"+
                                conteudo.getText().toString().trim()+"\n\n"+
                        categorias.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();

                context.finish();

            }
        });

        inserirFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                context.startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 1);
                //context.startActivityForResult(intentGaleria, 1);
            }
        });

        return this;
    }

    public void configure(){
        String [] categorias = context.getResources().getStringArray(R.array.activity_postar_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, categorias);

        this.categorias.setAdapter(adapter);
    }

    @Subscribe
    public void onEventSelecionarFotos(Eventos.PostagemMultiplasImagens imagens){
        for(Uri u: imagens.getFotos()){
            Log.e("CHEGOU A FOTO", u.getPath());
        }

        Glide.with(context).load(imagens.getFotos().get(0)).into(inserirFotos);

    }


}
