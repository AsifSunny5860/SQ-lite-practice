package com.example.mysqlll;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


   public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<Expense> expenseList;
    Context context;
    DatabaseeHelper helper;
    Fragment fragment = new ExpenseFragment();

    public CustomAdapter(Context context,List<Expense> expenseList,DatabaseeHelper helper) {
        this.context=context;
        this.expenseList = expenseList;
        this.helper=helper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Expense currentExpense=expenseList.get(position);
        SimpleDateFormat dateSDF= new SimpleDateFormat("dd/MM/yyyy");

        Date date = new Date();
        date.setTime(currentExpense.getEdate());

        //Toast.makeText(context, (int) currentExpense.getEdate(), Toast.LENGTH_SHORT).show();

        holder.ExpenseTypeTV.setText(currentExpense.getEtype());

        holder.ExpenseDateTV.setText(dateSDF.format(date));

        holder.ExpenseAmountTV.setText(currentExpense.getEamount());


       holder.popUpMenuIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context,holder.popUpMenuIV);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.deletemenu:
                                deleteAlart(position);
                                break;
                            case R.id.updatemenu:
                                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });

            }
        });

    }

    ///////delete/////
    private void deleteAlart(final int i) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.delete_alertdialog,null);

        builder.setView(view);
        final Dialog dialog = builder.create();
        dialog.show();

        TextView deleteActin=view.findViewById(R.id.deleteAlartTv);
        TextView cancel =view.findViewById(R.id.cancelAlartTv);


        deleteActin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Expense currentExpense=expenseList.get(i);

                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                helper.deleteData(currentExpense.getId());
                expenseList.remove(i);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }



    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ExpenseTypeTV,ExpenseDateTV,ExpenseAmountTV;
        private ImageView popUpMenuIV ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ExpenseTypeTV = itemView.findViewById(R.id.typeTV);
            ExpenseDateTV = itemView.findViewById(R.id.dateTV);
            ExpenseAmountTV= itemView.findViewById(R.id.amountTV);
            popUpMenuIV = itemView.findViewById(R.id.popupmenuIV);


        }
    }
}
