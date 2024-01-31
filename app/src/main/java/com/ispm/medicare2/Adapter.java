package com.ispm.medicare2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<com.ispm.medicare2.Medicine> list = new ArrayList<>();

    public Adapter(Context context) {
        this.context = context;
    }

    public void setItems(ArrayList<com.ispm.medicare2.Medicine> med) {
        list.addAll(med);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view, parent, false);
        return new com.ispm.medicare2.MedicineView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        com.ispm.medicare2.MedicineView mv = (com.ispm.medicare2.MedicineView) holder;
        com.ispm.medicare2.Medicine med = list.get(position);

        mv.medName.setText(med.getMedName());
        mv.medType.setText(med.getMedType());
        mv.medUse.setText(med.getMedUse());
        mv.medDose.setText(med.getMedDose());
        mv.medIntake.setText(med.getMedIntake());
        mv.medInfo.setText(med.getMedInfo());

        mv.option.setOnClickListener(op -> {
            PopupMenu popupMenu = new PopupMenu(context, mv.option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_remove) {
                    // Show the "Confirm Delete" menu item
                    item.getSubMenu().findItem(R.id.menu_confirm_delete).setVisible(true);
                    return true;
                } else if (itemId == R.id.menu_confirm_delete) {
                    // Execute the deletion logic here
                    com.ispm.medicare2.Action act = new com.ispm.medicare2.Action();
                    act.remove(med.getKey()).addOnSuccessListener(suc -> {
                        Toast.makeText(context, "REMOVED!", Toast.LENGTH_LONG).show();

                        // Remove the item from the list
                        list.remove(position);

                        // Notify the adapter about the removal
                        notifyItemRemoved(position);
                    }).addOnFailureListener(e -> Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show());

                    // Reset the visibility of the "Confirm Delete" menu item
                    item.getSubMenu().findItem(R.id.menu_confirm_delete).setVisible(false);
                    return true;
                } else {
                    // Handle other menu items if needed
                    return false;
                }
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}