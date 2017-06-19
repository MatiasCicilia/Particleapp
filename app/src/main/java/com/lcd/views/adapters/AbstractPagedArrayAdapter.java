package com.lcd.views.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import lcd.particle.R;


public abstract class AbstractPagedArrayAdapter<T> extends BaseAdapter implements AbsListView.OnScrollListener {
    public interface IMoreItemRequester{
        public void requestMoreItems();
    }

    public interface OnCheckedListUpdate {
        public void onCheckedListUpdate();
    }

    private OnCheckedListUpdate updater;

    private FragmentManager manager;
    private IMoreItemRequester requester;
    protected List<T> items;
    protected LayoutInflater inflater;
    protected SparseArray<HashMap<T, View>> cachedViews;
    protected Boolean hasMore;

    private List<T> checkedItems = new ArrayList<T>();

    private View pendingView=null;
    private View pendingViewProgressBar = null;
    private AtomicBoolean keepOnAppending = new AtomicBoolean(true);
    private boolean wasErrorShown = false;



    protected abstract View fillView (T item, ViewGroup parent);

    protected Activity context;
    private ListView listView;
    private Boolean shouldRequestMoreItemsImmediately = false;
    private Boolean shouldChache;




    public void init(Activity context, ListView listView) {
        this.inflater = LayoutInflater.from(context);
        this.pendingViewProgressBar = this.createProgressBar(context);

        this.cachedViews = new SparseArray<HashMap<T, View>>(2);
        this.cachedViews.put(0, new HashMap<T, View>());
        this.cachedViews.put(1, new HashMap<T, View>());
        this.context = context;
        this.items = new ArrayList<T>();

        setListView(listView);
        this.shouldChache = true;
    }

    public void addItem(T item){
        checkedItems.add(item);
        if (updater != null) updater.onCheckedListUpdate();
    }

    public void deleteItem(T item){
        checkedItems.remove(item);
        if (updater != null) updater.onCheckedListUpdate();
    }

    public List<T> getCheckedItems(){
        return checkedItems;
    }
    public void setListView(ListView listView) {
        this.listView = listView;
        this.listView.setOnScrollListener(this);
    }
    public void setShouldChache(Boolean shouldChache) {
        this.shouldChache = shouldChache;
    }
    public void asyncErrorShown() {
        this.removeSpinner();
        wasErrorShown = true;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (wasErrorShown && totalItemCount == firstVisibleItem + visibleItemCount){
            wasErrorShown = false;
            this.setKeepOnAppending(true);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0 || scrollState == 2)
            this.notifyReleaseEvent();
    }

    private View createProgressBar(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.infinite_scrolling_spinner, null);
    }

    public void setRequester(IMoreItemRequester requester) {
        this.requester = requester;
    }

    public void setUpdater(OnCheckedListUpdate updater){
        this.updater= updater;
    }

    public void stopAppending() {
        setKeepOnAppending(false);
    }

    public void restartAppending() {
        setKeepOnAppending(true);
    }

    @Override
    public int getCount() {
        return items.size() + (keepOnAppending.get()?1:0);
    }

    public int getItemsCount(){
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size()) {
            return(IGNORE_ITEM_VIEW_TYPE);
        }

        return(super.getItemViewType(position));
    }

    @Override
    public int getViewTypeCount() {
        return(super.getViewTypeCount() + 1);
    }

    @Override
    public Object getItem(int position) {
        if (position >= items.size()) {
            return null;
        }

        return items.get(position);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position >= items.size()) {
            return false;
        }
        return super.isEnabled(position);
    }


    public void setKeepOnAppending(boolean newValue) {
        boolean same = newValue==keepOnAppending.get();
        keepOnAppending.set(newValue);
        if (!same) {
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearCache(){
        this.cachedViews.get(0).clear();
        this.cachedViews.get(1).clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View res = null;

        if (position == items.size() && keepOnAppending.get()) {
            if (pendingView == null) {
                pendingView=this.pendingViewProgressBar;
                if (this.requester == null)
                    throw new RuntimeException("You must set an IMoreItemRequester to use a AbstractPagedArrayAdapter");
                else
                if (shouldRequestMoreItemsImmediately)
                    this.requester.requestMoreItems();
            }

            res = pendingView;
        } else {
            int orientationIndex = getOrientationIndex(this.context);
            T item = this.items.get(position);

            if (!shouldChache || this.cachedViews.get(orientationIndex).get(item)==null){
                this.cachedViews.get(orientationIndex).put(item, this.fillView(item, parent));
            }

            res = this.cachedViews.get(orientationIndex).get(item);
        }


        return res;
    }

    public void requestMoreItemsImmediately(){
        this.shouldRequestMoreItemsImmediately = true;
    }

    public void notifyReleaseEvent(){
        if (pendingView != null)
            this.requester.requestMoreItems();
    }

    protected int getOrientationIndex(Context context) {
        return context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE?0:1;
    }

    public void notifyNewItems(List<T> newItems){
        if(newItems!=null) {
            this.items.addAll(newItems);
            cleanPendingView();
            notifyDataSetChanged();
        }
    }

    public void cleanPendingView() {
        pendingView=null;
    }

    public void removeSpinner(){
        this.setKeepOnAppending(false);
        this.cleanPendingView();
    }

    public void clearItems(){
        this.items.clear();
        checkedItems.clear();
    }

    public void clearUnchecked(){
        this.items.clear();
        for(T item: checkedItems){
            items.add(item);
        }
    }

    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }
}