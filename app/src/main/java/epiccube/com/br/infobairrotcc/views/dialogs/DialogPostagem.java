package epiccube.com.br.infobairrotcc.views.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.eventos.Eventos;

/**
 * Created by abadari on 14/10/2016.
 */

public class DialogPostagem extends DialogFragment {

    private Activity context;

    public DialogPostagem(){}

    @SuppressLint("ValidFragment")
    public DialogPostagem(Activity context){
        this.context = context;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentCompat);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        View v = inflater.inflate(R.layout.activity_postagem, container);
        //HelperActivityPostagem.init(context).cast().onClick();
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        return v;
    }

    @Override
    public void onResume() {

        int width = getActivity().getResources().getDisplayMetrics().widthPixels;
        int height = getActivity().getResources().getDisplayMetrics().heightPixels;

        //Toast.makeText(getActivity(), width+" "+height, Toast.LENGTH_SHORT).show();

        //WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        //params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        //params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;
        //getDialog().getWindow().setAttributes(params);


        super.onResume();
    }


    @Subscribe
    public void onEventDismiss(Eventos.FechaDialogoPostagem fechaDialogoPostagem){
        this.dismiss();
    }


}
