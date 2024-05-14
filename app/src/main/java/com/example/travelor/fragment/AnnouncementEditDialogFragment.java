package com.example.travelor.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.travelor.R;

public class AnnouncementEditDialogFragment extends DialogFragment {

    private View rootView;
    private EditText editText;

    // The activity that creates an instance of this dialog fragment must
    // implement this interface to receive event callbacks. Each method passes
    // the DialogFragment in case the host needs to query it.
    public interface AnnouncementEditDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String newAnnouncement);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events.
    AnnouncementEditDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the
    // NoticeDialogListener.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface.
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            listener = (AnnouncementEditDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface. Throw exception.
            throw new ClassCastException(listener.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_anouncement_edit, null);
        editText = rootView.findViewById(R.id.announcement_edit_text);
        // Inflate and set the layout for the dialog.
        // Pass null as the parent view because it's going in the dialog layout.
        builder.setView(rootView);
        builder.setTitle("修改公告")
                .setPositiveButton("确定", (dialog, id) -> {
                    // Send the positive button event back to the host activity.
                    listener.onDialogPositiveClick(AnnouncementEditDialogFragment.this, editText.getText().toString());
                })
                .setNegativeButton("取消", (dialog, id) -> {
                    // Send the negative button event back to the host activity.
                    listener.onDialogNegativeClick(AnnouncementEditDialogFragment.this);
                });
        return builder.create();
    }

}