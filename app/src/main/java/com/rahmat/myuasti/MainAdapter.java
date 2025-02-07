package com.rahmat.myuasti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter <MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(final myViewHolder holder, int position, @NonNull MainModel model) {
        holder.nama.setText(model.getNama());
        holder.npm.setText(model.getNpm());
        holder.nohp.setText(model.getNohp());
        holder.alamat.setText(model.getAlamat());

        Glide.with(holder.img.getContext())
                .load(model.getMurl())
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.img);

        holder.btnedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,2000)
                        .create();
                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText nama = view.findViewById(R.id.txtnama);
                EditText npm = view.findViewById(R.id.txtnpm);
                EditText nohp = view.findViewById(R.id.txtnohp);
                EditText alamat = view.findViewById(R.id.txtalamat);
                EditText murl = view.findViewById(R.id.txturl);

                Button btnUpdate = view.findViewById(R.id.btnupdate);

                nama.setText(model.getNama());
                npm.setText(model.getNpm());
                nohp.setText(model.getNohp());
                alamat.setText(model.getAlamat());
                murl.setText(model.getMurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("nama",nama.getText().toString());
                        map.put("npm",npm.getText().toString());
                        map.put("nohp",nohp.getText().toString());
                        map.put("alamat",alamat.getText().toString());
                        map.put("murl",murl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("mahasiswa")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nama.getContext(), "data berhasil di update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.nama.getContext(), "data gagal di update", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.btndlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nama.getContext());
                builder.setTitle("apakah anda yakin");
                builder.setMessage("data yang dihapus tidak dapat di kembalikan ");

                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("mahasiswa")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.nama.getContext(),"Batal", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView nama,npm,nohp,alamat;

        Button btnedt,btndlt;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.img1);
            nama = (TextView) itemView.findViewById(R.id.namatxt);
            npm = (TextView) itemView.findViewById(R.id.npmtxt);
            nohp= (TextView) itemView.findViewById(R.id.nohp);
            alamat =(TextView) itemView.findViewById(R.id.alamattxt);
            btnedt =(Button) itemView.findViewById(R.id.btnedt);
            btndlt =(Button) itemView.findViewById(R.id.btndlt);
        }
    }
}
