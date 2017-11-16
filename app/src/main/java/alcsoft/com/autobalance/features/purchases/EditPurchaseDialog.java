package alcsoft.com.autobalance.features.purchases;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import alcsoft.com.autobalance.R;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * @author ALCRamirez94
 * @version 1.0 (11/15/2017)
 */

public class EditPurchaseDialog extends android.support.v4.app.DialogFragment implements DialogInterface.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private EditPurchaseDialogListener editPurchaseDialogListener;
    private EditText editPurchaseName;
    private EditText editPurchaseAmt;
    private EditText editPurchaseDate;
    private Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getTargetFragment() instanceof EditPurchaseDialogListener) {
            editPurchaseDialogListener = (EditPurchaseDialogListener) getTargetFragment();
        } else {
            throw new RuntimeException("Class must implement interface.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_editpurchase, null, false);
        calendar = Calendar.getInstance();
        editPurchaseName = (EditText) view.findViewById(R.id.dialog_editpurchasenameinput);
        editPurchaseDate = (EditText) view.findViewById(R.id.dialog_editpurchasedateinput);
        editPurchaseAmt = (EditText) view.findViewById(R.id.dialog_editpurchaseamtinput);
        editPurchaseDate.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("Edit Purchase");
        builder.setPositiveButton("Save", this);
        builder.setNeutralButton("Delete", this);
        builder.setNegativeButton("Cancel", this);
        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case BUTTON_POSITIVE:
                editPurchaseDialogListener.onPurchaseEdit(editPurchaseName.getText().toString(), editPurchaseDate.getText().toString(), editPurchaseAmt.getText().toString());
                break;
            case BUTTON_NEUTRAL:
                editPurchaseDialogListener.onPurchaseRemoval();
                break;
            case BUTTON_NEGATIVE:
                Toast.makeText(getActivity(), R.string.PurchaseCancelAction, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateText();
    }

    private void updateText() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        editPurchaseDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        new DatePickerDialog(getTargetFragment().getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
