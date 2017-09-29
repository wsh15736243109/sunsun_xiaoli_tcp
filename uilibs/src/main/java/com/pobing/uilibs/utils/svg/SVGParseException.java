package com.pobing.uilibs.utils.svg;

/**
 * Runtime exception thrown when there is a problem parsing an SVG.
 */
public class SVGParseException extends RuntimeException
{

    public SVGParseException(String s)
    {
        super(s);
    }

    public SVGParseException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public SVGParseException(Throwable throwable)
    {
        super(throwable);
    }
}
