package adapters;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.sparks.medic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CardsAdapter extends BaseAdapter {

    private List<String> items;
    private String[] doctors_list;
    private final OnClickListener itemButtonClickListener;
    private final Context context;

    public CardsAdapter(Context context, List<String> items, OnClickListener itemButtonClickListener) {
        this.context = context;
        this.items = items;
        doctors_list = context.getResources().getStringArray(R.array.doctor_list);	
        this.itemButtonClickListener = itemButtonClickListener;
    }

    
    private int randNum(int num)
    {
    	return new Random().nextInt(num);
    }
    
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_card, null);

            holder = new ViewHolder();
            holder.itemText = (TextView) convertView.findViewById(R.id.card_main_inner_simple_title);
            holder.dateText = (TextView) convertView.findViewById(R.id.card_main_inner_secondary_title);
            holder.itemButton1 = (Button) convertView.findViewById(R.id.list_item_card_button_1);
            holder.itemButton2 = (Button) convertView.findViewById(R.id.list_item_card_button_2);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.itemText.setText(doctors_list[randNum(position==0||position>doctors_list.length?1:position)]);
        
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        holder.dateText.setText(mydate);

        if (itemButtonClickListener != null) {
            holder.itemButton1.setOnClickListener(itemButtonClickListener);
            holder.itemButton2.setOnClickListener(itemButtonClickListener);
        }
        
        return convertView;
    }

    private static class ViewHolder {
        private TextView itemText;
        private TextView dateText;
        private Button itemButton1;
        private Button itemButton2;
    }

}
