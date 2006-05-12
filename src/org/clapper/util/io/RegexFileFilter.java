/*---------------------------------------------------------------------------*\
  $Id: RegexFilenameFilter.java 5812 2006-05-12 00:38:16Z bmc $
  ---------------------------------------------------------------------------
  This software is released under a Berkeley-style license:

  Copyright (c) 2004-2006 Brian M. Clapper. All rights reserved.

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

package org.clapper.util.io;

import java.io.FileFilter;
import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <p><tt>RegexFileFilter</tt> implements a <tt>java.io.FileFilter</tt>
 * class that matches files using a regular expression. Multiple regular
 * expression filters can be combined using {@link AndFileFilter}
 * and/or {@link OrFileFilter} objects.</p>
 *
 * <p>A <tt>RegexFileFilter</tt> can be configured to operate on just the
 * simple file name, or on the file's path.</p>
 *
 * <p><tt>RegexFileFilter</tt> uses the <tt>java.util.regex</tt>
 * regular expression classes.</p>
 *
 * @see AndFileFilter
 * @see OrFileFilter
 * @see NotFileFilter
 * @see RegexFilenameFilter
 *
 * @version <tt>$Revision: 5812 $</tt>
 *
 * @author Copyright &copy; 2004-2006 Brian M. Clapper
 */
public class RegexFileFilter
    implements FileFilter
{
    /*----------------------------------------------------------------------*\
                            Private Data Items
    \*----------------------------------------------------------------------*/

    private FileFilterMatchType matchType = FileFilterMatchType.PATH;
    private Pattern pattern;

    /*----------------------------------------------------------------------*\
                            Constructor
    \*----------------------------------------------------------------------*/

    /**
     * Construct a new <tt>RegexFileFilter</tt> using the specified
     * pattern.
     *
     * @param regex     the regular expression to add
     * @param matchType <tt>FileFilterMatchType.FILENAME</tt> to match just the
     *                  filename, <tt>FileFilterMatchType.PATH</tt> to match
     *                  the path (via <tt>java.io.File.getPath()</tt>)
     *
     * @throws PatternSyntaxException  bad regular expression
     */
    public RegexFileFilter (String regex, FileFilterMatchType matchType)
        throws PatternSyntaxException
    {
        this.matchType = matchType;
        pattern = Pattern.compile (regex);
    }

    /**
     * Construct a new <tt>RegexFileFilter</tt> using the specified
     * pattern.
     *
     * @param regex      the regular expression to add
     * @param regexFlags regular expression compilation flags (e.g.,
     *                   <tt>Pattern.CASE_INSENSITIVE</tt>). See
     *                   the Javadocs for <tt>java.util.regex</tt> for
     *                   legal values.
     * @param matchType <tt>FileFilterMatchType.FILENAME</tt> to match just the
     *                  filename, <tt>FileFilterMatchType.PATH</tt> to match
     *                  the path (via <tt>java.io.File.getPath()</tt>)
     *
     * @throws PatternSyntaxException  bad regular expression
     */
    public RegexFileFilter (String              regex,
                            int                 regexFlags,
                            FileFilterMatchType matchType)
        throws PatternSyntaxException
    {
        this.matchType = matchType;
        pattern = Pattern.compile (regex, regexFlags);
    }

    /*----------------------------------------------------------------------*\
                              Public Methods
    \*----------------------------------------------------------------------*/

    /**
     * Determine whether a file is to be accepted or not, based on the
     * regular expressions in the <i>reject</i> and <i>accept</i> lists.
     *
     * @param file  The file to test. If the match type is
     *              <tt>FileFilterMatchType.FILENAME</tt>, then the value
     *              of <tt>file.getPath()</tt> is compared to the regular
     *              expression. If the match type is
     *              <tt>FileFilterMatchType.PATH</tt>, then the value of
     *              <tt>file.getName()</tt> is compared to the regular
     *              expression.
     *
     * @return <tt>true</tt> if the file matches, <tt>false</tt> if it doesn't
     */
    public boolean accept (File path)
    {
        String name = null;

        switch (matchType)
        {
            case PATH:
                name = path.getPath();
                break;

            case FILENAME:
                name = path.getName();
                break;

            default:
                assert (false);
        }

        return pattern.matcher (name).find();
    }
}