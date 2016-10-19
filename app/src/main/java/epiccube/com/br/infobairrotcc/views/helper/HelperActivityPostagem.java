package epiccube.com.br.infobairrotcc.views.helper;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import epiccube.com.br.infobairrotcc.R;

/**
 * Created by abadari on 14/10/2016.
 */

public class HelperActivityPostagem {

    private Activity context;

    private EditText titulo;
    private EditText conteudo;
    // fotos...ver como vai ficar
    private Button postar;
    private Spinner categorias;

    // TODO SEGUIR ESSE PADRÃO DE DESIGN....LOUCO DEMAIS
    // http://www.cssauthor.com/wp-content/uploads/2015/04/Facebook-Material-Design-GUI-Kit-PSD.jpg

    public HelperActivityPostagem(Activity context){
        this.context = context;
        this.context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static HelperActivityPostagem init(Activity context){
        return new HelperActivityPostagem(context);
    }


    public HelperActivityPostagem cast(){

        titulo = (EditText) context.findViewById(R.id.activity_postar_edt_titulo); titulo.requestFocus();
        conteudo = (EditText) context.findViewById(R.id.activity_postar_edt_conteudo);
        postar = (Button) context.findViewById(R.id.activity_postar_btn_concluir);
        postar.getBackground().setColorFilter(Color.parseColor("#00ffffff"), PorterDuff.Mode.SRC_ATOP);
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
                        titulo.getText().toString().trim()+"\n"+
                                conteudo.getText().toString().trim()+"\n"+
                        categorias.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();

                context.finish();

            }
        });
        return this;
    }

    public void configure(){
        String [] categorias = context.getResources().getStringArray(R.array.activity_postar_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item, categorias);

        this.categorias.setAdapter(adapter);
    }



}
