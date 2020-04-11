package org.ariia.mvc;

import java.lang.reflect.Parameter;
import java.util.Arrays;

import org.ariia.mvc.processing.ProxySwitcher;
import org.junit.jupiter.api.Test;

public class ControllerTest {
	
	
	@Test
	public void test1() {
		ItemController item = new ItemController();
        ProxySwitcher controller = new ProxySwitcher(item);
        controller.getMethodIndexs().forEach(System.out::println);
        controller.getMethodIndexs().forEach(m -> {
        	System.out.println(m.getMethod().getReturnType());
        	System.out.println(m.getMethod().getAnnotatedReturnType());
        	try {
        		for (Parameter parameter : m.getMethod().getParameters()) {
        			System.out.print(parameter.getName());
        			System.out.print('\t');
        			System.out.print(parameter.getAnnotatedType());
        			System.out.print('\t');
        			System.out.print(Arrays.toString(parameter.getAnnotations()));
        			System.out.print('\t');
        			System.out.print(parameter.isSynthetic());
        			System.out.print('\n');
        		}
    			System.out.print('\n');
//				System.out.println(m.getMethod().invoke(item, 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
	}

}