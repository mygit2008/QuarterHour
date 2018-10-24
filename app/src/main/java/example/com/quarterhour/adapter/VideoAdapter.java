package example.com.quarterhour.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.boylucky.myquarter.R;
import com.example.boylucky.myquarter.bean.VoderBean;

import java.util.List;

/**
 * Created by BoyLucky on 2018/7/25.
 */

public class VideoAdapter extends RecyclerView.Adapter{
    private List<VoderBean.DataBean> data;
    private Context context;
    private final RequestOptions mRequestOptions;

    public VideoAdapter(List<VoderBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
        mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.video_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_time.setText(data.get(position).getCreateTime());
        myViewHolder.tv_nick.setText(data.get(position).getUser().getNickname());
        if (data.get(position).getUser().getIcon() == null){

            Glide.with(context).load(R.mipmap.ppp).apply(mRequestOptions).into(myViewHolder.img_nick);
        }else{
            Glide.with(context).load(data.get(position).getUser().getIcon()).apply(mRequestOptions).into(myViewHolder.img_nick);
        }
        myViewHolder.vv.setVideoURI(Uri.parse(data.get(position).getVideoUrl()));
//        controller.setMediaPlayer( myViewHolder.vv);
//        myViewHolder.vv.setMediaController(controller);
        //设置视频控制器
        myViewHolder.vv.setMediaController(new MediaController(context));

        //开始播放视频
        myViewHolder.vv.start();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_nick, img_jia;
        TextView tv_nick,tv_time;
        VideoView vv;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_nick = itemView.findViewById(R.id.img_nick);
            img_jia = itemView.findViewById(R.id.img_jia);
            tv_nick = itemView.findViewById(R.id.tv_nick);
            tv_time = itemView.findViewById(R.id.tv_time);
            vv = itemView.findViewById(R.id.vv);

        }
    }
}
