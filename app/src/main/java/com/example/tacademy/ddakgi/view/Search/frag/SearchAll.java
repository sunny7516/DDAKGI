package com.example.tacademy.ddakgi.view.Search.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.Search.ResSearchAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * 검색창에서 전체검색에 해당하는 Fragment
 */

public class SearchAll extends Fragment {
    AutoCompleteTextView autocomplete_places, mAutocompleteView;
    String autoKeyword[];

    public SearchAll() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_all, container, false);

        autocomplete_places = (AutoCompleteTextView) getActivity().findViewById(R.id.autocomplete_all);
        autocomplete_places.setVisibility(View.VISIBLE);
        mAutocompleteView = (AutoCompleteTextView) getActivity().findViewById(R.id.autocomplete_places);
        mAutocompleteView.setVisibility(GONE);
        autocomplete_places.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (count > 2) {
                    String keyword = autocomplete_places.getText().toString();
                    //String keyword = s.toString();
                    getKeyword(keyword);
                }
            }
        });
        return view;
    }

    public void getKeyword(String keyword) {
        Call<ResSearchAll> resSearchAllCall = NetSSL.getInstance().getMemberImpFactory()
                .resSearchAll(keyword);
        resSearchAllCall.enqueue(new Callback<ResSearchAll>() {
            @Override
            public void onResponse(Call<ResSearchAll> call, Response<ResSearchAll> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:searchKeyword", "SUCCESS" + response.body().getResult());
                    if (response.body().getResult() != null) {
                        autoKeyword = response.body().getResult();
                        Log.i("autoKeyword", autoKeyword[0] + autoKeyword[1] + autoKeyword[2]);
                        autocomplete_places.setAdapter(new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, autoKeyword));
                    }
                } else {
                    Log.i("RF:searchKeyword", "FAIL" + response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResSearchAll> call, Throwable t) {
                Log.i("RF:searchKeyword", "ERR" + t.getMessage());
            }
        });
    }

}
