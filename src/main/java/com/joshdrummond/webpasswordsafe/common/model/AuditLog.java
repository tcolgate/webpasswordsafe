/*
    Copyright 2010 Josh Drummond

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
package com.joshdrummond.webpasswordsafe.common.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * Domain model POJO for an audit_log
 * 
 * @author Josh Drummond
 *
 */
@Entity
@Table(name="audit_log")
public class AuditLog extends LightEntity implements Serializable
{
    private static final long serialVersionUID = 2460784101759679672L;

    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    
    @Column(name="date", nullable=false, updatable=false)
    private Date date;

    @Column(name="username", length=64, nullable=false, updatable=false)
    private String username;

    @Column(name="ipaddress", length=17, nullable=false, updatable=false)
    private String ipaddress;

    @Column(name="action", length=50, nullable=false, updatable=false)
    private String action;

    @Column(name="target", nullable=false, updatable=false)
    @Type(type="text")
    private String target;

    @Column(name="success", nullable=false, updatable=false)
    @Type(type = "yes_no")
    private boolean success;

    @Column(name="message", nullable=false, updatable=false)
    @Type(type="text")
    private String message;

    public AuditLog(Date date, String username, String ipaddress, String action,
            String target, boolean success, String message)
    {
        super();
        this.date = date;
        this.username = (null==username) ? "" : username;
        this.ipaddress = (null==ipaddress) ? "" : ipaddress;
        this.action = (null==action) ? "" : action;
        this.target = (null==target) ? "" : target;
        this.success = success;
        this.message = (null==message) ? "" : message;
    }

    public long getId()
    {
        return id;
    }

    public Date getDate()
    {
        return date;
    }

    public String getUsername()
    {
        return username;
    }

    public String getIpaddress()
    {
        return ipaddress;
    }

    public String getAction()
    {
        return action;
    }

    public String getTarget()
    {
        return target;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }

}
