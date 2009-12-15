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
package com.joshdrummond.webpasswordsafe.server.plugin.encryption;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.junit.Test;
import com.joshdrummond.webpasswordsafe.server.plugin.encryption.JasyptDigester;
import static org.junit.Assert.*;


/**
 * @author Josh Drummond
 *
 */
public class JasyptDigesterTest
{

    /**
     * Test method for {@link com.joshdrummond.webpasswordsafe.server.plugin.encryption.JasyptDigester#check(java.lang.String, java.lang.String)}.
     */
	@Test
    public void testCheck()
    {
        JasyptDigester digester = new JasyptDigester();
        digester.setPasswordEncryptor(new StrongPasswordEncryptor());
        String password1 = digester.digest("josh");
        System.out.println("password1="+password1);
        String password2 = digester.digest("josh");
        System.out.println("password2="+password2);
        assertTrue(digester.check("josh", password1));
        assertTrue(digester.check("josh", password2));
        assertFalse(password1.equals(password2));
    }

}
