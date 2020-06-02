package yaqub.tesapp001.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yaqub.tesapp001.R;
import yaqub.tesapp001.activity.KehadiranActivity;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.api.RetrofitClient;
import yaqub.tesapp001.model.General;
import yaqub.tesapp001.model.ResponseKehadiran;
import yaqub.tesapp001.model.Responseku;

/**
 * Created by Yaqub on 12/15/2017.
 */

public class AdapterPostKehadiran extends RecyclerView.Adapter<AdapterPostKehadiran.ViewHolder> {
    List<ResponseKehadiran> apiPostResponses;

    public AdapterPostKehadiran(List<ResponseKehadiran> data) {
        this.apiPostResponses = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kehadiran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResponseKehadiran response = apiPostResponses.get(position);

//        if (response.absensi == 1) {
//            holder.llListKehadiran.setBackgroundColor(Color.rgb(211, 251, 200));
//        }
        holder.llListKehadiran.setBackgroundColor(General.getColorRandom());

        holder.textViewNip.setText(response.nip);
        holder.textViewNama.setText(response.nama_person);
        holder.textViewKet.setText(response.ket);
        holder.textViewKehadiran.setText(response.tanggal);

        Picasso.with(KehadiranActivity.kehadiranActivity)
                .load(response.file_path)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
//                .resize(150, 150)
                .into(holder.civProfil);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(KehadiranActivity.kehadiranActivity, response.nama_person, Toast.LENGTH_SHORT).show();
            }
        });

//        String dateNow1= DateFormat.getDateTimeInstance().format(new Date());
//        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
        String dateNow= General.pDatetime("yyyy-MM-dd");
        String strTgl = "";
        if(response.nama_person.equals("Tidak ada data")){
            
        }else{
            strTgl=response.tanggal;
        }


        if (strTgl.equals(dateNow)) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Pesan Konfirmasi.!");
                    builder.setMessage(response.nama_person + " Hadir atau Tidak.?");
                    builder.setPositiveButton("Hadir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            APIRetrofitAnggota.APIRetrofit.iAPIUpdateKehadiran apiUpdateKehadiran = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.iAPIUpdateKehadiran.class);
                            Call<Responseku> sendUpdateKehadirn = apiUpdateKehadiran.sendDataUpdate(response.pid_kehadiran, "1", "Hadir", "2012001");
                            sendUpdateKehadirn.enqueue(new Callback<Responseku>() {
                                @Override
                                public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                    }
                                    APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");
//                                    APIRetrofit.DoGetAllPost();
                                }

                                @Override
                                public void onFailure(Call<Responseku> call, Throwable t) {

                                    Toast.makeText(KehadiranActivity.kehadiranActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                                }

                            });
                        }
                    });

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final EditText inputTxt= new EditText(KehadiranActivity.kehadiranActivity);
                            inputTxt.setInputType(InputType.TYPE_CLASS_TEXT);

                            AlertDialog.Builder builder2 = new AlertDialog.Builder(KehadiranActivity.kehadiranActivity);
                            builder2.setTitle("Pesan Konfirmasi.!");
                            builder2.setMessage("Keterangan Tidak Hadir.?");
                            builder2.setView(inputTxt);
                            builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(KehadiranActivity.kehadiranActivity,  inputTxt, Toast.LENGTH_SHORT).show();

                                    APIRetrofitAnggota.APIRetrofit.iAPIUpdateKehadiran apiUpdateKehadiran = RetrofitClient.getClient().create(APIRetrofitAnggota.APIRetrofit.iAPIUpdateKehadiran.class);
                                    Call<Responseku> sendUpdateKehadirn = apiUpdateKehadiran.sendDataUpdate(response.pid_kehadiran, "0", inputTxt.getText().toString(), "2012001");
                                    sendUpdateKehadirn.enqueue(new Callback<Responseku>() {
                                        @Override
                                        public void onResponse(Call<Responseku> call, Response<Responseku> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(KehadiranActivity.kehadiranActivity, response.body().iremarks, Toast.LENGTH_SHORT).show();
                                            }
                                    APIRetrofitAnggota.APIRetrofit.DoPostAllKehadiran("0");
//                                            APIRetrofit.DoGetAllPost();
                                        }

                                        @Override
                                        public void onFailure(Call<Responseku> call, Throwable t) {
                                            Toast.makeText(KehadiranActivity.kehadiranActivity, "Gagal mengirim request.", Toast.LENGTH_SHORT).show();
                                        }

                                    });

                                }
                            });


//                            dialog.dismiss();
                            builder2.show();
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
        TextView textViewNip, textViewNama, textViewKehadiran,textViewKet;
        CircleImageView civProfil;
        ImageView imageViewProfil;
        LinearLayout llListKehadiran;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNip = itemView.findViewById(R.id.txtNip);
            textViewNama = itemView.findViewById(R.id.txtNama);
            textViewKehadiran = itemView.findViewById(R.id.txtKehadiran);
            textViewKet = itemView.findViewById(R.id.txtKet);
            civProfil = itemView.findViewById(R.id.civListProfil);
            llListKehadiran = itemView.findViewById(R.id.llListKehadiran);

        }
    }
}
