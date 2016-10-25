package epiccube.com.br.infobairrotcc.views.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import epiccube.com.br.infobairrotcc.R;
import epiccube.com.br.infobairrotcc.models.mock.Mock;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by abadari on 18/10/2016.
 */

public class AdapterVisualizarFotosPostagem extends PagerAdapter{

    private List<String> fotos;

    private LayoutInflater layoutInflater;
    private PhotoViewAttacher photoView;;
    private Context context;

    public AdapterVisualizarFotosPostagem(List<String> fotos, Context context){
        this.fotos = fotos;
        this.context = context;
    }


    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.adapter_visualizar_fotos, container, false);

        PhotoView imageView = (PhotoView)  item_view.findViewById(R.id.activity_visualizar_fotos_photo_view);

        Glide.with(context).load(fotos.get(position))
                .crossFade()
                //.placeholder(R.drawable.placeholder_img_vazia)
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

        container.addView(item_view);

        photoView = new PhotoViewAttacher(imageView);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
