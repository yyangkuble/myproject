package www.springmvcplus.com.services;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.app.project.mode.User;
import com.app.project.util.Result;

public class Test extends UpdateInterface<User> {

	@Override
	void updateBefore(User entity, Result result) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		Type type = ((ParameterizedType) Test.class.getGenericSuperclass()).getActualTypeArguments()[0];
		System.out.println(type.getTypeName());
	}
}
