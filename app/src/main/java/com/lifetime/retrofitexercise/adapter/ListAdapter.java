package com.lifetime.retrofitexercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifetime.retrofitexercise.R;
import com.lifetime.retrofitexercise.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements Filterable {
    private OnItemClickListener listener;
    private List<Employee> employees;
    private Context context;

    private List<Employee> employeesFullList;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public ListAdapter(List<Employee> employees, Context context) {
        this.employees = employees  ;
        this.context = context;
        employeesFullList = new ArrayList<>(employees);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_employee, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bindView(employees.get(position));
//        holder.id.setBackgroundColor(0xFF93C47D);

//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(employees.get(position).get)
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Employee> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(employeesFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Employee employee : employeesFullList) {
                    if (employee.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(employee);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            employees.clear();
            employees.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        TextView textViewName;
        TextView textViewSalary;
        TextView textViewAge;
        TextView id;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewSalary = itemView.findViewById(R.id.textViewSalary);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            id = itemView.findViewById(R.id.textViewId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(employees.get(position));
                    }
                }
            });


        }

        public void bindView(Employee employee) {

            textViewName.setText(employee.getName());
            textViewAge.setText("age:" + employee.getAge());
            textViewSalary.setText("$" + employee.getSalary());
            id.setText("EmployeeID: " + employee.getId());
        }

//        @Override
//        public void onClick(View view) {
//            Employee employee = employees.get(getAdapterPosition());
//
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("employee", employee);
//
//            context.startActivity(intent);
//        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Employee employee);
    }

}
