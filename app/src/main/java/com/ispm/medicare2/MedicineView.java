package com.ispm.medicare2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MedicineView extends RecyclerView.ViewHolder {

    TextView medName, medType, medUse, medDose, medIntake, medInfo, option;

    public MedicineView(View itemView) {
        super(itemView);
        medName = itemView.findViewById(R.id.medName);
        medType = itemView.findViewById(R.id.medType);
        medUse = itemView.findViewById(R.id.medUse);
        medDose = itemView.findViewById(R.id.medDose);
        medIntake = itemView.findViewById(R.id.medIntake);
        medInfo = itemView.findViewById(R.id.medInfo);
        option = itemView.findViewById(R.id.option);

    }

    public void bind(Medicine medicine) {
        medName.setText(medicine.getMedName());
        medType.setText(medicine.getMedType());
        medUse.setText(medicine.getMedUse());
        medDose.setText(medicine.getMedDose());
        medIntake.setText(medicine.getMedIntake());
        medInfo.setText(medicine.getMedInfo());
    }
}
