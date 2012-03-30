package view;

import ca.ualberta.ca.c301.R;
import control.Controller;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * @author J-Tesseract
 * 
 * Password Activity takes needs a password from the user to allow access to the rest of the application.
 */
public class ChangePasswordActivity extends Activity  implements OnClickListener{

    /**
     * Variables created in onCreate and used depending on the view clicked
     */
    String password;
    EditText oldP;
    EditText newP1;
    EditText newP2;
    Button doneButton;
    Button cancelButton;
    TextView status;
    boolean knowOld;
    boolean newPassMatch;
    
    
    /**
     * On Create
     * 
     * Loads the layout of the change_password and create the buttons.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        password = Controller.getPassword();
        
        

        
        setContentView(R.layout.change_password_view);

        oldP = (EditText) findViewById(R.id.oldPasswordEditText);
        newP1 = (EditText) findViewById(R.id.newPasswordEditText1);
        newP2 = (EditText) findViewById(R.id.newPasswordEditText2);
        doneButton = (Button) findViewById(R.id.confirmPasswordButton);
        doneButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.canelPasswordChangeButton);
        cancelButton.setOnClickListener(this);
        status = (TextView) findViewById(R.id.passwordChangeStatusTextView);
        status.setText("");
        
    }
    
    
    /**
     * onClick
     * 
     * depending on the input of the user, onClick determines what to set as a status if there is incorrect input.
     * Or onClick finishes the Activity by canceling or changing the password
     * 
     * @param v View that has been clicked
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.confirmPasswordButton:
                knowOld = (oldP.getText().toString().equals(password));
                newPassMatch = (newP1.getText().toString().equals(newP2.getText().toString()));
                
                if(knowOld && !newPassMatch)
                    status.setText("Confirm Password didn't match");
                else if(!knowOld)
                    status.setText("Old Password Incorrect");
                else if(knowOld && newPassMatch){
                    Controller.setPassword(newP1.getText().toString());
                    Controller.savePassword(null);
                    finish();
                }
                
                
                break;

            case R.id.canelPasswordChangeButton:
                finish();
                break;

        }
    }

}
