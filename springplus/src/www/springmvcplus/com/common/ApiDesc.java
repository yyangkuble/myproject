package www.springmvcplus.com.common;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDesc   {
	public String value() default "无注释";
}
