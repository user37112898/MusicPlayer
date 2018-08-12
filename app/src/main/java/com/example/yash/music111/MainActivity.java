package com.example.yash.music111;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.path;
import static android.R.attr.x;
import static android.R.attr.y;
import static android.os.Build.VERSION_CODES.M;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    ArrayList music;
    Cursor c;
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    MediaPlayer Mp = new MediaPlayer();
    boolean paused[]= new boolean[1000];
    boolean stopped[]= new boolean[1000];
    String path;
    boolean playing[] = new boolean[1000];
    int temp;
 //   private MediaPlayer mMp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView =(ListView) findViewById(R.id.list);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            showMusic();
        }
        else {
            //TODO
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,music);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(playing[position]) {
                    Mp.stop();
                }
                stopped[position]=true;
                playing[position]=false;
                Toast.makeText(MainActivity.this,"Stopped",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long z){
                c = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
                c.moveToPosition(position);
                path= c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));

               if(!playing[position]) {
                   if(temp!=position || stopped[position]==true ){
                       playing[temp]=false;
                       stopped[position]=false;
                   temp=position;
                        try {
                            Mp.reset();
                            Mp.setDataSource(path);
                            Mp.prepare();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
               }
                    Mp.start();
                    playing[position] = true;
                    Toast.makeText(MainActivity.this,"Played",Toast.LENGTH_SHORT).show();
                }else{
                    Mp.pause();
                    temp=position;
                    Toast.makeText(MainActivity.this,"Paused",Toast.LENGTH_SHORT).show();
                    playing[position]=false;
                   paused[position]=false;
                   temp=position;

                }
//                //   Toast.makeText(MainActivity.this,"ITEM_CLICKED:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showMusic();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void showMusic(){

        c = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);

            music = new ArrayList();
//        final ArrayList music = new ArrayList();
//        MediaPlayer Mp= new MediaPlayer();

        while (c.moveToNext()){
            String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//            path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
            music.add("Title: "+title + "\n" + "Artist:"  + artist);
//            Mp.start();
        }
        c.close();
    }

}




//import android.Manifest;
//        import android.content.pm.PackageManager;
//        import android.database.Cursor;
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.provider.ContactsContract;
//        import android.support.v4.app.ActivityCompat;
//        import android.support.v4.content.ContextCompat;
//        import android.support.v4.widget.SimpleCursorAdapter;
//        import android.support.v7.app.AppCompatActivity;
//        import android.widget.ArrayAdapter;
//        import android.widget.ListView;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//import com.example.yash.music111.R;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    ListView listView;
//    TextView textView;
//    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
//    Cursor c;
//    ArrayList contacts;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ListView listView = (ListView) findViewById(R.id.list);
//
//
//
//        //Method to Start the Service
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
//
//        if (permissionCheck == PackageManager.PERMISSION_GRANTED ) {
//            //Name of Method for Calling Message
//            showContacts();
//
//        } else {
//            //TODO
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS},
//                    PERMISSIONS_REQUEST_READ_CONTACTS);
//        }
//
//
//        ArrayAdapter adapter = new ArrayAdapter(
//                this, android.R.layout.simple_list_item_1, contacts);
//
//        listView.setAdapter(adapter);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission is granted
//                showContacts();
//            } else {
//                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void showContacts() {
//        c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
//
//        contacts = new ArrayList();
//        while (c.moveToNext()) {
//
//            String contactName = c.getString(c.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ));
//            String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            contacts.add("Name: "+contactName + "\n"+ "PhoneNo: " + phNumber);
//
//            //Toast.makeText(this,"COntacts: "+contacts,Toast.LENGTH_SHORT).show();
//        }
//        c.close();
//    }
//
//}
