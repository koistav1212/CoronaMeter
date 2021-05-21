package com.example.travellingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
Button login,go;
TextInputLayout regname,regusername,regpassword,regemail,regphn_no;
FirebaseDatabase rootnode;
DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_sign_up);
        login=findViewById(R.id.login);
        go=findViewById(R.id.go_btn2);
        regname=findViewById(R.id.name);
        regusername=findViewById(R.id.username);
        regemail=findViewById(R.id.email);
        regpassword=findViewById(R.id.password);
        regphn_no=findViewById(R.id.phn_no);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(SignUp.this,Login.class);

                startActivity(intent);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            rootnode=FirebaseDatabase.getInstance();

            reference=rootnode.getReference("users");
        registerUser(v);
            }
        });
    }
    public void registerUser(View view)
    {
        if(!validateemail()| !validateName()|!validatepass()|!validatephnno()|!validateUsername())
        {
            return;
        }
        String name=regname.getEditText().getText().toString();
        String username=regusername.getEditText().getText().toString();
        String email=regemail.getEditText().getText().toString();
        String password=regpassword.getEditText().getText().toString();
        String phn_no=regphn_no.getEditText().getText().toString();
       //
        Intent intent=new Intent(getApplicationContext(),otp.class);
        intent.putExtra("phone_no",phn_no);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        intent.putExtra("username",username);

        intent.putExtra("email",email);

        startActivity(intent);

    }
private boolean validateName()
{
    String val=regname.getEditText().getText().toString();
    if(val.isEmpty())
    {
        regname.setError("Field can not be Empty");
        return false;
    }
    else
    { regname.setError(null);
    regname.setErrorEnabled(false);
    return  true;

    }
    }
    private boolean validateUsername()
    {
        String val=regusername.getEditText().getText().toString();
        String nowhite= "(^[a-zA-Z\\d]*$)";
        if(val.isEmpty())
        {
            regusername.setError("Field can not be Empty");
            return false;
        }
        else if(val.length()>=15)
        {
            regusername.setError("Username length too long.");
            return false;

        }
        else if(!val.matches(nowhite))
        {
            regusername.setError("White spaces are not allowed");

            return false;

        }


        else
        { regusername.setError(null);
            regusername.setErrorEnabled(false);
            return  true;

        }
    }
    private boolean validateemail()
    {
        String valemail=regemail.getEditText().getText().toString();
        String noemail="[a-z0-9._+]+@[a-z]+\\.+[a-z]+";
        if(valemail.isEmpty())
        {
            regemail.setError("Field can not be Empty");
            return false;
        }
        else if(!valemail.matches(noemail))
        {
            regemail.setError("Invalid Email address");
            return false;
        }
        else
        { regemail.setError(null);
            regemail.setErrorEnabled(false);
            return  true;

        }
    }
    private boolean validatephnno() {
        String val=regphn_no.getEditText().getText().toString();
        if(val.isEmpty())
        {
            regphn_no.setError("Field can not be Empty");
            return false;
        }
        else
        { regphn_no.setError(null);
            regphn_no.setErrorEnabled(false);
            return  true;

        }
    }
    private boolean validatepass()
    {
        String val=regpassword.getEditText().getText().toString();
        String pass="^"+"(?=.*[a-zA-Z])"+
                "(?=.*[@#&*$+=?])"
           +
                ".{4,}"+"$";
        if(val.isEmpty())
        {
            regpassword.setError("Field can not be Empty");
            return false;
        }
        else if(!val.matches(pass))
        {
            regpassword.setError("Pass word must be 4 characters long, and contain at least one special character.");
            return false;
        }
        else
        { regpassword.setError(null);
            regpassword.setErrorEnabled(false);
            return  true;

        }
    }

}
