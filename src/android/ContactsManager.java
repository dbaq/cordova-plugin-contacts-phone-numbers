package com.dbaq.cordova.contactsPhoneNumbers;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

public class ContactsManager extends CordovaPlugin {

    private CallbackContext callbackContext;
    
    private JSONArray executeArgs;
    
    public static final String ACTION_LIST_CONTACTS = "list";
    
    private static final String LOG_TAG = "Contact Phone Numbers";
    
    public ContactsManager() {}

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        
        this.callbackContext = callbackContext;
        this.executeArgs = args; 
        
        if (ACTION_LIST_CONTACTS.equals(action)) {
            this.cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    callbackContext.success(list());
                }
            });    
            return true;
        }
        
        return false;
    }
    
    private JSONArray list() {
	    JSONArray contacts = new JSONArray(); 
	    try {
		    ContentResolver cr = this.cordova.getActivity().getContentResolver();
		    String[] projection = new String[] { 
		    		ContactsContract.Contacts._ID,
		    		ContactsContract.Contacts.DISPLAY_NAME,
		    		ContactsContract.Contacts.HAS_PHONE_NUMBER};
		    // Retrieve only the contacts with a phone number at least
		    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, projection, 
		    		ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1", null,
		    		ContactsContract.Contacts._ID + " ASC");
		    
		    while (cursor.moveToNext()) {
		    	String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
		        //  Get all phone numbers for this contact
		        Cursor phonesCursor = cr.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + " = " + contactId, null, null);
		        JSONArray phones = new JSONArray();
		        while (phonesCursor.moveToNext()) {
		            int type = phonesCursor.getInt(phonesCursor.getColumnIndex(Phone.TYPE));
		            JSONObject phone = new JSONObject();
		            phone.put("number", phonesCursor.getString(phonesCursor.getColumnIndex(Phone.NUMBER)));
		            phone.put("normalizedNumber", phonesCursor.getString(phonesCursor.getColumnIndex(Phone.NORMALIZED_NUMBER)));
                    phone.put("type", getPhoneTypeLabel(phonesCursor.getInt(phonesCursor.getColumnIndex(Phone.TYPE))));
		            phones.put(phone);
		        }
		        phonesCursor.close();
		        
		        // Create the contact entry
		    	JSONObject contact = new JSONObject();
		        contact.put("id", contactId);
		        contact.put("displayName", cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
		        contact.put("phoneNumbers", phones);
		        contacts.put(contact);
		    }
		    cursor.close();
	    } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return contacts;
    }
    
    private String getPhoneTypeLabel(int type) {
    	String label = "OTHER";
    	if (type == Phone.TYPE_HOME)
    		label = "HOME";
    	else if (type == Phone.TYPE_MOBILE)
    		label = "MOBILE";
    	else if (type == Phone.TYPE_WORK)
    		label = "WORK";
    	
    	return label;
    }
}
