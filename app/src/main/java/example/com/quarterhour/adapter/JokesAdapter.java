package example.com.quarterhour.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.quarterhour.R;
import example.com.quarterhour.bean.JokesBean;



public class JokesAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<JokesBean.DataBean> data;

    public JokesAdapter(Context context, List<JokesBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.cross_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_time.setText(data.get(position).getCreateTime());
        myViewHolder.tv.setText(data.get(position).getContent());
            myViewHolder.tv_nick.setText(data.get(position).getUser().getNickname());
        Glide.with(context).load(data.get(position).getUser().getIcon()).into(myViewHolder.img_nick);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
                ImageView img_nick, img_jia;
                TextView tv_nick,tv_time,tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_nick = itemView.findViewById(R.id.head_icon);
            img_jia = itemView.findViewById(R.id.img_jia);
            tv_nick = itemView.findViewById(R.id.tv_nick);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv = itemView.findViewById(R.id.tv);

        }
    }
}
