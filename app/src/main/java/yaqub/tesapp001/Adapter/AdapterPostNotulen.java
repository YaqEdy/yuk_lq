package yaqub.tesapp001.Adapter;

import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
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
import yaqub.tesapp001.activity.NotulenActivity;
import yaqub.tesapp001.activity.SubNotulenActivity;
import yaqub.tesapp001.api.APIRetrofitKas;
import yaqub.tesapp001.api.APIRetrofitNotulen;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.General;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.ResponseNotulen;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class AdapterPostNotulen extends RecyclerView.Adapter<AdapterPostNotulen.ViewHolder> {
    List<ResponseNotulen> apiPostResponses;

    public AdapterPostNotulen(List<ResponseNotulen> data) {
        this.apiPostResponses = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResponseNotulen response = apiPostResponses.get(position);

//        holder.ilistNotes.setBackgroundColor(KehadiranActivity.kehadiranActivity.getColor());
        if(response.notes.length()<5){
            holder.textViewNotes.setTextSize(50);
        }else if(response.notes.length()<20){
            holder.textViewNotes.setTextSize(40);
        }else if(response.notes.length()<40){
            holder.textViewNotes.setTextSize(30);
        }else if(response.notes.length()<60){
            holder.textViewNotes.setTextSize(25);
        }else if(response.notes.length()<80){
            holder.textViewNotes.setTextSize(20);
        }else if(response.notes.length()<100){
            holder.textViewNotes.setTextSize(15);
        }else{
            holder.textViewNotes.setTextSize(12);
        }

//        holder.textViewNotes.setTypeface(null,Typeface.BOLD);
//        holder.textViewNotes.setTypeface(Typeface.createFromFile(response.notes.substring(0,5)),Typeface.BOLD);
//        Spanned inotess=Html.fromHtml("<b>"+response.notes.substring(0,5)+"</b>");
//        holder.textViewNotes.setText(Html.fromHtml("<b>"+response.notes.substring(0,5)+"</b>"));
        holder.ilistNotes.setBackgroundColor(General.getColorRandom());
        holder.textViewNotes.setText(response.notes);
        holder.textViewDate.setText(response.last_update_date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NotulenActivity.notulenActivity, response.notulen, Toast.LENGTH_LONG).show();
                NotulenActivity.notulenActivity.sParam=0;
                NotulenActivity.notulenActivity.intentSubNote();
                SubNotulenActivity.subNotulenActivity.strNote= response.notulen;
                SubNotulenActivity.subNotulenActivity.strPidEdit= response.pid_notulen;
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
                        APIRetrofitNotulen.iAPIDelNotulen iAPIDelNotulen = RetrofitClient.getClient().create(APIRetrofitNotulen.iAPIDelNotulen.class);
                        Call<Responseku> postDelNotulen = iAPIDelNotulen.postDelNotulen(response.pid_notulen);
                        postDelNotulen.enqueue(new Callback<Responseku>() {
                            @Override
                            public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(NotulenActivity.notulenActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(NotulenActivity.notulenActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                }
                                APIRetrofitNotulen.DoGetAllNotulen();
                            }

                            @Override
                            public void onFailure(Call<Responseku> call, Throwable t) {
                                Toast.makeText(NotulenActivity.notulenActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NotulenActivity.notulenActivity, "Note Tidak Dihapus.", Toast.LENGTH_SHORT).show();
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
        TextView textViewNotes, textViewDate;
        LinearLayout ilistNotes;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNotes = itemView.findViewById(R.id.tvNotes);
            textViewDate = itemView.findViewById(R.id.tvDateNote);
            ilistNotes = itemView.findViewById(R.id.llListNotes);

        }
    }
}
