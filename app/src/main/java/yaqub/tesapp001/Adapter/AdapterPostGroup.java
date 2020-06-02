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
import yaqub.tesapp001.activity.GroupActivity;
import yaqub.tesapp001.activity.KasActivity;
import yaqub.tesapp001.activity.MainActivity;
import yaqub.tesapp001.api.APIRetrofitGroup;
import yaqub.tesapp001.api.APIRetrofitKas;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.General;
import yaqub.tesapp001.model.ResponseGroup;
import yaqub.tesapp001.model.ResponseKas;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class AdapterPostGroup extends RecyclerView.Adapter<AdapterPostGroup.ViewHolder> {
    List<ResponseGroup> apiPostResponses;

    public AdapterPostGroup(List<ResponseGroup> data) {
        this.apiPostResponses = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResponseGroup response = apiPostResponses.get(position);

        holder.ilistGroup.setBackgroundColor(General.getColorRandom());

        holder.textViewIdGroup.setText(response.id_group);
        holder.textViewNamaGroup.setText(response.group_desc);
        holder.textViewAlias.setText(response.nama_group);

        if (MainActivity.mainActivity.session.isUserLoggedIn()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupActivity.groupActivity.editTextIdGroup.setText(response.id_group);
                    GroupActivity.groupActivity.editTextNamaGroup.setText(response.group_desc);
                    GroupActivity.groupActivity.editTextAlias.setText(response.nama_group);

                    Toast.makeText(GroupActivity.groupActivity, response.nama_group, Toast.LENGTH_SHORT).show();
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
                            APIRetrofitGroup.iAPIDelGroup apiDelGroup = RetrofitClient.getClient().create(APIRetrofitGroup.iAPIDelGroup.class);
                            Call<Responseku> sendDelGroup = apiDelGroup.postDelGroup(response.id_group);
                            sendDelGroup.enqueue(new Callback<Responseku>() {
                                @Override
                                public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(GroupActivity.groupActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(GroupActivity.groupActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                    }
                                    APIRetrofitGroup.DoGetAllGroup();
                                }

                                @Override
                                public void onFailure(Call<Responseku> call, Throwable t) {
                                    Toast.makeText(GroupActivity.groupActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    });

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(GroupActivity.groupActivity, "Data Tidak Dihapus.", Toast.LENGTH_SHORT).show();
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
        TextView textViewIdGroup, textViewAlias, textViewNamaGroup;
        LinearLayout ilistGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewIdGroup = itemView.findViewById(R.id.tvIdGroup);
            textViewNamaGroup = itemView.findViewById(R.id.tvNamaGroup);
            textViewAlias = itemView.findViewById(R.id.tvAlias);
            ilistGroup=itemView.findViewById(R.id.llListGroup);

        }
    }
}
