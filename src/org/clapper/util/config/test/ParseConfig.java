/*---------------------------------------------------------------------------*\
  $Id$
  ---------------------------------------------------------------------------
  This software is released under a Berkeley-style license:

  Copyright (c) 2004 Brian M. Clapper. All rights reserved.

  Redistribution and use in source and binary forms are permitted provided
  that: (1) source distributions retain this entire copyright notice and
  comment; and (2) modifications made to the software are prominently
  mentioned, and a copy of the original software (or a pointer to its
  location) are included. The name of the author may not be used to endorse
  or promote products derived from this software without specific prior
  written permission.

  THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
  WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.

  Effectively, this means you can do what you want with the software except
  remove this notice or take advantage of the author's name. If you modify
  the software and redistribute your modified version, you must indicate that
  your version is a modification of the original, and you must provide either
  a pointer to or a copy of the original.
\*---------------------------------------------------------------------------*/

package org.clapper.util.config.test;

import java.util.*;
import org.clapper.util.text.*;
import org.clapper.util.io.*;
import org.clapper.util.config.*;
import java.io.*;
import java.net.*;

/**
 * <p>Test the <code>Configuration</code> class.</p>
 *
 * @see UnixShellVariableSubstituter
 * @see VariableDereferencer
 * @see VariableSubstituter
 * @see java.lang.String
 *
 * @version <kbd>$Revision$</kbd>
 */
public class ParseConfig
{
    /*----------------------------------------------------------------------*\
                             Private Variables
    \*----------------------------------------------------------------------*/

    /*----------------------------------------------------------------------*\
                                Constructor
    \*----------------------------------------------------------------------*/

    private ParseConfig()
    {
    }

    /*----------------------------------------------------------------------*\
			       Main Program
    \*----------------------------------------------------------------------*/

    /**
     * Tester for this class. Invoke with no parameters for usage.
     *
     * @param args  Parameters.
     */
    public static void main (String args[])
    {
	if (args.length < 1)
            usage();

        String file = args[0];
        Collection vars = new ArrayList();
        for (int i = 1; i < args.length; i++)
            vars.add (args[i]);

        try
        {
            ParseConfig tester = new ParseConfig();
            tester.runTest (args[0], vars);
            System.exit (0);
        }

        catch (ConfigurationException ex)
        {
            new WordWrapWriter (System.err).println (ex.getMessage());
            System.exit (1);
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit (1);
        }
    }

    /*----------------------------------------------------------------------*\
                              Private Methods
    \*----------------------------------------------------------------------*/

    private static void usage()
    {
        System.err.println ("Usage: java " + ParseConfig.class +
                            " file|url [section:var=value] ... ");
        System.exit (1);
    }

    private void runTest (String thing, Collection vars)
        throws FileNotFoundException,
               IOException,
	       ConfigurationException,
               SectionExistsException,
               NoSuchElementException,
               VariableSubstitutionException
    {
        Configuration config = null;

	try
        {
            config = new Configuration (new URL (thing));
        }

        catch (MalformedURLException ex)
        {
            config = new Configuration (new File (thing));
        }

        for (Iterator it = vars.iterator(); it.hasNext(); )
        {
            String s = (String) it.next();

            int i = s.indexOf (':');
            if (i == -1)
            {
                throw new ConfigurationException ("Bad variable setting: \""
                                                + s
                                                + "\"");
            }

            String section = s.substring (0, i);

            i++;
            int j = s.indexOf ('=', i);
            if (i == -1)
            {
                throw new ConfigurationException ("Bad variable setting: \""
                                                + s
                                                + "\"");
            }

            String varName = s.substring (i, j);
            String value = s.substring (j + 1);

            if (! config.containsSection (section))
                config.addSection (section);

            config.setVariable (section, varName, value, true);
        }

        config.write (System.out);
    }
}
