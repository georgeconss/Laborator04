package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity {

    final public static int CONTACTS_MANAGER_REQUEST_CODE = 2022;

    private EditText phoneNumberEditText;

    private final ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            phoneNumberEditText = (EditText)findViewById(R.id.phone_number_edit_text);
            String phoneNumber = phoneNumberEditText.getText().toString();
            phoneNumber += ((Button)view).getText().toString();
            phoneNumberEditText.setText(phoneNumber);
        }
    }

    private final ImageButtonClickListener imageButtonClickListener = new ImageButtonClickListener();
    private class ImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int id = ((ImageButton)view).getId();
            if (id == R.id.backspace) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                if (phoneNumber.length() == 0) {
                    return;
                }
                phoneNumberEditText.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
            } else if (id == R.id.button_call) {
                if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            PhoneDialerActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            0
                    );
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                    startActivity(intent);
                }
            } else if (id == R.id.button_hangup) {
                finish();
            } else if (id == R.id.button_contacts) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                if (phoneNumber.length() > 0) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.lab04." +
                            "contactsmanager.intent.action.ContactsManagerActivity");
                    intent.putExtra("ro.pub.cs.systems.eim.lab04." +
                            "contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_dialer);
        phoneNumberEditText = (EditText)findViewById(R.id.phone_number_edit_text);

        GridLayout gridLayout = (GridLayout)findViewById(R.id.grid_layout);
        setAllButtonListener(gridLayout);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void setAllButtonListener(ViewGroup viewGroup) {
        int noChildren = viewGroup.getChildCount();
        for (int i = 0; i < noChildren; ++i) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(buttonClickListener);
            } else if (view instanceof ImageButton) {
                view.setOnClickListener(imageButtonClickListener);
            }
        }
    }
}