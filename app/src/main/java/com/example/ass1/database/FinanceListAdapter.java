package com.example.ass1.database;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ass1.R;

import java.util.List;
import java.util.Locale;

public class FinanceListAdapter   extends RecyclerView.Adapter<FinanceListAdapter.ViewHolder>
{
    private int financeItemLayout;

    private List<Finance> financeList;

    public FinanceListAdapter(int layoutId) {
        financeItemLayout = layoutId;
    }

    public void setFinanceList(List<Finance> finance) {
        financeList = finance;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return financeList == null ? 0 : financeList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(financeItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        TextView getTitle = holder.title;
        getTitle.setText(financeList.get(listPosition).getTitle());
        TextView getCategory = holder.category;
        getCategory.setText(financeList.get(listPosition).getCategory());
        TextView getAmount = holder.amount;
        getAmount.setText(String.format(Locale.US, "%d",
                financeList.get(listPosition).getAmount()));
        TextView getDate = holder.date;
        getDate.setText(financeList.get(listPosition).getDate());

        if (financeList.get(listPosition).getCategory().equals("Travel")) {
            holder.icon.setImageResource(R.drawable.ic_travel_foreground);

        }
        if (financeList.get(listPosition).getCategory().equals("Other")) {
            holder.icon.setImageResource(R.drawable.ic_other_foreground);
        }
        if (financeList.get(listPosition).getCategory().equals("Food")) {
            holder.icon.setImageResource(R.drawable.ic_food_foreground);
        }
        if (financeList.get(listPosition).getCategory().equals("Accommodation")) {
            holder.icon.setImageResource(R.drawable.ic_accomondation_foreground);
        }
        if (financeList.get(listPosition).getCategory().equals("Leisure")) {
            holder.icon.setImageResource(R.drawable.ic_leisure_foreground);
        }
        if (financeList.get(listPosition).getCategory().equals("Salary")) {
            holder.icon.setImageResource(R.drawable.ic_salary_foreground);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView category;
        TextView amount;
        TextView date;
        ImageView icon;
        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            category = itemView.findViewById(R.id.tv_category);
            amount = itemView.findViewById(R.id.tv_amount);
            date = itemView.findViewById(R.id.tv_date);
            icon =  itemView.findViewById(R.id.imgView);
        }
    }
}
