package view;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

import control.Controller;
import ca.ualberta.ca.c301.R;
import android.app.Activity;
import android.content.Intent;

/**
 * @author J-Tesseract
 * 
 * Password Activity takes needs a password from the user to allow access to the rest of the application.
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
     * 
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * onResume
     * 
     * Update the view and buttons
     */
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

    /**
     * onClick
     * 
     * Perfome action depending on clicked View
     * 
     * @param v The View that has been clicked
     */
    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.enterPasswordButton:
                if(password == null){
                Controller.setPassword(PasswordActivity.this.passwordET.getText().toString());
                Controller.savePassword(this);
                password = Controller.getPassword();
                Log.e(null,"password was null now is " + password);
                }
                
                if(passwordET.getText().toString().equals(password)){
                    Log.e(null,"entered password equals set password");
                finish();
                
                Intent welcomeIntent = new Intent(PasswordActivity.this.getApplicationContext(), WelcomeActivity.class);
                startActivity(welcomeIntent);
                }
                else
                {
                    int k = (int) (Math.random()*10);
                    TextView status = (TextView) findViewById(R.id.enterPasswordStatusTextView);
                    switch (k)
                    {
                        case 0:
                            status.setText("Wrong");
                            break;
                        case 1:
                            status.setText("Fail");
                            break;
                        case 2:
                            status.setText("No...FOOL!");
                            break;
                        case 3:
                            status.setText("Really?");
                            break;
                        case 4:
                            status.setText("Umm.. NO!");
                            break;
                        case 5:
                            status.setText("Come on!");
                            break;
                        case 6:
                            status.setText("You're making me sad");
                            break;
                        case 7:
                            status.setText("That is not the needed input");
                            break;
                        case 8:
                            status.setText("Deleting SDCard... Just kidding");
                            break;
                        case 9:
                            status.setText(":(");
                            break;
                        default:
                            status.setText("Incorrect Password");
                            break;
                    }
                }
                
                break;

            case R.id.resetPasswordButton:
                Intent changePasswordIntent = new Intent(PasswordActivity.this.getApplicationContext(), ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;

        }
    }
    
    
    

}
