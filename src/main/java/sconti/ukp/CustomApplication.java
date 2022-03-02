package sconti.ukp;

import org.glassfish.jersey.server.ResourceConfig;
 
public class CustomApplication extends ResourceConfig 
{
    public CustomApplication() 
    {
        packages("ksrsac.basicauth");
    //   register(LoggingFilter.class);
     //   register(GsonMessageBodyHandler.class);
 
        //Register Auth Filter here
        register(AuthenticationFilter.class);
    }
}