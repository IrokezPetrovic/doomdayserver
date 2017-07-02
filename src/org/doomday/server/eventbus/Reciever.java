package org.doomday.server.eventbus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Reciever {
	public String value() default "*";	
}
