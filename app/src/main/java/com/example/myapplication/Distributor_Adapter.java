package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Distributor_Adapter extends BaseAdapter {
    private List<Distributor_Model> list;
    private Context context;
    private  EvenClick evenClick;
    public Distributor_Adapter(List<Distributor_Model> list, Context context, EvenClick evenClick) {
        this.list = list;
        this.context = context;
        this.evenClick = evenClick;
    }

    // Tạo interface để sử lý sự kiện bên màn hình main(Tránh việc gọi lại class httpRequest)
    public interface EvenClick{

        //Truyền vào 1 Distributor_Model đồ để sửa lý sự kiện
        void delete(Distributor_Model distributorModel);
        void update(Distributor_Model distributorModel);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         convertView = LayoutInflater.from(context).inflate(R.layout.item_distributor, parent, false);
        TextView tv_id, tv_Name;
        ImageView btn_delete;
        LinearLayout click_item_update;
        Distributor_Model distributorModel = list.get(position);
        tv_id = convertView.findViewById(R.id.tv_index);
        tv_Name = convertView.findViewById(R.id.tv_name);
        btn_delete = convertView.findViewById(R.id.btn_delete);
        click_item_update = convertView.findViewById(R.id.click_update);

        tv_id.setText(""+(position+1));
        tv_Name.setText(""+distributorModel.getName());


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi interface
                evenClick.delete(distributorModel);
            }
        });

        click_item_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evenClick.update(distributorModel);
            }
        });

        return convertView;
    }
}
