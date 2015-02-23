package com.cube.utils.list.adapter;

import com.cube.utils.list.holder.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * // TODO: Add class description
 *
 * @author Matt Allen
 * @project Utils
 */
public class ItemTypeAdapter
{
	private ArrayList<Class> mClassTypes;
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
