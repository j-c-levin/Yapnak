package com.uq.yapnak;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by vahizan on 11/07/2015.
 */
public class ContactListFragment extends Activity implements LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemClickListener {


  //ListView inflateListView
  //  public ContactListFragment(){
        //this.contactList = inflateListView;
    //Fragment
    //}

    public ContactListFragment(){}

    private ContactsContract contacts;

    private final String[] columns = {Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME};

    private final int[] id ={android.R.id.text1};

    private ListView contactList;

    private SimpleCursorAdapter adapter;

    private String contactKey;

    private long contactID;

    private Uri contactURI;

    private final String[]PROJECTION = {ContactsContract.Contacts._ID,
                                          ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME : ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                                          };
    private static final int CONTACT_ID_INDEX = 0;
    private static final int CONTACT_KEY = 1;

    private final String SELECTION =Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? (ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?") : (ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?");

    private String searchString;

    private String [] names={searchString} ;

    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.contacts_list_fragment,container,false);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.contacts_list_fragment);

        populateList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /*
        //Do SOMETHING - OPEN UP Contact INFO

        Cursor cursor = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();

        //GO TO SELECTED LIST ITEM/CONTACT
        cursor.moveToPosition(position);

        //get id in position 0
        contactID = cursor.getLong(CONTACT_ID_INDEX);

        //get key in position 1
        contactKey = cursor.getString(CONTACT_KEY);

        contactURI = ContactsContract.Contacts.getLookupUri(contactID,contactKey);

        */



    }

    public void populateList(){

        ListView contactList = (ListView) findViewById(R.id.contactList);

        ListAdapter listAdapter = new ContactListAdapter(this,R.layout.contactlist_item,getContactName());

        contactList.setAdapter(listAdapter);
       /* adapter = new SimpleCursorAdapter(
                this,
                R.layout.contactlist_item,
                null,
                columns,
                id,0
        );

        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(this);

        getLoaderManager().initLoader(0,null,this);
        */



    }

    private ContentResolver contentResolver;
    private Cursor c;
    private Person[] details;
    private String [] displayNameArray;

    public String [] getContactName(){

        contentResolver = getContentResolver();

        c = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        displayNameArray = new String[c.getCount()];



        c.moveToFirst();

        boolean hasNumber;
        String correctDisplayName = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME : ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        int i=0;


        if(!c.moveToFirst()){

            Toast.makeText(getApplicationContext(),"No Contacts - YOU LOSER!",Toast.LENGTH_LONG).show();

            String [] empty = new String[1];
            empty[0]= "Unavailable";

            return empty;
        }
        while(c.moveToNext()) {

            String displayName = c.getString(c.getColumnIndexOrThrow(correctDisplayName));


            displayNameArray[i] = displayName;

            i++;

        }

        c.close();
        return displayNameArray;

    }

    public Person[] getContacts(){


        contentResolver = getContentResolver();

        c= contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        //c.moveToFirst();

        details = new Person[c.getCount()];


        boolean hasNumber;
        String correctDisplayName = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME : ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        int i=0;
        int j =0;

        c.moveToFirst();
        while(c.moveToNext()){

           String displayName =  c.getString(c.getColumnIndexOrThrow(correctDisplayName));
           String userid = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            Person temp = new Person();

            temp.setId(userid);
            temp.setDisplayName(displayName);

            String [] phoneNumber = new String[5];
            String [] email = new String[5];


            //Query - find PhoneNumber/s of a contact with particular userID
                hasNumber = c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)>0;
            if(hasNumber){

                Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",new String[]{userid},null);
                 phoneCursor.moveToFirst();
                while(phoneCursor.moveToNext()){

                    String phoneNum = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneNumber[j] = phoneNum;
                    j++;
                }
            }

            //Query - find PhoneNumber/s of a contact with particular userID



            //Query - find email of a contact with particular userID

            Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + "= ?" ,new String[]{userid},null);

                emailCursor.moveToFirst();

                int emailAd = 0;
            while(emailCursor.moveToNext()){

                String getEmail = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA));

                email[emailAd] = getEmail;

                emailAd++;
            }

            emailCursor.close();
            //Query - find email of a contact with particular userID


            //Query - find picture of a contact with particular userID

            Cursor pictureCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,null,ContactsContract.Data.CONTACT_ID + "= ? AND " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", new String[]{userid},null);


            pictureCursor.moveToFirst();
            while(pictureCursor.moveToNext()){

                long id = Long.parseLong(userid);
                Uri contact = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);

                Uri photoUri = Uri.withAppendedPath(contact, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                temp.setPictureURI(photoUri);


            }

            //Query - find picture of a contact with particular userID

            pictureCursor.close();


            //Added all QUERY RESULTS TO Person Instance
            temp.setEmail(email);
            temp.setPhoneNumber(phoneNumber);
            details[i] = temp;

            i++;
        }

        c.close();
        return details;

    }

    public Bitmap getPhotoBitmap(Uri photoUri){

        try {
            InputStream stream = getContentResolver().openInputStream(photoUri);
            if(stream!=null){

                return BitmapFactory.decodeStream(stream);
            }

            return null;

        }catch(IOException e){

            return null;
        }
    }

    public class Person{

        private String  displayName;
        private String  id;
        private String [] phoneNumber;
        private String [] email;
        private String displayPictureURI;
        private Uri pictureURI;



        public String getDisplayName() {
            return displayName;
        }

        private void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getId() {
            return id;
        }

        private void setId(String id) {
            this.id = id;
        }

        public String[] getPhoneNumber() {
            return phoneNumber;
        }

        private void setPhoneNumber(String[] phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String[] getEmail() {
            return email;
        }

        private void setEmail(String[] email) {
            this.email = email;
        }

        public String getDisplayPictureURI() {
            return displayPictureURI;
        }

        private void setDisplayPictureURI(String displayPictureURI) {
            this.displayPictureURI = displayPictureURI;
        }

        public Uri getPictureURI() {
            return pictureURI;
        }

        private void setPictureURI(Uri pictureURI) {
            this.pictureURI = pictureURI;
        }
    }

    /*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ListView contactList = (ListView) findViewById(R.id.contactList);

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.contactlist_item,
                null,
                columns,
                id,0
        );

        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(this);

        getLoaderManager().initLoader(0,null,this);
    }
    */



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        names[0] = "% " + searchString + " % " ;

        //START QUERY

        CursorLoader cursorLoader = new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                names,
                null
        );



        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //Put results of Cursor in  SimpleCursorAdapter, which then automatically updates search results in the list view
        adapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //RESET happens when search results from curson happens to be invalid/stale
        adapter.swapCursor(null);

    }



}