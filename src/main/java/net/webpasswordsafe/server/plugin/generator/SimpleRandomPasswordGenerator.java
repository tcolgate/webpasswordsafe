/*
    Copyright 2008-2011 Josh Drummond

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
package net.webpasswordsafe.server.plugin.generator;

import java.util.Random;
import org.apache.log4j.Logger;


/**
 * Default implementation of PasswordGenerator
 * 
 * @author Josh Drummond
 *
 */
public class SimpleRandomPasswordGenerator implements PasswordGenerator
{
    private int passwordLength;
    private boolean allowLowercase;
    private boolean allowUppercase;
    private boolean allowNumeric;
    private String specialChars;
    private static Logger LOG = Logger.getLogger(SimpleRandomPasswordGenerator.class);

    /**
     * Constructor creating default length of 10
     */
    public SimpleRandomPasswordGenerator()
    {
        passwordLength = 10;
    }
    
    /* (non-Javadoc)
     * @see net.webpasswordsafe.server.plugin.generator.PasswordGenerator#generatePassword()
     */
    @Override
    public String generatePassword()
    {
        Random random = new Random();
        StringBuilder password = new StringBuilder(passwordLength);
        char[] allowedChars = getAllowedChars();
        for (int i = 0; i < passwordLength; i++)
        {
            password.append(allowedChars[random.nextInt(allowedChars.length)]);
        }
        return password.toString();
    }

    private char[] getAllowedChars()
    {
        int allowedCharSize = 0;
        if (allowLowercase)
        {
            allowedCharSize += 26;
        }
        if (allowUppercase)
        {
            allowedCharSize += 26;
        }
        if (allowNumeric)
        {
            allowedCharSize += 10;
        }
        allowedCharSize += specialChars.length();
        char[] allowedChars = new char[allowedCharSize];
        int i = 0;
        if (allowLowercase)
        {
            for (int c = 97; c <= 122; c++)
            {
                allowedChars[i] = (char)c;
                i++;
            }
        }
        if (allowUppercase)
        {
            for (int c = 65; c <= 90; c++)
            {
                allowedChars[i] = (char)c;
                i++;
            }
        }
        if (allowNumeric)
        {
            for (int c = 48; c <= 57; c++)
            {
                allowedChars[i] = (char)c;
                i++;
            }
        }
        for (int c = 0; c < specialChars.length(); c++)
        {
            allowedChars[i] = specialChars.charAt(c);
            i++;
        }
        LOG.debug("allowedChars="+String.valueOf(allowedChars));
        return allowedChars;
    }
    
    public int getPasswordLength()
    {
        return this.passwordLength;
    }

    public void setPasswordLength(int passwordLength)
    {
        this.passwordLength = passwordLength;
    }

    public boolean isAllowLowercase() {
        return allowLowercase;
    }

    public void setAllowLowercase(boolean allowLowercase) {
        this.allowLowercase = allowLowercase;
    }

    public boolean isAllowUppercase() {
        return allowUppercase;
    }

    public void setAllowUppercase(boolean allowUppercase) {
        this.allowUppercase = allowUppercase;
    }

    public boolean isAllowNumeric() {
        return allowNumeric;
    }

    public void setAllowNumeric(boolean allowNumeric) {
        this.allowNumeric = allowNumeric;
    }

    public String getSpecialChars() {
        return specialChars;
    }

    public void setSpecialChars(String specialChars) {
        this.specialChars = specialChars;
    }

}
