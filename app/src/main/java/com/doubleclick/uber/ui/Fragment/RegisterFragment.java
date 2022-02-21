package com.doubleclick.uber.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doubleclick.uber.MapsActivity;
import com.doubleclick.uber.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match


    private EditText email, username, password, C_password;
    private FirebaseAuth mAuth;
    private Button Register, iHave;
    private ProgressBar progressBar;
    private DatabaseReference UserRef;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void M(String i) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        C_password = view.findViewById(R.id.confarmpassword);
        Register = view.findViewById(R.id.Register);
        progressBar = view.findViewById(R.id.progressBar);
        iHave = view.findViewById(R.id.ihave);
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        Register.setOnClickListener(v -> {
            LogIn(email.getText().toString(), password.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
        });
        iHave.setOnClickListener(v -> {
            StartFragment(new LoginFragment());
        });

        return view;
    }

    private void LogIn(String email, String password) {
        if (ConfarmData()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    String id = mAuth.getCurrentUser().getUid().toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("name", username.getText().toString());
                    map.put("password", password);
                    map.put("email", email);
                    map.put("image", "default");
                    map.put("id", id);
                    UserRef.child(id).setValue(map);
                    startActivity(new Intent(getContext(), MapsActivity.class));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Check Internet", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Check input...", Toast.LENGTH_SHORT).show();
        }

    }


    private void StartFragment(Fragment login) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.lefttoright, R.anim.righttoleft);
        transaction.replace(R.id.mainFrgment, login);
        transaction.commit();
    }

    private boolean ConfarmData() {
        if (!password.getText().toString().equals("") || password.getText().toString() != null &&
                !email.getText().toString().equals("") || email.getText().toString() != null &&
                !C_password.getText().toString().equals("") || C_password.getText().toString() != null &&
                !username.getText().toString().equals("") || username.getText().toString() != null) {
            return true;
        } else {
            return false;
        }

    }

}