package ufc.smd.esqueleto_placar

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import data.UIEducacionalPermissao
import androidx.activity.result.ActivityResultLauncher as ActivityResultLauncher1

class PermissionActivity : AppCompatActivity(), UIEducacionalPermissao.NoticeDialogListener {

    lateinit var requestPermissionLauncher:androidx.activity.result.ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        requestPermissionLauncher= registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    ligarFunc("tel:+5588999999999")
                } else {
                    Snackbar.make(
                        findViewById(R.id.layoutPermission),
                        R.string.semPerLigar,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun ligarWhen(v:View){
        when{
            //Primeiro Caso do When - A permissão já existe
            ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED ->{
                ligarFunc("tel:+558588888889")
            }
            //Permissão Negada mas não para sempre
            shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) ->{
                // Chamar a UI Educacional
                val mensagem =
                    "Nossa aplicação precisa acessar o telefone para discagem automática. Uma janela de permissão será solicitada"
                val titulo = "Permissão de acesso a chamadas"
                val codigo = 1 //Código da requisição
                val mensagemPermissao = UIEducacionalPermissao(mensagem, titulo, codigo)
                mensagemPermissao.onAttach(this as Context)
                mensagemPermissao.show(supportFragmentManager, "primeiravez")
            }
            // Permissão não foi pedida ainda
            else ->{
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }

    fun ligarFunc (numeroCall:String){
        val uri = Uri.parse(numeroCall)
        val itLigar = Intent(Intent.ACTION_CALL, uri)
        startActivity(itLigar)
    }

    override fun onDialogPositiveClick(codigo: Int) {
        //Método chamado pela caixa de diálogo
        Log.v("PDM","Apertou OK")
        requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
    }
}