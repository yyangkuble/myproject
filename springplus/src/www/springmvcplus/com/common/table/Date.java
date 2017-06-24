package www.springmvcplus.com.common.table;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 只有年月日，没有时分秒
 * @author Administrator
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Date   {
}
