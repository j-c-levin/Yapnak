package com.uq.yapnak;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by vahizan on 11/07/2015.
 */
public class ContactList extends Activity implements LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemClickListener {


  //ListView inflateListView
  //  public ContactListFragment(){
        //this.contactList = inflateListView;
    //Fragment
    //}

    public ContactList(){}

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

        ContactMain temp = (ContactMain)parent.getItemAtPosition(position);

        Person[] details = getAllContactDetails();

        Toast.makeText(getApplicationContext(),"Here is Contact's Phone Number " + details[0].getPhoneNumber(),Toast.LENGTH_SHORT).show();






    }

    public void populateList(){
        ListView contactList = (ListView) findViewById(R.id.contactList);
        ListAdapter listAdapter = new ContactListAdapter(this,R.layout.contactlist_item,getContactName());
        contactList.setAdapter(listAdapter);
        contactList.setOnItemClickListener(this);
        Log.d("contactsLength",(getContactName().length)+"");


       /* adapter = new SimpleCursorAdapter(
                this,
                R.layout.contactlist_item,
                null,
                columns,
                id,0
        );

        contactList.setAdapter(adapter);



        getLoaderManager().initLoader(0,null,this);
        */



    }

    private ContentResolver contentResolver;
    private Cursor c;
    private Person[] details;
    private String [] displayNameArray;



    public ContactMain [] getContactName(){
        contentResolver = getContentResolver();
        c = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,ContactsContract.Contacts.HAS_PHONE_NUMBER,null,null);
        int count  = c.getCount();
        ArrayList<ContactMain> contactName = new ArrayList<>();
        c.moveToFirst();

        String displayName = (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)? ContactsContract.Contacts.DISPLAY_NAME: ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        while(c.moveToNext()){

        String name = c.getString(c.getColumnIndexOrThrow(displayName));
        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            ContactMain mainC = new ContactMain();
            mainC.setContactName(name);
            mainC.setId(id);
            contactName.add(mainC);
        }

        return contactName.toArray(new ContactMain[contactName.size()]);

    }


    public Person getContactDetails(String ID){
        contentResolver = getContentResolver();
        c= contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,ContactsContract.Contacts._ID + "= "+ID ,null,null);
        //c.moveToFirst();
        ArrayList<Person> contactList = new ArrayList<>();


        boolean hasNumber;
        String correctDisplayName = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME : ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        int i=0;
        int j=0;

        Person temp = new Person();
        c.moveToFirst();
        while(c.moveToNext()){

            String displayName =  c.getString(c.getColumnIndexOrThrow(correctDisplayName));
            String userid = ID;



            temp.setId(userid);
            temp.setDisplayName(displayName);

            String [] phoneNumber;
            ArrayList<String> phoneNos = new ArrayList<>();

            String [] email;
            ArrayList<String> emails = new ArrayList<>();


            //Query - find PhoneNumber/s of a contact with particular userID

            Cursor phoneCursor;


                phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= " + ID ,null,null);
                phoneCursor.moveToFirst();

                while(phoneCursor.moveToNext()){
                    String phoneNum = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("phone",phoneNum);
                    phoneNos.add(phoneNum);
                    j++;
                }


                phoneNumber = phoneNos.toArray(new String[phoneCursor.getCount()]);
                temp.setPhoneNumber(phoneNumber);
                phoneCursor.close();


            //Query - find PhoneNumber/s of a contact with particular userID



            //Query - find email of a contact with particular userID

            Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + "= " + ID,new String[]{userid},null);
            emailCursor.moveToFirst();
            int emailAd = 0;

            while(emailCursor.moveToNext()){

                String getEmail = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA));
                emails.add(getEmail);
                emailAd++;
            }
            email = emails.toArray(new String[emailCursor.getCount()]);
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
            //contactList.add(temp);

            i++;
        }

       // details = contactList.toArray(new Person[c.getCount()]);

        c.close();
        return temp;

    }


    public Person[] getAllContactDetails(){
        contentResolver = getContentResolver();
        c= contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //c.moveToFirst();
        ArrayList<Person> contactList = new ArrayList<>();


        boolean hasNumber;
        String correctDisplayName = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME : ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        int i=0;
        int j=0;

        c.moveToFirst();
        while(c.moveToNext()){

           String displayName =  c.getString(c.getColumnIndexOrThrow(correctDisplayName));
           String userid = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            Person temp = new Person();

            temp.setId(userid);
            temp.setDisplayName(displayName);

            String [] phoneNumber;
            ArrayList<String> phoneNos = new ArrayList<>();

            String [] email;
            ArrayList<String> emails = new ArrayList<>();


            //Query - find PhoneNumber/s of a contact with particular userID
            hasNumber = c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)>0;
            Cursor phoneCursor;

            if(hasNumber){
                 phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +userid,null,null);
                 phoneCursor.moveToFirst();

                while(phoneCursor.moveToNext()){
                    String phoneNum = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("phone",phoneNum);
                    phoneNos.add(phoneNum);
                    j++;
                }


                phoneNumber = phoneNos.toArray(new String[phoneNos.size()]);
                temp.setPhoneNumber(phoneNumber);
                phoneCursor.close();
            }

            //Query - find PhoneNumber/s of a contact with particular userID



            //Query - find email of a contact with particular userID

            Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?" ,new String[]{userid},null);

            int emailAd = 0;
            //emailCursor.moveToFirst();
            while(emailCursor.moveToNext()){

                String getEmail = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                emails.add(getEmail);
                emailAd++;
            }
            email = emails.toArray(new String[emails.size()]);
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
            contactList.add(temp);

            i++;
        }

        details = contactList.toArray(new Person[contactList.size()]);

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