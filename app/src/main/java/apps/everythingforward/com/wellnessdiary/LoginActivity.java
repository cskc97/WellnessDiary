package apps.everythingforward.com.wellnessdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

public class LoginActivity extends AppCompatActivity {


    public static final int LOGIN_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ParseUser.logOut();




    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseLoginBuilder builder = new ParseLoginBuilder(LoginActivity.this);
        startActivityForResult(builder.build(), LOGIN_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {

            case LOGIN_CODE:
            {
                if(ParseUser.getCurrentUser()!=null) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                }
                else
                {
                    Toast.makeText(this, "Please Login or register", Toast.LENGTH_SHORT).show();
                }

            }
            default:
            {
                finish();
            }


        }







    }
}
