package com.example.material_design;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class SpaceItem extends DrawerItem<SpaceItem.ViewHolder> {
   private int spaceDP;
   public SpaceItem(int spaceDP) {
   this.spaceDP=spaceDP;

   }


    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        Context c=parent.getContext();
        View view=new View(c);
        int height=(int)(c.getResources().getDisplayMetrics().density*spaceDP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    public class ViewHolder extends DrawerAdapter.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    }

