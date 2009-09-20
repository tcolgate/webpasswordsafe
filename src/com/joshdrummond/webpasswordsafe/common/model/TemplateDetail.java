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
package com.joshdrummond.webpasswordsafe.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.gilead.pojo.java5.LightEntity;


/**
 * Domain model POJO for a template_detail
 * 
 * @author Josh Drummond
 *
 */
@Entity
@Table(name="template_details")
public class TemplateDetail extends LightEntity implements Serializable
{

	private static final long serialVersionUID = 8295900905712832170L;

	@Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    
    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;
    
    @Column(name="access_level", nullable=false)
    private int accessLevel;
    
    @ManyToOne
    @JoinColumn(name="template_id", nullable=false)
    private Template parent;
    
    
    public TemplateDetail()
    {
    }

    public long getId()
    {
        return this.id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public Subject getSubject()
    {
        return this.subject;
    }
    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }
    public int getAccessLevel()
    {
        return this.accessLevel;
    }
    public void setAccessLevel(int accessLevel)
    {
        this.accessLevel = accessLevel;
    }
    public Template getParent()
    {
        return this.parent;
    }
    public void setParent(Template parent)
    {
        this.parent = parent;
    }
    
}
