package com.example.dev_2.g2mdx.Fragments.CardFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dev_2.g2mdx.DB.Helper;
import com.example.dev_2.g2mdx.DB.InternalStorage;
import com.example.dev_2.g2mdx.Home;
import com.example.dev_2.g2mdx.R;
import com.example.dev_2.g2mdx.Retrofit.Models.ListEX;
import com.example.dev_2.g2mdx.Retrofit.Models.Products;
import com.example.dev_2.g2mdx.Retrofit.Models.Shaleh;
import com.example.dev_2.g2mdx.Retrofit.NetworkResponse;
import com.example.dev_2.g2mdx.Retrofit.callRetrofitPaging;
import com.example.dev_2.g2mdx.Retrofit.callRetrofitShaleh;
import com.example.dev_2.g2mdx.dummy.DummyContent.DummyItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */

public class RecyclFragment extends Fragment  {


    static String[] titels;
    static String[] detals;
    static String[] imag;
    static ListEX l;
    RecyclerView recyclerView;
    public static boolean Run1stTime=true;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adapter1;


    private static EndlessRecyclerViewScrollListener scrollListener;
    ArrayList<ListEX> product_arr= new  ArrayList<ListEX>();

    static int count=1;
    static int from=0;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecyclFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecyclFragment newInstance(int columnCount) {
        RecyclFragment fragment = new RecyclFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);


        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String mParam1 = getArguments().getString("key");

        View view = inflater.inflate(R.layout.activity_card_demo1, container, false);

        recyclerView =
                (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        titels = new String[0];
        detals = new String[0];
        imag = new String[0];









        // Append the next page of data into the adapter
        // This method probably sends out a network request and appends new data items to your adapter.


        if (mParam1.equals("s")) {
            if (Helper.internetConnectionAvailable(100)) {
                callRetrofitShaleh retrofitMovies = callRetrofitShaleh.getInstance(getContext());
                retrofitMovies.retrofitCall(new NetworkResponse<ListEX>() {
                    @Override
                    public void onSucess(ListEX result) {
                        fillAdapt(result);

                        List<Shaleh> newList = new ArrayList<Shaleh>(result.getShaleh());
                        try {
                            InternalStorage.writeObject(getContext().getApplicationContext(), "cach_data", newList);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }


                    }


                        @Override
                    public void onFailure() {
                        Toast.makeText(getContext().getApplicationContext(), "Not_success", Toast.LENGTH_LONG).show();
                    }
                });


//       Toast.makeText(getContext(),imag[0], Toast.LENGTH_LONG).show();
            } else

            {
                Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                ReadFromLocal();
            }
        } else {
            callRetrofitPaging retrofitMovies = callRetrofitPaging.getInstance(getContext(),count,from);
            retrofitMovies.retrofitCall(new NetworkResponse<ArrayList<Products>>() {
                @Override
                public void onSucess(ArrayList<Products> result) {
                    Log.e(TAG, "onSucess:___________ " );
                    for(Products p:result){
                        ProductArray.product_arr.add(p);
                    }
                    adapter1 = new ProductAdapter(ProductArray.product_arr, getContext());
                    recyclerView.setAdapter(adapter1);
                    Run1stTime = false;
                    from=from+count;
                                       //      List<Products> newList = new ArrayList<ArrayList<Products>>();
                }


                @Override
                public void onFailure() {


                }
            });
        }
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.e(TAG, "onLoadMore: ");

                loadNextDataFromApi();

                }

        };
        // Adds the scroll listener to RecyclerView

        recyclerView.addOnScrollListener(scrollListener);



        return view;
    }


    public void loadNextDataFromApi() {

    callRetrofitPaging retrofitMovies = callRetrofitPaging.getInstance(getContext(), count, from);
    retrofitMovies.retrofitCall(new NetworkResponse<ArrayList<Products>>() {
        @Override
        public void onSucess(ArrayList<Products> result) {

            for (Products p : result) {
                ProductArray.product_arr.add(p);
            }

            adapter1.notifyDataSetChanged();

            from = from + count;
            //      List<Products> newList = new ArrayList<ArrayList<Products>>();
        }


        @Override
        public void onFailure() {


        }
    });

}


    public void fillAdapt(ListEX result){
        List<String> list = new ArrayList<String>();
        List<String> d = new ArrayList<String>();
        List<String> im = new ArrayList<String>();
        list.clear();
        d.clear();
        im.clear();
        for (int i = 0; i < result.getShaleh().size(); i++) {


            list.add(result.getShaleh().get(i).getTitle().toString());
            d.add(result.getShaleh().get(i).getHeader().toString());
            im.add("http://shaleh4u.com/photos/"+result.getShaleh().get(i).getPicLink().toString());


        }
        titels = list.toArray(new String[0]);
        detals = d.toArray(new String[0]);
        imag = im.toArray(new String[0]);

        adapter = new RecyclerAdapter1(titels, detals, imag, getContext());
        recyclerView.setAdapter(adapter);


    }

    public void ReadFromLocal()
    {
        ListEX l=new ListEX();
        try {
            // Retrieve the list from internal storage :OSAMA ELMADAH
            l.setShaleh( (ArrayList<Shaleh>) InternalStorage.readObject(getContext(), "cach_data"));
            fillAdapt(l);

        } catch (IOException e) {
            Log.e("Error1", e.getMessage());
            Toast.makeText(getContext(),"e1",Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Log.e("Error2", e.getMessage());
            Toast.makeText(getContext(),"e2",Toast.LENGTH_LONG).show();
        }
    }
    Home activityCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
            activityCallback= (Home) context;



        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

 /*   @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        us=true;
        Log.e(TAG, "onToch______ ");

        return false;
    }

*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
