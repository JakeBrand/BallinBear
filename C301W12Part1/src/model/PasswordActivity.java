package model;

import view.WelcomeActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;

import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Intent;

/**
 * @author J-Tesseract
 * 
 *         Password Activity takes needs a password from the user to allow
 *         access to the rest of the application.
 */
public class PasswordActivity extends Activity
{

    EditText passwordET;
    String   password;

    /**
     * On Create
     * 
     * Loads the layout of the password_enter. Sets the on click listener for
     * the enter password button.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        password = Controller.getPassword();

        setContentView(R.layout.password_enter);

        passwordET = (EditText) findViewById(R.id.enterPasswordEditText);

        passwordET.setText("");

        Button enterButton = (Button) findViewById(R.id.enterPasswordButton);
        OnClickListener enterListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if (passwordET.getText().toString() == password)
                {
                    finish();
                    Intent welcomeIntent = new Intent(
                            PasswordActivity.this.getApplicationContext(),
                            WelcomeActivity.class);
                }

            }

        };

    }

}
