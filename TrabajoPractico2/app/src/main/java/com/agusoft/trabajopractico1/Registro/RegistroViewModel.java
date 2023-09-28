package com.agusoft.trabajopractico1.Registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.agusoft.trabajopractico1.Login.MainActivity;
import com.agusoft.trabajopractico1.Login.MainActivityViewModel;
import com.agusoft.trabajopractico1.Modelo.Usuario;
import com.agusoft.trabajopractico1.R;
import com.agusoft.trabajopractico1.Request.ApiClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegistroViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> userM;
    public RegistroViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }
    public LiveData<Usuario> getUserM(){
        if(userM==null){
            userM = new MutableLiveData<>();
        }
        return userM;
    }
    public void logeado(Bundle bundle){
        Boolean log = bundle.getBoolean("logueado");
        if(log){
            File carpeta=context.getFilesDir();
            File archivo=new File(carpeta,"informacion.dat");

            try {
                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);

                Usuario us= (Usuario) ois.readObject();
                userM.setValue(us);
                fis.close();

            } catch (FileNotFoundException e) {
                Toast.makeText(context,"Error al leer.",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context,"Error E/S",Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(context,"Error de clases",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void guardar(Usuario us){
        File carpeta=context.getFilesDir();
        File archivo=new File(carpeta,"informacion.dat");

        try {
            FileOutputStream fos=new FileOutputStream(archivo);
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            ObjectOutputStream ous=new ObjectOutputStream(bos);

            ous.writeObject(us);
            bos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(context,"Error al guardar",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context,"Error E/S",Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
