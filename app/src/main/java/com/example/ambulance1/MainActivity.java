package com.example.ambulance1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.ambulance1.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn,btnRegister;
    FirebaseAuth auth;
    FirebaseAuth mAuth;
    //PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout rootLayout;
    String codeSent;
    MaterialEditText otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        rootLayout=findViewById(R.id.rootLayout);
        users=db.getReference("Users");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogInDialog();
            }
        });
    }

    private void showLogInDialog() {
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("LOGIN");
        dialog.setMessage("Please Use E-mail To Sign In");
        LayoutInflater inflater=LayoutInflater.from(this);
        View login_layout=inflater.inflate(R.layout.login,null);
        final MaterialEditText email=login_layout.findViewById(R.id.email);
        final MaterialEditText password=login_layout.findViewById(R.id.password);
        //final MaterialEditText name=login_layout.findViewById(R.id.name);
        //final MaterialEditText phone_number=login_layout.findViewById(R.id.phone_number);
        dialog.setView(login_layout);
        dialog.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //5654646654543
                 btnSignIn.setEnabled(false);
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(rootLayout, "Please enter Email address", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    Snackbar.make(rootLayout, "Please enter Password", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                final  android.app.AlertDialog waitingDialog= new SpotsDialog.Builder()
                        .setContext(MainActivity.this)
                        .setMessage("Please wait")
                        .setCancelable(false)
                        .build();
               // waitingDialog.dismiss();

                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, Welcome.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(rootLayout, "Failed! Retry", Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");
        dialog.setMessage("Please Use E-mail To Register");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View register_layout=inflater.inflate(R.layout.register,null);
        final MaterialEditText email=register_layout.findViewById(R.id.email);
        final MaterialEditText password=register_layout.findViewById(R.id.password);
        final MaterialEditText name=register_layout.findViewById(R.id.name);
        final MaterialEditText phone_number=register_layout.findViewById(R.id.phone_number);
        //final Button otp=register_layout.findViewById(R.id.phone_verification);
        dialog.setView(register_layout);
        /*otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendVerificationCode();
            }
        });
        Button button=register_layout.findViewById(R.id.verify_phone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification(register_layout);
            }
        });*/
        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                if(TextUtils.isEmpty(email.getText().toString()))
                {
                    Snackbar.make(rootLayout,"Please enter Email address",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString()))
                {
                    Snackbar.make(rootLayout,"Please enter Password",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name.getText().toString()))
                {
                    Snackbar.make(rootLayout,"Please enter the Name",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone_number.getText().toString()))
                {
                    Snackbar.make(rootLayout,"Please enter the phone number",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //verifySignInCode();
                //String abc=generaeteOTP();
                //MaterialEditText ett=findViewById(R.id.otp1);
                //String cde=ett.getText().toString();
                //if(abc.equals(cde)) {
                    auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User();
                                    user.setEmail(email.getText().toString());
                                    user.setPassword(password.getText().toString());
                                    user.setName(name.getText().toString());
                                    user.setPhone_number(phone_number.getText().toString());
                                    users.child(user.getEmail()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar.make(rootLayout, "Registered Successfully", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(rootLayout, "Failed! Retry", Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(rootLayout, "Failed! Retry", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            //}
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
    /*public void sendNotification(View view) {

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_phone)
                        .setContentTitle("My notification")
                        .setContentText("OTP :- "+generaeteOTP());


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationManager.notify().

                mNotificationManager.notify(001, mBuilder.build());
    }
    String generaeteOTP(){
        MaterialEditText et1=(findViewById(R.id.name));
        MaterialEditText et2=(findViewById(R.id.email));
        MaterialEditText et3=(findViewById(R.id.phone_number));
        String name=et1.getText().toString();
        String email=et2.getText().toString();
        String phone=et3.getText().toString();
        int a=(((int)(email.charAt(1)))%10);
        int b=(((int)(phone.charAt(5)))%10);
        int c=(((int)(name.charAt(0)))%10);
        String otp="";
        otp=otp+String.valueOf(a)+String.valueOf(b)+String.valueOf(c);
        otp=otp+randomXY();
        return otp;
    }
    private String randomXY() {
        double ab=Math.random();
        int a=(int)(ab*100000000);
        return String.valueOf(a);
    }*/
}
