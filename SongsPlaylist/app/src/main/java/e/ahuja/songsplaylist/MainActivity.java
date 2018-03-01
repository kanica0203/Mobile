package e.ahuja.songsplaylist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener {

    private ListView listView;

    ArrayList<String> Titles=new ArrayList<>();
    ArrayList<String> Artist= new ArrayList<>();
    ArrayList<String> uris= new ArrayList<>();
    ArrayList<String> WikepediaURL = new ArrayList<>();
    ArrayList<String> ArtistURL= new ArrayList<>();
    CustomAdapter customAdapter =new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialize();

        listView= (ListView)findViewById(R.id.list);


        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long it) {

                Intent intent = new Intent(getApplicationContext(),WebPage.class);

                intent.putExtra("url",uris.get(position)) ;
                intent.putExtra("title",Titles.get(position));
                startActivity(intent);
            }
        }) ;
        registerForContextMenu(listView);
    }

    public void Initialize(){
        Titles.add("Closer");
        Titles.add("We Don't Talk Anymore");
        Titles.add("Perfect");
        Titles.add("Meant To Be");
        Titles.add("Nazm Nazm");
        Artist.add("ChainSmoker");
        Artist.add("Charlie Puth");
        Artist.add("Ed Sheeren");
        Artist.add("Bebe Rehxa");
        Artist.add("Ayushman");
        uris.add("https://youtu.be/PT2_F-1esPk");
        uris.add("https://youtu.be/3AtDnEC4zak");
        uris.add("https://youtu.be/2Vv-BfVoq4g");
        uris.add("https://youtu.be/zDo0H8Fm7d0");
        uris.add("https://youtu.be/DK_UsATwoxI");
        WikepediaURL.add("https://en.wikipedia.org/wiki/Closer_(The_Chainsmokers_song)");
        WikepediaURL.add("https://en.wikipedia.org/wiki/We_Don%27t_Talk_Anymore_(Charlie_Puth_song)");
        WikepediaURL.add("https://en.wikipedia.org/wiki/Perfect_(Ed_Sheeran_song)");
        WikepediaURL.add("https://en.wikipedia.org/wiki/Meant_to_Be_(Bebe_Rexha_song)");
        WikepediaURL.add("https://en.wikipedia.org/wiki/Bareilly_Ki_Barfi");
        ArtistURL.add("https://en.wikipedia.org/wiki/The_Chainsmokers");
        ArtistURL.add("https://en.wikipedia.org/wiki/Charlie_Puth");
        ArtistURL.add("https://en.wikipedia.org/wiki/Ed_Sheeran");
        ArtistURL.add("https://en.wikipedia.org/wiki/Bebe_Rexha");
        ArtistURL.add("https://en.wikipedia.org/wiki/Ayushmann_Khurrana");

    }

    @Override
    public void addsongs(String song, String artist,String artisturl, String wikiurl, String videourl) {
        Titles.add(song);
        Artist.add(artist);
        ArtistURL.add(artisturl);
        WikepediaURL.add(wikiurl);
        uris.add(videourl);
        customAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.remove);

        SubMenu subMenu = menuItem.getSubMenu();
        for(int i =0 ;i<Titles.size();i++){
            subMenu.add(0,i,i,Titles.get(i));
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.findItem(R.id.remove);
        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.clear();
        for(int i =0 ;i<Titles.size();i++){
            subMenu.add(0,i,i,Titles.get(i));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                openDialog();
                break;
            case R.id.remove:
                break;
            case R.id.exit:
                finish();
                break;
            default:
                if(Titles.size()==1)
                    Toast.makeText(getApplicationContext(), "Can't Remove", Toast.LENGTH_SHORT).show();
                else {
                    Titles.remove(id);
                    ArtistURL.remove(id);
                    WikepediaURL.remove(id);
                    Artist.remove(id);
                    uris.remove(id);
                    customAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add(0, v.getId(), 0, "Video");
        menu.add(0, v.getId(), 0, "Description");
        menu.add(0, v.getId(), 0, "Artist");

    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index  = info.position;

        if(item.getTitle()=="Video"){
            Intent intent = new Intent(getApplicationContext(),WebPage.class);
            intent.putExtra("url",uris.get(index)) ;
            startActivity(intent);
        }

        if (item.getTitle()== "Description"){
            Intent intent = new Intent(getApplicationContext(),WebPage.class);
            intent.putExtra("url",WikepediaURL.get(index)) ;
            startActivity(intent);
        }

        if(item.getTitle()=="Artist"){
            Intent intent = new Intent(getApplicationContext(),WebPage.class);
            intent.putExtra("url",ArtistURL.get(index)) ;
            startActivity(intent);
        }

        return true;
    }



    public void openDialog(){
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(),"add dialog");
    }

    protected void onPause() {
        super.onPause() ;
        setResult(RESULT_OK) ;
    }



    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Titles.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.list_item,null);
            TextView title = (TextView)view.findViewById(R.id.title);
            TextView artist =(TextView)view.findViewById(R.id.artist);

            title.setText(Titles.get(i));
            artist.setText(Artist.get(i));
            return view;
        }
    }


}
