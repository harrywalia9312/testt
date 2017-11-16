package alcsoft.com.autobalance.features.shared.dialogs;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import alcsoft.com.autobalance.R;
import alcsoft.com.autobalance.features.shared.interfaces.ConfirmActionDialogListener;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * ConfirmActionDialog
 * This generic dialog uses specified Title and Message to build a generic yes/no
 * dialog, returns only the boolean values (true or false).
 *
 * @author ALCRamirez94
 * @version 1.0 (11/15/2017)
 */

public class ConfirmActionDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private ConfirmActionDialogListener confirmActionDialogListener;

    public static ConfirmActionDialog newInstance(String title, String message) {
        ConfirmActionDialog confirmActionDialog = new ConfirmActionDialog();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        confirmActionDialog.setArguments(args);

        return confirmActionDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getTargetFragment() instanceof ConfirmActionDialogListener) {
            confirmActionDialogListener = (ConfirmActionDialogListener) getTargetFragment();
        } else {
            throw new RuntimeException("Class must implement interface.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ConfirmDialogYesText, this);
        builder.setNegativeButton(R.string.ConfirmDialogNoText, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case BUTTON_POSITIVE:
                confirmActionDialogListener.onConfirmAction(true);
                break;

            case BUTTON_NEGATIVE:
                confirmActionDialogListener.onConfirmAction(false);
                break;
        }
    }

    @Override
    public void onDetach() {
        confirmActionDialogListener = null;
        super.onDetach();
    }
}
