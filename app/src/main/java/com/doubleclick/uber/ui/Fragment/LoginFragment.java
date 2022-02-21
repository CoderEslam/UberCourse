package com.doubleclick.uber.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doubleclick.uber.MapsActivity;
import com.doubleclick.uber.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {


    private FirebaseAuth auth;
    private EditText email, password;
    private Button login;
    private TextView donthave;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        auth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        donthave = view.findViewById(R.id.donthave);
        login.setOnClickListener(v -> {
            Login(email.getText().toString(),password.getText().toString());
        });
        donthave.setOnClickListener(v -> {
            RegisterFragment registerFragment = new RegisterFragment();
            StartFragment( registerFragment);
        });

        return view;
    }

    private void StartFragment(Fragment registerFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.righttoleft,R.anim.lefttoright);
        transaction.replace(R.id.mainFrgment,registerFragment);
        transaction.commit();
    }

    private void Login(String e, String p) {
        auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });
    }
}