/*
    Copyright 2008 Josh Drummond

    This file is part of WebPasswordSafe.

    WebPasswordSafe is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    WebPasswordSafe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with WebPasswordSafe; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package com.joshdrummond.webpasswordsafe.client.ui;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.joshdrummond.webpasswordsafe.client.model.common.PasswordDTO;
import com.joshdrummond.webpasswordsafe.client.model.common.PermissionDTO;
import com.joshdrummond.webpasswordsafe.client.model.common.SubjectDTO;
import com.joshdrummond.webpasswordsafe.client.remote.PasswordService;

/**
 * @author Josh Drummond
 *
 */
public class PasswordDialog extends DialogBox implements PermissionListener
{
    private PasswordDTO password;
    private TextBox nameTextBox;
    private TextBox usernameTextBox;
    private TextBox passwordTextBox;
    private TextBox tagsTextBox;
    private TextArea notesTextArea;
    private CheckBox activeCheckBox;

    public PasswordDialog(PasswordDTO password)
    {
        this.password = password;
        setHTML("Password");

        final FlexTable flexTable = new FlexTable();
        setWidget(flexTable);
        flexTable.setSize("100%", "100%");

        final Label nameLabel = new Label("Name");
        flexTable.setWidget(0, 0, nameLabel);

        final Label usernameLabel = new Label("Username");
        flexTable.setWidget(1, 0, usernameLabel);

        final Label passwordLabel = new Label("Password");
        flexTable.setWidget(2, 0, passwordLabel);

        final Label tagsLabel = new Label("Tags");
        flexTable.setWidget(3, 0, tagsLabel);

        final Label notesLabel = new Label("Notes");
        flexTable.setWidget(4, 0, notesLabel);

        final Label permissionsLabel = new Label("Permissions");
        flexTable.setWidget(5, 0, permissionsLabel);

        nameTextBox = new TextBox();
        flexTable.setWidget(0, 1, nameTextBox);
        flexTable.getFlexCellFormatter().setColSpan(0, 1, 2);
        nameTextBox.setWidth("100%");

        usernameTextBox = new TextBox();
        flexTable.setWidget(1, 1, usernameTextBox);
        usernameTextBox.setWidth("100%");

        passwordTextBox = new TextBox();
        flexTable.setWidget(2, 1, passwordTextBox);
        passwordTextBox.setWidth("100%");

        tagsTextBox = new TextBox();
        flexTable.setWidget(3, 1, tagsTextBox);
        tagsTextBox.setWidth("100%");

        notesTextArea = new TextArea();
        flexTable.setWidget(4, 1, notesTextArea);
        flexTable.getFlexCellFormatter().setColSpan(4, 1, 2);
        notesTextArea.setWidth("100%");

        final Button editPermissionsButton = new Button();
        flexTable.setWidget(5, 1, editPermissionsButton);
        editPermissionsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)
			{
                doEditPermissions();
			}
        });
        editPermissionsButton.setText("Edit Permissions");

        final VerticalPanel verticalPanel = new VerticalPanel();
        flexTable.setWidget(2, 2, verticalPanel);

        final Button generateButton = new Button();
        verticalPanel.add(generateButton);
        generateButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event)
            {
                doGeneratePassword();
            }
        });
        generateButton.setText("Generate");

        final FlowPanel flowPanel = new FlowPanel();
        flexTable.setWidget(6, 0, flowPanel);
        flexTable.getCellFormatter().setHorizontalAlignment(6, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexTable.getFlexCellFormatter().setColSpan(6, 0, 3);

        final Button saveButton = new Button();
        flowPanel.add(saveButton);
        saveButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event)
            {
                doSave();
            }
        });
        saveButton.setText("Save");

        final Button cancelButton = new Button();
        flowPanel.add(cancelButton);
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event)
            {
                doCancel();
            }
        });
        cancelButton.setText("Cancel");

        activeCheckBox = new CheckBox();
        flexTable.setWidget(5, 2, activeCheckBox);
        activeCheckBox.setText("Active");
        
        setFields();
    }

    /**
     * 
     */
    protected void doGeneratePassword()
    {
        AsyncCallback<String> callback = new AsyncCallback<String>()
        {

            public void onFailure(Throwable caught)
            {
                Window.alert("Error: "+caught.getMessage());
            }

            public void onSuccess(String result)
            {
                passwordTextBox.setText(result);
            }
            
        };
        PasswordService.Util.getInstance().generatePassword(callback);
    }

    /**
     * 
     */
    protected void doSave()
    {
        if (validateFields())
        {
            password.setName(nameTextBox.getText().trim());
            password.setUsername(usernameTextBox.getText().trim());
            password.setCurrentPassword(passwordTextBox.getText().trim());
            password.setTags(tagsTextBox.getText().trim());
            password.setNotes(notesTextArea.getText().trim());
            password.setActive(activeCheckBox.getValue());
            
            AsyncCallback<Void> callback = new AsyncCallback<Void>()
            {

                public void onFailure(Throwable caught)
                {
                    Window.alert("Error: "+caught.getMessage());
                }

                public void onSuccess(Void result)
                {
                    hide();
                }
                
            };
            if (password.getId() == 0)
            {
                PasswordService.Util.getInstance().addPassword(password, callback);
            }
            else
            {
                PasswordService.Util.getInstance().updatePassword(password, callback);
            }
        }
    }

    /**
     * @return
     */
    private boolean validateFields()
    {
        return true;
    }

    /**
     * 
     */
    protected void doEditPermissions()
    {
        new PermissionDialog(this, password, new ArrayList<SubjectDTO>()).show();
    }

    /**
     * 
     */
    private void setFields()
    {
        nameTextBox.setText(password.getName());
        usernameTextBox.setText(password.getUsername());
        passwordTextBox.setText(password.getCurrentPassword());
        tagsTextBox.setText(password.getTags());
        notesTextArea.setText(password.getNotes());
        activeCheckBox.setValue(password.isActive());
    }

    /**
     * 
     */
    protected void doCancel()
    {
        hide();
    }

    /* (non-Javadoc)
     * @see com.joshdrummond.webpasswordsafe.client.ui.PermissionListener#doPermissionsChanged(java.util.List)
     */
    public void doPermissionsChanged(List<PermissionDTO> permissions)
    {
        // TODO Auto-generated method stub
        
    }

}
