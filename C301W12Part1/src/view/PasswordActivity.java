package view;

import org.omg.CORBA.INV_IDENT;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * @author J-Tesseract
 * 
 *         Password Activity takes needs a password from the user to allow access to the rest of the application.
 */
public class PasswordActivity extends Activity  implements OnClickListener{
    
    EditText passwordET;
    String password;
    TextView instructions;

    /**
     * On Create
     * 
     * Loads the layout of the password_enter. Sets the on click listener for the 
     * enter password button.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    
    public void onResume(){
        super.onResume();
        password = Controller.getPassword();
        

        
        setContentView(R.layout.password_enter);

        passwordET = (EditText) findViewById(R.id.enterPasswordEditText);

        passwordET.setText("");

        Button enterButton = (Button) findViewById(R.id.enterPasswordButton);
        instructions = (TextView) findViewById(R.id.enterPasswordTextView);
        enterButton.setOnClickListener(this);
        

    
        instructions.setText("Enter New Password");
        
        Button resetButton = (Button) findViewById(R.id.resetPasswordButton);
        resetButton.setOnClickListener(this);
        
        resetButton.setVisibility(-1);
        
        if(password != null){
            instructions.setText("Enter Password");
            resetButton.setVisibility(0);
        }
           
        
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.enterPasswordButton:
                Log.e(null, "HI");
                if(password == null){
                Controller.setPassword(PasswordActivity.this.passwordET.getText().toString());
                password = Controller.getPassword();
                Log.e(null,"password was null now is " + password);
                }
                
                if(passwordET.getText().toString().equals(password)){
                    Log.e(null,"entered password equals set password");
                finish();
                
                Intent welcomeIntent = new Intent(PasswordActivity.this.getApplicationContext(), WelcomeActivity.class);
                startActivity(welcomeIntent);
                }
                
                break;

            case R.id.resetPasswordButton:
                Intent changePasswordIntent = new Intent(PasswordActivity.this.getApplicationContext(), ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;

        }
    }
    
    
    

}
