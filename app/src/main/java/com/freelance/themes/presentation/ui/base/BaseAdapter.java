package com.freelance.themes.presentation.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

  protected ArrayList<T> itemList;

  public BaseAdapter() {
    this.itemList = new ArrayList<>();
  }

  protected View getItemViewInflated(int itemViewLayoutId, ViewGroup parent) {
    return LayoutInflater.from(parent.getContext())
        .inflate(itemViewLayoutId, parent, false);
  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  public ArrayList<T> getItemList() {
    return itemList;
  }

  public T getItem(int position) {
    return itemList.get(position);
  }

  public void setItems(ArrayList<T> items) {
    itemList = items;
    notifyDataSetChanged();
  }
}
