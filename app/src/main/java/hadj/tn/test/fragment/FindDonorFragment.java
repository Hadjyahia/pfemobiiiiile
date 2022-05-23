package hadj.tn.test.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.Model.AppUserRole;
import hadj.tn.test.Model.User;
import hadj.tn.test.R;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindDonorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindDonorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindDonorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindDonorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindDonorFragment newInstance(String param1, String param2) {
        FindDonorFragment fragment = new FindDonorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    List<Bitmap> images=new ArrayList<>();
    List<String> names=new ArrayList<>(), blood=new ArrayList<>(), region= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        Call<List<User>> call = userApi.findByRole(AppUserRole.DONOR);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                String personImage;
                int i=0;
                List<User> getusers = new ArrayList<>();
                getusers.addAll(response.body());
                assert getusers != null;
                System.out.println(getusers.size());
                while(i < getusers.size()){

                                names.add(getusers.get(i).getUsername());
                                blood.add(getusers.get(i).getTypeSang());
                                region.add(getusers.get(i).getRegion());
                                personImage = getusers.get(i).getImage();
                                if (personImage != null) {
                                    byte[] bytes = Base64.decode(personImage, Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    images.add(bitmap);
                                }
                                  i++;
                            }

                System.out.println(images);

                RecyclerView recyclerView = view.findViewById(R.id.listDonor);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                ListDonorAdapter myAdapter = new ListDonorAdapter(names,images,blood,region);
                recyclerView.setAdapter(myAdapter);



            }




            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), "couldn't get", Toast.LENGTH_SHORT).show();
            }
        });





    }
}