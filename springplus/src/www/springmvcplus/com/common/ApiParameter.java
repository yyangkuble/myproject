package www.springmvcplus.com.common;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParameter   {
	public String desc() default "无描述";
	public String[] parameters() ;
}
