package com.example.restclient;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        Label errLabel = new Label();
        
        final TextField id = new TextField();
        id.setCaption("Enter the id of the user");
        id.setValidationVisible(false);
        id.setRequired(true);
        StringLengthValidator slv = new StringLengthValidator("ID must be alpha numeric of length 6 to 7", 6, 7, true);
        id.addValidator(slv);
        id.setInputPrompt("1234567");      
        
        final TextField userName = new TextField();
        userName.setCaption("Enter the username");
        userName.setValidationVisible(false);
        userName.setRequired(true);
        StringLengthValidator unv = new StringLengthValidator("Username must be alpha numeric of length 4 to 20", 6, 20, true);
        userName.addValidator(unv);
        userName.setInputPrompt("john.doe");
        
        final TextField vorName = new TextField();
        vorName.setCaption("Enter the first name");
        vorName.setRequired(true);
        vorName.setValidationVisible(false);
        StringLengthValidator vnv = new StringLengthValidator("Firstname must be alphanumeric of length greater than 4", 4, 25, true);
        vorName.addValidator(vnv);
        vorName.setInputPrompt("John");
        
        final TextField nachName = new TextField();
        nachName.setCaption("Enter the last name");
        nachName.setRequired(true);
        nachName.setValidationVisible(false);
        StringLengthValidator lnv = new StringLengthValidator("Firstname must be alphanumeric of length greater than 4", 4, 25, true);
        nachName.addValidator(lnv);
        nachName.setInputPrompt("Doe");
        
        final TextField emailId = new TextField();
        emailId.setCaption("Enter the email id");
        emailId.setRequired(true);      
        emailId.setValidationVisible(false);        
        emailId.setInputPrompt("john.doe@xyz.com");
        EmailValidator ev = new EmailValidator("You must provide a valid email id");
        emailId.addValidator(ev);
        
        
        Button button = new Button("Submit");
/*        button.addClickListener( e -> {
            layout.addComponent(new Label("Thanks " + vorName.getValue() 
                    + ", it works!"));
        });
*/
        button.addClickListener(new Button.ClickListener(
        		) {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String url = "http://localhost:8080/users";				
				Boolean failed = false;
				errLabel.setValue("");
				try {
					id.validate();
				}
				catch (InvalidValueException e) {
					errLabel.setValue(errLabel.getValue() +" " + e.getMessage());
					id.setValidationVisible(true);
					failed = true;
				}
				try {
					userName.validate();
				}
				catch (InvalidValueException e) {
					errLabel.setValue(errLabel.getValue() + " " + e.getMessage());
					userName.setValidationVisible(true);
					failed=true;
				}
				
				try {
					vorName.validate();
				}
				catch (InvalidValueException e) {
					errLabel.setValue(errLabel.getValue() + " " + e.getMessage());
					vorName.setValidationVisible(true);
					failed = true;
				}
				try {
					nachName.validate();
				}
				catch(InvalidValueException e) {
					errLabel.setValue(errLabel.getValue() + " " + e.getMessage());
					nachName.setValidationVisible(true);
					failed = true;
				}
				try {
					emailId.validate();
				}
				catch (InvalidValueException e) {
					errLabel.setValue(errLabel.getValue() + " " + e.getMessage());
					emailId.setValidationVisible(true);
					failed = true;
				}
	            if (!failed)
	            {
	              Notification.show("Everythig is OK !", Notification.Type.TRAY_NOTIFICATION);
	            }
			}});
/*	            RequestBuilder builder = new 
	            		RequestBuilder(RequestBuilder.GET, url);
	            
	            try {
	            	Request request = builder.sendRequest(null, new RequestCallback() {	            		
						
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (200 == response.getStatusCode()) {
								System.out.println(response.getStatusCode());
							}
							else {
								System.out.println(response.getText());
							}
						}
						
						@Override
						public void onError(Request request, Throwable exception) {							
							System.out.println("Could not connect to the server");
						}
					});
	            }
	            catch (RequestException e) {
	            	System.out.println("Could not connect to the server "+ e.getMessage());
				}
			}			
		});
*/        			
        layout.addComponents(id, userName, vorName, nachName, emailId, button, errLabel);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
