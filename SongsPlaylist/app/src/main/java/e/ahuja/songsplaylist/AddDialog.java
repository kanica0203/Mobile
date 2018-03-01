package e.ahuja.songsplaylist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ahuja on 2/25/2018.
 */

public class AddDialog extends AppCompatDialogFragment {
    EditText Title;
    EditText Artist;
    EditText ArtistURL;
    EditText WikiUrl;
    EditText VideoUrl;
    Button Add;
    private AddDialogListener listener;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstance){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add,null);
        Title = (EditText) view.findViewById(R.id.Songtitle);
        Artist=(EditText) view.findViewById(R.id.ArtistName);
        ArtistURL=(EditText) view.findViewById(R.id.ArtistURL);
        WikiUrl=(EditText) view.findViewById(R.id.WikiURL);
        VideoUrl=(EditText) view.findViewById(R.id.VideoURL);

        builder.setView(view).setTitle("Add Songs")
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = Title.getText().toString();
                        String artist = Artist.getText().toString();
                        String artisturl = ArtistURL.getText().toString();
                        String wikiurl = Artist.getText().toString();
                        String videourl = VideoUrl.getText().toString();
                        listener.addsongs(title,artist,artisturl,wikiurl,videourl);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddDialogListener)context;
        } catch (ClassCastException e) {
            throw new  ClassCastException(context.toString()+"must implement AddDialogListener");
        }
    }

    public interface AddDialogListener{
        void addsongs(String title, String song, String artist, String wikiurl, String videourl);
    }
}
