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

package org.clapper.util.cmdline;

import java.util.Comparator;

/**
 * Used solely by <tt>UsageInfo</tt>, this class contains information about
 * an option.
 *
 * @version <tt>$Revision$</tt>
 *
 * @see UsageInfo
 */
final class OptionInfo
{
    char    shortOption;
    String  longOption;
    String  argToken;
    String  explanation;

    OptionInfo (char   shortOption,
                String longOption,
                String argToken,
                String explanation)
    {
        this.shortOption = shortOption;
        this.longOption  = longOption;
        this.argToken    = argToken;
        this.explanation = explanation;
    }
}
