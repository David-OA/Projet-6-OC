package com.oconte.david.go4lunch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.oconte.david.go4lunch.databinding.ActivitySettingsBinding;
import com.oconte.david.go4lunch.workMates.UserRepository;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    //For Design
    private ActivitySettingsBinding binding;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.configureToolbar();

        this.updateInfoOfUser();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     *  Configure the Toolbar
     */
    protected void configureToolbar() {
        setSupportActionBar(binding.layoutToolbar.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("I'm Hungry !");

    }


    public void updateInfoOfUser() {
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateUsername();
            }
        });
    }

    public Task<Void> updateUsername(String username){
        return userRepository.updateUsername(username);
    }

    //@OnClick(R.id.profile_activity_button_delete)
    public void onClickDeleteButton() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String uid = Objects.requireNonNull(userRepository.getCurrentUser()).getUid();
                        userRepository.deleteUserFromFirestore(uid);
                    }
                })
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }
}
