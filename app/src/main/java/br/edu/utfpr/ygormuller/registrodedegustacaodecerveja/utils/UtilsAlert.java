package br.edu.utfpr.ygormuller.registrodedegustacaodecerveja.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import br.edu.utfpr.ygormuller.registrodedegustacaodecerveja.R;

public class UtilsAlert {
    public static void confirmarAcao(Context context, String mensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmacao);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.sim, listenerSim);
        builder.setNegativeButton(R.string.nao, listenerNao);
        builder.setCancelable(false);
        builder.show();
    }
}