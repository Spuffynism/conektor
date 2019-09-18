package xyz.ndlr.infrastructure.provider.mathpix.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LatexFormat {
    /**
     * Returns unmodified output
     */
    @JsonProperty("latex_raw")
    RAW,
    /**
     * Removes some extraneous spaces
     */
    @JsonProperty("latex_normal")
    NORMAL,
    /**
     * Shortens operators, converts periods to \cdot, replaces \longdiv with \frac, and replaces
     * unknown operators with \mathrm
     */
    @JsonProperty("latex_simplified")
    SIMPLIFIED,
    /**
     * Splits an array into lists where appropriate
     */
    @JsonProperty("latex_list")
    LIST,
    /**
     * Improves the visual appearance of the rendered latex
     */
    @JsonProperty("latex_styled")
    STYLED,
    /**
     * Returns a string containing the MathML for the recognized math
     */
    @JsonProperty("mathml")
    MATHML,
    /**
     * Returns a string that is compatible with the Wolfram Alpha engine.
     */
    @JsonProperty("wolfram")
    WOLFRAM;

    public static final LatexFormat DEFAULT = NORMAL;
}
