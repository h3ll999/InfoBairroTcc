package epiccube.com.br.infobairrotcc.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.EventoPegarCategoriaPostagem;

/**
 * Created by abadari on 24/10/2016.
 */

public class DialogoSelecionarCategoria extends DialogFragment {

    private Spinner spn;
    private String [] categorias;
    boolean selecionado = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_selecionar_categoria, container);

        configuraSpn(v);

        return v;
    }

    void configuraSpn(View v){

        categorias = getActivity().getResources().getStringArray(R.array.activity_postar_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, categorias);

        spn = (Spinner) v.findViewById(R.id.dialog_selecionar_postagem_spn);
        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(selecionado){ //TODO BUGADO DO KARAI
                    String selecao = categorias[i];
                    EventBus.getDefault().post(new EventoPegarCategoriaPostagem(selecao));
                    dismiss();
                } else {
                    selecionado = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
