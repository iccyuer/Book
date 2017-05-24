package com.example.book.library.adapter;

import android.database.DataSetObserver;
import android.widget.ExpandableListAdapter;

/**
 * Created by Administrator on 2016/5/21.
 */
public abstract class SimpleExlvAdapter implements ExpandableListAdapter {
    
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        
    }
    
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        
    }
    
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    @Override
    public void onGroupExpanded(int groupPosition) {
        
    }
    
    @Override
    public void onGroupCollapsed(int groupPosition) {
        
    }
    
    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }
    
    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
