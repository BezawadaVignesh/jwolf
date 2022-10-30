package test;

import java.lang.reflect.Method;


public class Test {
   public static void main(String args[]) throws Exception{
      Class c = Class.forName("test.DemoTest");
      Object obj = c.newInstance();
      
      Method method = c.getDeclaredMethod("sampleMethod", null);
      //method.setAccessible(true);
      method.invoke(obj, null);
   }
}