package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private LinearLayout bottomContainer;
    private Button saveButton;
    private Button cancelButton;
    private Button additionalFieldsButton;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;

    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText imEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        bottomContainer = (LinearLayout)findViewById(R.id.bottom_container);
        additionalFieldsButton = (Button)findViewById(R.id.additional_fields_button);
        saveButton = (Button)findViewById(R.id.save_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        nameEditText = (EditText)findViewById(R.id.name_edit_text);
        phoneEditText = (EditText)findViewById(R.id.phone_number_edit_text);
        emailEditText = (EditText)findViewById(R.id.email_edit_text);
        addressEditText = (EditText) findViewById(R.id.address_edit_text);

        jobTitleEditText = (EditText) findViewById(R.id.job_edit_text);
        companyEditText = (EditText) findViewById(R.id.company_edit_text);
        websiteEditText = (EditText) findViewById(R.id.website_edit_text);
        imEditText = (EditText) findViewById(R.id.im_edit_text);

        additionalFieldsButton.setOnClickListener(buttonOnClickListener);
        saveButton.setOnClickListener(buttonOnClickListener);
        cancelButton.setOnClickListener(buttonOnClickListener);
    }

    final private ButtonOnClickListener buttonOnClickListener = new ButtonOnClickListener();

    private class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button buttonView = (Button)v;
            int id = buttonView.getId();
            if (id == R.id.additional_fields_button) {
                if (bottomContainer.getVisibility() == View.VISIBLE) {
                    bottomContainer.setVisibility(View.GONE);
                    buttonView.setText(getResources().getString(R.string.button_show));
                } else if (bottomContainer.getVisibility() == View.GONE) {
                    bottomContainer.setVisibility(View.VISIBLE);
                    buttonView.setText(getResources().getString(R.string.button_hide));
                }
            } else if (id == R.id.save_button) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String jobTitle = jobTitleEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = imEditText.getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name.length() > 0) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone.length() > 0) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email.length() > 0) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address.length() > 0) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle.length() > 0) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company.length() > 0) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<>();
                if (website.length() > 0) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, String.valueOf(website));
                    contactData.add(websiteRow);
                }
                if (im.length() > 0) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, String.valueOf(im));
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            } else if (id == R.id.cancel_button) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }
}