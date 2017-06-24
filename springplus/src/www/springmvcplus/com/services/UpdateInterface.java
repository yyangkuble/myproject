package www.springmvcplus.com.services;

import com.app.project.util.Result;

public abstract class UpdateInterface<T> {
	
	void updateBefore(T entity,Result result) {
		
	}
	void updateAfter(Result result) {
	}
	void deleteBefore(T entity,Result result) {
	}
	void deleteAfter(Result result) {
	}
	void insertBefore(T entity,Result result) {
	}
	void insertAfter(Result result) {
	}
	
}
