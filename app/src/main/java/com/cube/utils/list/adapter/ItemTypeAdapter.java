package com.cube.utils.list.adapter;

import com.cube.utils.list.holder.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This adapter is intended to define the relationships between a class and the view holder
 * used to populate a view with members of that class.
 *
 * @author Matt Allen
 * @project Utils
 */
public class ItemTypeAdapter
{
	/**
	 * This list stores all of the class types registered in the adapter. We use
	 * the index of the class here to define the view type integer needed
	 * in the {@link android.support.v7.widget.RecyclerView.Adapter}.
	 */
	private ArrayList<Class> mClassTypes;
	/**
	 * The class types in the class types list are the key to this map that will
	 * return the relevant view holder class type that can be instantiated.
	 */
	private HashMap<Class, Class<? extends ViewHolder>> mViewHolders;

	public Class<? extends ViewHolder> getHolderForType(int type)
	{
		return mViewHolders.get(mClassTypes.get(type));
	}

	public int getIndexForClassType(Class classType)
	{
		return mClassTypes.indexOf(classType);
	}

	public void addClassType(Class classType, Class<? extends ViewHolder> holderClass)
	{

	}

	private HashMap<Class, Class<? extends ViewHolder>> getViewHolders()
	{
		if (mViewHolders == null)
		{
			mViewHolders = new HashMap<>();
		}
		return mViewHolders;
	}

	private ArrayList<Class> getClassTypes()
	{
		if (mClassTypes == null)
		{
			mClassTypes = new ArrayList<>();
		}
		return mClassTypes;
	}
}
