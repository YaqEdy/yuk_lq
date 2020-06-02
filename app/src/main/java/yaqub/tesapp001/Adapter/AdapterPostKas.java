package yaqub.tesapp001.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.R;
import yaqub.tesapp001.activity.KasActivity;
import yaqub.tesapp001.activity.KehadiranActivity;
import yaqub.tesapp001.activity.MainActivity;
import yaqub.tesapp001.api.APIRetrofitKas;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.General;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class AdapterPostKas extends RecyclerView.Adapter<AdapterPostKas.ViewHolder> {
    List<ResponseKas> apiPostResponses;

    public AdapterPostKas(List<ResponseKas> data) {
        this.apiPostResponses = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResponseKas response = apiPostResponses.get(position);

        holder.ilistKas.setBackgroundColor(General.getColorRandom());

        holder.textViewTgl.setText(response.tgl_kas);
        holder.textViewStatus.setText(response.status);
        holder.textViewJml.setText(response.jumlah);
        holder.textViewSaldo.setText(response.saldo);
        holder.textViewKet.setText(response.ket);

        if (MainActivity.mainActivity.session.isUserLoggedIn()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KasActivity.kasActivity.spinnerStatus.setSelection(response.st);
                    KasActivity.kasActivity.pid_kas_update = response.pid_kas;
                    if(response.st==1){
                        KasActivity.kasActivity.editTextJml.setText(response.jumlah);
                    }else {
                        KasActivity.kasActivity.editTextJml.setText(response.jumlah.substring(1,response.jumlah.length()));
                    }
                    KasActivity.kasActivity.editTextKet.setText(response.ket);
                    Toast.makeText(KasActivity.kasActivity, response.tgl_kas, Toast.LENGTH_SHORT).show();
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Pesan Konfirmasi.!");
                    builder.setMessage("Apakah anda yakin akan menghapus data ini.?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            APIRetrofitKas.iAPIDelKas apiDelKas = RetrofitClient.getClient().create(APIRetrofitKas.iAPIDelKas.class);
                            Call<Responseku> sendDelKas = apiDelKas.postDelkas(response.pid_kas);
                            sendDelKas.enqueue(new Callback<Responseku>() {
                                @Override
                                public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(KasActivity.kasActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(KasActivity.kasActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                    }
                                    APIRetrofitKas.DoGetAllKas();
                                }

                                @Override
                                public void onFailure(Call<Responseku> call, Throwable t) {
                                    Toast.makeText(KasActivity.kasActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    });

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(KasActivity.kasActivity, "Data Tidak Dihapus.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return apiPostResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTgl, textViewStatus, textViewJml, textViewSaldo, textViewKet;
        LinearLayout ilistKas;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTgl = itemView.findViewById(R.id.tvTgl);
            textViewStatus = itemView.findViewById(R.id.tvStatus);
            textViewJml = itemView.findViewById(R.id.tvJumlah);
            textViewSaldo = itemView.findViewById(R.id.tvSaldo);
            textViewKet = itemView.findViewById(R.id.tvKet);
            ilistKas=itemView.findViewById(R.id.llListKas);

        }
    }
}
