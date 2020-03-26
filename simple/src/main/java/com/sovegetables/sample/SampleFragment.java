package com.sovegetables.sample;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sovegetables.permission.OnPermissionResultListener;
import com.sovegetables.permission.PermissionResult;
import com.sovegetables.permission.Permissions;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class SampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_sample, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] p = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS};
                Permissions.request(SampleFragment.this, p, new OnPermissionResultListener() {
                    @Override
                    public void allGranted(@NotNull ArrayList<PermissionResult> grantedPermissions) {
                        Toast.makeText(requireContext(),"allGranted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void denied(@NotNull ArrayList<PermissionResult> grantedPermissions, @NotNull ArrayList<PermissionResult> deniedPermissions) {
                        StringBuilder sb = new StringBuilder();
                        boolean has = false;
                        for (PermissionResult g: grantedPermissions){
                            has = true;
                            sb.append(g.getPermission());
                            sb.append(File.pathSeparator);
                        }
                        if(has){
                            sb.append("- granted  ");
                        }
                        has = false;
                        for (PermissionResult g: deniedPermissions){
                            has = true;
                            sb.append(g.getPermission());
                            sb.append(File.pathSeparator);
                        }
                        if(has){
                            sb.append(" denied");
                        }
                        Toast.makeText(requireContext(), sb.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
