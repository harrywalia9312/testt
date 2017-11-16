package alcsoft.com.autobalance.features.shared.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import alcsoft.com.autobalance.R;
import alcsoft.com.autobalance.features.shared.interfaces.AddPurchaseDialogListener;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * AddPurchaseDialog
 * This generic dialog handles the user input on adding a purchase.
 *
 * @author ALCRamirez94
 * @version 1.0 (11/14/2017)
 */

public class AddPurchaseDialog extends DialogFragment implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    private AddPurchaseDialogListener addPurchaseDialogListener;
    private EditText purchaseName;
    private EditText purchaseAmt;
    private View dialogView;

    public static AddPurchaseDialog newInstance(String title) {
        AddPurchaseDialog addPurchaseDialog = new AddPurchaseDialog();

        Bundle args = new Bundle();
        args.putString("title", title);
        addPurchaseDialog.setArguments(args);

        return addPurchaseDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getTargetFragment() instanceof AddPurchaseDialogListener) {
            addPurchaseDialogListener = (AddPurchaseDialogListener) getTargetFragment();
        } else {
            throw new RuntimeException("Class must implement Interface");
        }
    }

    @Override
    public void onDetach() {
        addPurchaseDialogListener = null;
        purchaseName = null;
        purchaseAmt = null;
        dialogView = null;
        super.onDetach();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_addpurchase, null, false);
        purchaseName = (EditText) dialogView.findViewById(R.id.dialog_purchasenameinput);
        purchaseAmt = (EditText) dialogView.findViewById(R.id.dialog_purchaseamtinput);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(dialogView);
        if (getArguments() == null) {
            alertDialog.setTitle("Add Purchase");
        } else {
            alertDialog.setTitle(getArguments().getString("title"));
        }
        alertDialog.setMessage("Enter a name for the purchase and the amount of the purchase.");
        alertDialog.setPositiveButton("Add Purchase", this);
        alertDialog.setNegativeButton("Cancel", this);
        return alertDialog.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case BUTTON_POSITIVE:
                if (!purchaseAmt.getText().toString().isEmpty()) {
                    if (Float.valueOf(purchaseAmt.getText().toString()) <= 99999999.99f) {
                        addPurchaseDialogListener.onDialogAddPurchase(purchaseName.getText().toString(), purchaseAmt.getText().toString());
                        Toast.makeText(getActivity(), R.string.PurchaseAddSuccess, Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    } else {
                        addPurchaseDialogListener.onDialogInvalidInput(4);
                        return;
                    }
                } else {
                    addPurchaseDialogListener.onDialogInvalidInput(5);
                    return;
                }
                break;

            case BUTTON_NEGATIVE:
                // DO nothing
                break;
        }
    }
}
