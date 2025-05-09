package com.example.rupizzeria;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import Classes.Order;
import Classes.Pizza;

/**adaptor to hold all orders created on the app in a recycler view
 * @author Jasmine Justin
 * @author Jimena Reyes*/
public class FinalOrderItemsAdapter extends RecyclerView.Adapter<FinalOrderItemsAdapter.FinalOrderItemHolder> {

    /**list of orders*/
    private ArrayList<Order> orders;

    /**selected list of orders*/
    private ArrayList<Order> selectedOrders;

    /**textview that displays subtotal*/
    private TextView subtotal;

    /**constructor of recycler view adaptor*/
    public FinalOrderItemsAdapter(ArrayList<Order> orders, TextView subTotalOrder) {
        this.orders = orders;
        this.selectedOrders = new ArrayList<>();
        this.subtotal = subTotalOrder;
    }

    /**initializes recycler view adapter to hold view for each order
     * @param parent is the parent view
     * @param viewType is the type of view
     * @return final order item holder*/
    @NonNull
    @Override
    public FinalOrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalorder_item, parent, false);
        return new FinalOrderItemHolder(view);
    }

    /**initializes values associated with each GUI object within each item
     * @param position is the position of the collection of orders
     * @param holder is the inner class that binds GUI objects to recycler view window*/
    @Override
    public void onBindViewHolder(@NonNull FinalOrderItemHolder holder, int position) {
        Order currentOrder = orders.get(position);
        ArrayList<Pizza> listPizzas = currentOrder.getPizzas();
        StringBuilder pizzaDetails = new StringBuilder();
        for (Pizza pizza : listPizzas) {
            pizzaDetails.append(pizza.getPizzaType()).append(" - ").append(pizza.getSize()).append("\n");
        }
        holder.pizzaTypeTextViewFinal.setText("");
        StringBuilder toppingsDetails = new StringBuilder();
        for (Pizza pizza : listPizzas) {
            toppingsDetails.append(pizza.getToppings()).append("\n");
        }
        holder.pizzaToppingsTextViewFinal.setText(toppingsDetails.length() > 0 ? toppingsDetails.toString() : "No toppings");
        holder.pizzaStyleTextViewFinal.setText(String.valueOf(currentOrder.getNumber()));
        holder.pizzaCrustTextViewFinal.setText("");
        holder.pizzaSizeTextViewFinal.setText(currentOrder.getPizzas().toString());
        holder.pizzaToppingsTextViewFinal.setText("");
        holder.pizzaSizeTextViewFinal.setText(listPizzas.isEmpty() ? "No pizzas" : listPizzas.toString());
        holder.selectItemCheckBoxFinal.setChecked(selectedOrders.contains(currentOrder));
        holder.selectItemCheckBoxFinal.setOnClickListener(v -> {
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedPrice = df.format(currentOrder.getTotal());
            subtotal.setText(formattedPrice);
            if (selectedOrders.contains(currentOrder)) {
                selectedOrders.remove(currentOrder);
            } else {
                selectedOrders.add(currentOrder);
            }
            notifyDataSetChanged();
        });
    }

    /**get size of collection
     * @return size*/
    @Override
    public int getItemCount() {
        return orders.size();
    }

    /**return selected orders
     * @return list of orders*/
    public ArrayList<Order> getSelectedOrders() {return selectedOrders;}

    /**item holder to associate GUI objects with respective value*/
    public static class FinalOrderItemHolder extends RecyclerView.ViewHolder {

        /**displays style of pizza*/
        TextView pizzaStyleTextViewFinal;
        /**displays pizza type*/
        TextView pizzaTypeTextViewFinal;
        /**displays crust*/
        TextView pizzaCrustTextViewFinal;
        /**displays size*/
        TextView pizzaSizeTextViewFinal;
        /**displays toppings*/
        TextView pizzaToppingsTextViewFinal;
        /**checkbox in holder*/
        CheckBox selectItemCheckBoxFinal;

        /**constructor of holder
         * @param itemView is reference to view*/
        public FinalOrderItemHolder(@NonNull View itemView) {
            super(itemView);
            pizzaStyleTextViewFinal = itemView.findViewById(R.id.pizzaStyleTextViewFinal);
            pizzaTypeTextViewFinal = itemView.findViewById(R.id.pizzaTypeTextViewFinal);
            pizzaCrustTextViewFinal = itemView.findViewById(R.id.crustTypeTextViewFinal);
            pizzaSizeTextViewFinal = itemView.findViewById(R.id.sizeTextViewFinal);
            pizzaToppingsTextViewFinal = itemView.findViewById(R.id.toppingsTextViewFinal);
            selectItemCheckBoxFinal = itemView.findViewById(R.id.checkBoxFinal);
        }
    }

    /**Method to update the list of orders
     * @param updatedOrders list of orders*/
    @SuppressLint("NotifyDataSetChanged")
    public void updateOrderItems(ArrayList<Order> updatedOrders) {
        this.orders = updatedOrders;
        notifyDataSetChanged();
    }

    /**removes selected item*/
    @SuppressLint("NotifyDataSetChanged")
    public void removeSelectedItems() {
        orders.removeAll(selectedOrders);
        selectedOrders.clear();
        notifyDataSetChanged();
    }
}