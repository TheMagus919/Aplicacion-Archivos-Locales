package com.agusoft.trabajopractico1.Registro;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.agusoft.trabajopractico1.Login.MainActivityViewModel;
import com.agusoft.trabajopractico1.Modelo.Usuario;
import com.agusoft.trabajopractico1.R;
import com.agusoft.trabajopractico1.databinding.ActivityMainBinding;
import com.agusoft.trabajopractico1.databinding.RegistroBinding;

public class RegistroActivity extends AppCompatActivity {
    private RegistroViewModel rv;
    private RegistroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroViewModel.class);
        Bundle bundle = getIntent().getExtras();
        rv.getUserM().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.edDniR.setText(usuario.getDni()+"");
                binding.edNombreR.setText(usuario.getNombre());
                binding.edApellidoR.setText(usuario.getApellido());
                binding.edEmailR.setText(usuario.getMail());
                binding.edPasswordR.setText(usuario.getPassword());
            }
        });
        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = binding.edDniR.getText().toString();
                Usuario user = new Usuario( Long.parseLong(dni),binding.edNombreR.getText().toString(),binding.edApellidoR.getText().toString(),binding.edEmailR.getText().toString(),binding.edPasswordR.getText().toString());
                rv.guardar(user);
            }
        });
        rv.logeado(bundle);
    }
}
