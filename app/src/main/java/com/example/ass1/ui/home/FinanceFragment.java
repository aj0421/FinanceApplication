package com.example.ass1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass1.R;
import com.example.ass1.database.Finance;
import com.example.ass1.database.FinanceListAdapter;

import java.util.List;
import java.util.Locale;


public class FinanceFragment extends Fragment {

    private FinanceViewModel homeViewModel;
    private FinanceListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView Title;
    private TextView Category;
    private TextView Amount;
    private TextView ShowAmount;
    private int expenditure = 0;
    private int income = 1;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Title = getView().findViewById(R.id.editTitle);
        Category = getView().findViewById(R.id.category_spinner);
        Amount = getView().findViewById(R.id.editAmount);
        ShowAmount = getView().findViewById(R.id.show_balance);
        observerSetup();
        listenerSetup();
        recyclerSetup();
        setShowAmount();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(FinanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_finance, container, false);
          homeViewModel = ViewModelProviders.of(this).get(FinanceViewModel.class);

        return root;
    }

    private void listenerSetup()
    {
        RadioButton allButton = getView().findViewById(R.id.all_RB);
        RadioButton incomeButton = getView().findViewById(R.id.income_RB);
        RadioButton expenditureButton = getView().findViewById(R.id.expenditure_RB);

        allButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeViewModel.getAllFinance().observe(getViewLifecycleOwner(), new Observer<List<Finance>>() {
                        @Override
                        public void onChanged(@Nullable final List<Finance> finance) {
                            adapter.setFinanceList(finance);
                            setShowAmount();
                        }
                    });
                }
        });

        //INCOME
        incomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                homeViewModel.getType(income).observe(getViewLifecycleOwner(), new Observer<List<Finance>>() {
                    @Override
                    public void onChanged(@Nullable final List<Finance> finance) {
                       adapter.setFinanceList(finance);
                        getAmountID(1);
                    }
                });
            }


        });

        //EXPENDITURE
        expenditureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.getType(expenditure).observe(getViewLifecycleOwner(), new Observer<List<Finance>>() {
                    @Override
                    public void onChanged(@Nullable final List<Finance> finance) {
                     adapter.setFinanceList(finance);
                    getAmountID(0);
                    }
                });
            }
        });
    }

    private void getAmountID(int id){

        homeViewModel.getAmountID(id).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null || integer == 0) {
                    ShowAmount.setText("0");
                } else if (integer >= 0) {
                    ShowAmount.setText("+ " + integer + " kr");
                } else {
                    ShowAmount.setText(+integer + " kr");
                }
            }

        });
    }

    private void setShowAmount() {
        homeViewModel.getAmount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null || integer == 0) {
                    ShowAmount.setText("0");
                } else if (integer >= 0) {
                    ShowAmount.setText("+ " + integer + " kr");
                } else {
                    ShowAmount.setText(+integer + " kr");
                }
            }

        });
    }

    private void observerSetup() {

        homeViewModel.getAllFinance().observe(getViewLifecycleOwner(), new Observer<List<Finance>>() {
            @Override
            public void onChanged(@Nullable final List<Finance> finance) {
                adapter.setFinanceList(finance);
            }
        });

        homeViewModel.getSearchResults().observe(getViewLifecycleOwner(),
                new Observer<List<Finance>>() {
                    @Override
                    public void onChanged(@Nullable final List<Finance> finance) {

                        if (finance.size() > 0) {

                            Title.setText(finance.get(0).getTitle());
                            Category.setText(finance.get(0).getCategory());
                            Amount.setText(String.format(Locale.US, "%d",
                                    finance.get(0).getAmount()));
                            ShowAmount.setText(String.format(Locale.US, "%d",finance.get(0).getAmount()));
                        }
                    }
                });
    }

    private void recyclerSetup() {
        adapter = new FinanceListAdapter(R.layout.finance_row);
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}