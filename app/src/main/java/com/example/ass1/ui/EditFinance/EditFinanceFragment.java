package com.example.ass1.ui.EditFinance;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.ass1.R;
import com.example.ass1.database.Finance;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class EditFinanceFragment extends Fragment {

    private EditFinanceViewModel editFinanceViewModel;

    private TextView Title;
    private TextView Amount;
    private Spinner Category;

    private int expense = 0;
    private int income = 1;

    private ArrayList<String> categoryOptions = new ArrayList<String>();
    private String category_Chosen;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getView().setBackgroundColor(Color.BLACK);
        Title = getView().findViewById(R.id.editTitle);
        Category = getView().findViewById(R.id.category_spinner);

        Amount = getView().findViewById(R.id.editAmount);
        listenerSetup();
        observerSetup();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editFinanceViewModel =
                new ViewModelProvider(this).get(EditFinanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit_finance, container, false);
        editFinanceViewModel = ViewModelProviders.of(this).get(EditFinanceViewModel.class);
        return root;
    }

    private void clearFields()
    {
        Title.setText("");
        Amount.setText("");
    }

    //For the spinner
    private void setCategoryExpenditure(){
    categoryOptions.clear();
        categoryOptions.add("Travel");
        categoryOptions.add("Food");
        categoryOptions.add("Accommodation");
        categoryOptions.add("Leisure");
        categoryOptions.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,categoryOptions);
        Category.setAdapter(adapter);
        Category.setSelection(0);
    }
    //For the spinner
    private void setCategoryIncome(){
        categoryOptions.clear();
        categoryOptions.add("Salary");
        categoryOptions.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,categoryOptions);
        Category.setAdapter(adapter);
        Category.setSelection(0);
    }

    private void listenerSetup() {

        AppCompatButton addButton = getView().findViewById(R.id.button_add);
        AppCompatButton findButton = getView().findViewById(R.id.button_find);
        AppCompatButton deleteButton = getView().findViewById(R.id.button_delete);
        RadioButton incomeButton = getView().findViewById(R.id.income_RB_edit);
        RadioButton expenseButton = getView().findViewById(R.id.expenditure_RB_edit);

        incomeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setCategoryIncome();
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addToFinance(income);
                    }
                });
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setCategoryExpenditure();
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addToFinance(expense);
                    }
                });
            }
        });

        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String label = adapterView.getItemAtPosition(i).toString();
                category_Chosen = label;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFinanceViewModel.findFinance(Title.getText().toString());
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFinanceViewModel.deleteFinance(Title.getText().toString());
                clearFields();
            }
        });
    }

    private void addToFinance(int type){
        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = myDateFormat.format(calendar.getTime());

        String title = Title.getText().toString();
        String category = category_Chosen;
        String amount = Amount.getText().toString();
        String text = "Succesfully added to expenditure ";

        MutableDateTime mutableDateTime = new MutableDateTime();
        mutableDateTime.setDate(0);
        DateTime thisMoment = new DateTime();

        Months months = Months.monthsBetween(mutableDateTime, thisMoment);
        if (!title.equals("") && !category.equals("")) {

            if(type == expense){
                Finance finance = new Finance(type, date, title,category,
                        -Integer.parseInt(amount), months.getMonths());
                editFinanceViewModel.insertFinance(finance);
            }
            else if(type == income){
                Finance finance = new Finance(type, date, title,category,
                        Integer.parseInt(amount), months.getMonths());
                editFinanceViewModel.insertFinance(finance);
            }
            clearFields();

            Toast.makeText(getActivity().getApplicationContext(), text , Toast.LENGTH_SHORT).show();
        }
    }

    private void observerSetup() {

        editFinanceViewModel.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<Finance>>() {
                    @Override
                    public void onChanged(@Nullable final List<Finance> finance) {

                        if (finance.size() > 0) {

                            Title.setText(finance.get(0).getTitle());
                            Amount.setText(String.format(Locale.US, "%d",
                                    finance.get(0).getAmount()));
                        }
                    }
                });
        }
    }