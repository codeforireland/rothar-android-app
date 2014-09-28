package eu.appbucket.rothar.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NetworkProblemRetryDialogFragment extends DialogFragment {
	
	private String message;
	
	public NetworkProblemRetryDialogFragment(String message) {
		this.message = message;
	}
	
	private NetworkProblemRetryDialogListener mListener;
	// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NetworkProblemRetryDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NetworkProblemRetryDialogFragment");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mListener.onDialogPositiveClick(NetworkProblemRetryDialogFragment.this);
                   }
               });
        return builder.create();
    }
    
    public interface NetworkProblemRetryDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }
}
