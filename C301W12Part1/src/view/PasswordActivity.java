package view;

import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
		try {
			Controller.loadPassword(this.getApplicationContext());
		} catch (Exception e) {
			
		}
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
        enterButton.setBackgroundResource(R.drawable.accept);
        enterButton.setOnClickListener(this);

        instructions = (TextView) findViewById(R.id.enterPasswordTextView);
        instructions.setText("Enter New Password");
        
        Button resetButton = (Button) findViewById(R.id.resetPasswordButton);
        resetButton.setBackgroundResource(R.drawable.edit_password);
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
     * Perform action depending on clicked View
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
                	inflatePasswordCheckDialog();
                }
                
                else if(passwordET.getText().toString().equals(password)){
                finish();
               
                Intent welcomeIntent = new Intent(PasswordActivity.this.getApplicationContext(), WelcomeActivity.class);
                startActivity(welcomeIntent);
                }
                else
                {
                    setErrorText();
                }
                
                break;

            case R.id.resetPasswordButton:
                Intent changePasswordIntent = new Intent(PasswordActivity.this.getApplicationContext(), ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;

        }
    }
    
    
    /**
     * inflatePasswordCheckDialog
     * 
     * Inflates a dialog to confirm the password you entered previously
     * 
     * If the password entered in the dialog does not match you will be asked to confirm again.
     * If the password does match the previously provided password you will be entered into the application
     */
    private void inflatePasswordCheckDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm Password");
        alert.setMessage("Please re-enter your password confirm your password");
        
        final EditText input = new EditText(this);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        alert.setView(input);
        
        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {

                String confirmPassword = input.getText().toString();
                
                if (!confirmPassword.equals(passwordET.getText().toString())){
                	Toast toast = Toast
                    .makeText(
                            getApplicationContext(),
                            "Passwords do not match try again",
                            Toast.LENGTH_SHORT);
                	toast.show();
                	inflatePasswordCheckDialog();
                }
                else
                {
                	goodPassword();
                	finish();
                	Intent welcomeIntent = new Intent(PasswordActivity.this.getApplicationContext(), WelcomeActivity.class);
                    startActivity(welcomeIntent);
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            public void onClick(DialogInterface dialog, int whichButton)
            {
            	
            }
        });
        alert.show();
    }
    /**
     * goodPassword
     * 
     * Sets and saves the password using the controller
     */
    private void goodPassword(){
    	Controller.setPassword(PasswordActivity.this.passwordET.getText().toString());
        Controller.savePassword(this);
        password = Controller.getPassword();        
    }
    
    private void setErrorText(){
        
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
    

}
