package www.springmvcplus.com.common.table;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TableCreate   {
	public enum TableCreateType{isNotExistsCreate,dropAndCreate};
	public TableCreateType value() default TableCreateType.isNotExistsCreate;
}
