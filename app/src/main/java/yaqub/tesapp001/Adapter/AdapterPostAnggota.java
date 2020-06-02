package yaqub.tesapp001.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import yaqub.tesapp001.activity.AnggotaActivity;
import yaqub.tesapp001.activity.UpdateAnggotaActivity;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.ResponseAnggota;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class AdapterPostAnggota extends RecyclerView.Adapter<AdapterPostAnggota.ViewHolder> {
    List<ResponseAnggota> apiPostResponses;

    public AdapterPostAnggota(List<ResponseAnggota> data) {
        this.apiPostResponses = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_anggota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResponseAnggota response = apiPostResponses.get(position);

//        holder.ilistNotes.setBackgroundColor(KehadiranActivity.kehadiranActivity.getColor());

        holder.textViewNama.setText(response.nama_person);
        holder.textViewNip.setText(response.nip);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AnggotaActivity.anggotaActivity, response.nama_person, Toast.LENGTH_LONG).show();
                UpdateAnggotaActivity.updateAnggotaActivity.sParam=1;
                UpdateAnggotaActivity.updateAnggotaActivity.strPid=response.pid_persons;
                UpdateAnggotaActivity.updateAnggotaActivity.strNip=response.nip;
                UpdateAnggotaActivity.updateAnggotaActivity.strNama=response.nama_person;
                UpdateAnggotaActivity.updateAnggotaActivity.strJenkel=response.jenis_kelamin;
                UpdateAnggotaActivity.updateAnggotaActivity.strNoHp=response.no_hp;
                UpdateAnggotaActivity.updateAnggotaActivity.strEmail=response.email;
                UpdateAnggotaActivity.updateAnggotaActivity.strAlamat=response.alamat;
                UpdateAnggotaActivity.updateAnggotaActivity.strKet=response.ket;

                AnggotaActivity.anggotaActivity.updateAnggota();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Pesan Konfirmasi.!");
                builder.setMessage("Apakah anda yakin akan menghapus note ini.?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AnggotaActivity.anggotaActivity, response.pid_persons, Toast.LENGTH_LONG).show();
                        APIRetrofitAnggota.iAPIDelAnggota iAPIDelAnggota = RetrofitClient.getClient().create(APIRetrofitAnggota.iAPIDelAnggota.class);
                        Call<Responseku> postDelNotulen = iAPIDelAnggota.postDelAnggota(response.pid_persons);
                        postDelNotulen.enqueue(new Callback<Responseku>() {
                            @Override
                            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(AnggotaActivity.anggotaActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AnggotaActivity.anggotaActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                }
                                APIRetrofitAnggota.DoGetAllAnggota();
                            }

                            @Override
                            public void onFailure(Call<Responseku> call, Throwable t) {
                                Toast.makeText(AnggotaActivity.anggotaActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AnggotaActivity.anggotaActivity, "Note Tidak Dihapus.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                builder.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return apiPostResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNama, textViewNip;
        LinearLayout ilistAnggota;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.tvNama);
            textViewNip = itemView.findViewById(R.id.tvNip);
            ilistAnggota = itemView.findViewById(R.id.llListAnggota);

        }
    }
}
