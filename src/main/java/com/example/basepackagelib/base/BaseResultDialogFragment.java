package com.example.basepackagelib.base;


public abstract class BaseResultDialogFragment<T> extends BaseDialogFragment {
	public IResultListener<T> listener;

	public void setDialogListener(IResultListener<T> listener){
		this.listener=listener;
	}

	public interface IResultListener<T> {
		public void onFeedBack(T result);
	}
}
