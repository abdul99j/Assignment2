package com.example.assignment2;



import android.widget.Filter;

import com.example.assignment2.Student;
import com.example.assignment2.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter{

    private StudentAdapter adapter;
    private List<Student> filterList;

    CustomFilter(ArrayList<Student> filterList, StudentAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED STUDENTS
            ArrayList<Student> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {


        adapter.setStudent((ArrayList<Student>) results.values);

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

