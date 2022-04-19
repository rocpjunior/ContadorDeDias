package view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.rocpjunior.contadordedias.R;

import constant.ContadorDiasConstant;
import data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder ();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);
        this.mViewHolder.checkParticipar = findViewById(R.id.check_participar);
        this.mViewHolder.checkParticipar.setOnClickListener(this);
        this.loadDataFromActivity ();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.check_participar) {
            if(this.mViewHolder.checkParticipar.isChecked()) {
                //Salvar a presença
                this.mSecurityPreferences.storeString(ContadorDiasConstant.PRESENCE_KEY, ContadorDiasConstant.CONFIRMATION_YES);
            } else {
                //Salvar a ausência
                this.mSecurityPreferences.storeString(ContadorDiasConstant.PRESENCE_KEY, ContadorDiasConstant.CONFIRMATION_NO);
            }
        }
    }

    private void loadDataFromActivity () {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String presence = extras.getString(ContadorDiasConstant.PRESENCE_KEY);
            if (presence != null && presence.equals(ContadorDiasConstant.CONFIRMATION_YES)) {
                this.mViewHolder.checkParticipar.setChecked(true);
            }  else {
                this.mViewHolder.checkParticipar.setChecked(false);
            }
        }
    }

    private static class ViewHolder {
        CheckBox checkParticipar;
    }
}