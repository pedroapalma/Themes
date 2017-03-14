package com.freelance.themes.presentation.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

  public BaseViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    itemView.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    onClickItemView(view);
  }

  protected abstract void onClickItemView(View view);
}
