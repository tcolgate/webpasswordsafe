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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.joshdrummond.webpasswordsafe.client.model.common.GroupDTO;
import com.joshdrummond.webpasswordsafe.client.model.common.UserDTO;

/**
 * @author Josh Drummond
 *
 */
public class GroupDialog extends DialogBox implements UserListener
{
    private GroupDTO group;
    private TextBox nameTextBox;
    private ListBox membersListBox;

    public GroupDialog(GroupDTO pGroup)
    {
        this.group = pGroup;
        setHTML("Group");

        final FlexTable flexTable = new FlexTable();
        setWidget(flexTable);
        flexTable.setSize("100%", "100%");

        final Label label = new Label("New Label");
        flexTable.setWidget(0, 0, label);

        final Label nameLabel = new Label("Name");
        flexTable.setWidget(0, 0, nameLabel);

        nameTextBox = new TextBox();
        flexTable.setWidget(0, 1, nameTextBox);
        nameTextBox.setWidth("100%");

        final Label membersLabel = new Label("Members");
        flexTable.setWidget(1, 0, membersLabel);

        membersListBox = new ListBox(true);
        flexTable.setWidget(1, 1, membersListBox);
        flexTable.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);
        membersListBox.setVisibleItemCount(5);

        final FlowPanel flowPanel = new FlowPanel();
        flexTable.setWidget(3, 0, flowPanel);
        flexTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
        flexTable.getFlexCellFormatter().setColSpan(3, 0, 3);

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

        final FlowPanel flowPanel_1 = new FlowPanel();
        flexTable.setWidget(2, 1, flowPanel_1);
        flexTable.getCellFormatter().setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_CENTER);

        final Button addButton = new Button();
        flowPanel_1.add(addButton);
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event)
            {
                doAddMembers();
            }
        });
        addButton.setText("Add");

        final Button removeButton = new Button();
        flowPanel_1.add(removeButton);
        removeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event)
            {
                doRemoveMembers();
            }
        });
        removeButton.setText("Remove");
        
        setFields();
    }
    
    /**
     * 
     */
    protected void doSave()
    {
        if (validateFields())
        {
            
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
    protected void doRemoveMembers()
    {
        while (membersListBox.getSelectedIndex() >= 0)
        {
            membersListBox.removeItem(membersListBox.getSelectedIndex());
        }
    }

    /**
     * 
     */
    protected void doAddMembers()
    {
        // fixme
        List<UserDTO> users = new ArrayList<UserDTO>(2);
        users.add(new UserDTO(5, "hillary", "Hillary Clinton", "h@clinton.net", true));
        users.add(new UserDTO(6, "edwards", "John Edwards", "j@edwards.net", true));
        new UserSelectionDialog(this, users, true).show();
    }

    /**
     * 
     */
    private void setFields()
    {
        nameTextBox.setText(group.getName());
        for (UserDTO user : group.getUsers())
        {
            membersListBox.addItem(user.getUsername(), String.valueOf(user.getId()));
        }
    }

    /**
     * 
     */
    private void doCancel()
    {
        hide();
    }

    /* (non-Javadoc)
     * @see com.joshdrummond.webpasswordsafe.client.ui.UserListener#doUsersChosen(java.util.List)
     */
    public void doUsersChosen(List<UserDTO> users)
    {
        for (UserDTO user : users)
        {
            group.addUser(user);
            membersListBox.addItem(user.getFullname(), String.valueOf(user.getId()));
        }
    }
}
