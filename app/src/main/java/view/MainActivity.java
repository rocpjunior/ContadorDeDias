package view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rocpjunior.contadordedias.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import constant.ContadorDiasConstant;
import data.SecurityPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder mViewHolder = new ViewHolder ();
    private SecurityPreferences mSecurityPreferences;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT  = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);
        this.mViewHolder.textHoje = findViewById(R.id.text_hoje);
        this.mViewHolder.textDiasRestantes = findViewById(R.id.text_dias_restantes);
        this.mViewHolder.buttonConfirmar = findViewById(R.id.button_confirmar);
        this.mViewHolder.buttonConfirmar.setOnClickListener(this);
        //DATAS
        this.mViewHolder.textHoje.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        String DaysLeft = String.format("%s %s", this.getDaysLeft(), getString(R.string.dias));
        this.mViewHolder.textDiasRestantes.setText(DaysLeft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.VerifyPresence();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_confirmar) {
           String presence = this.mSecurityPreferences.getStoredString(ContadorDiasConstant.PRESENCE_KEY);
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(ContadorDiasConstant.PRESENCE_KEY, presence);

            startActivity(intent);
        }
    }

    private void VerifyPresence() {
    //NÃO CONFIRMADO, SIM, NÃO
        String presence = this.mSecurityPreferences.getStoredString(ContadorDiasConstant.PRESENCE_KEY);
        if(presence.equals("")) {
            this.mViewHolder.buttonConfirmar.setText(getString(R.string.nao_confirmado));
        } else if(presence.equals(ContadorDiasConstant.CONFIRMATION_YES)) {
            this.mViewHolder.buttonConfirmar.setText(getString(R.string.sim));
        } else {
            this.mViewHolder.buttonConfirmar.setText(getString(R.string.nao));
        }
    }

    private int getDaysLeft () {
        //DATA DE HOJE
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        //DIA MÁXIMO DO ANO
        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    public static class ViewHolder {
    TextView textHoje;
    TextView textDiasRestantes;
    Button buttonConfirmar;
    }
}