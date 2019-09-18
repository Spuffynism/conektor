package xyz.ndlr.infrastructure.provider.mathpix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Taken from:
 * https://github.com/Mathpix/android-sample/blob/master/app/src/main/java/com/app/mathpix_sample
 * /api/response/DetectionResult.java
 */
public class DetectionResult {
    @JsonProperty("detection_map")
    private DetectionMap detectionMap;
    private String error;
    @JsonProperty("latex")
    private String latex;
    @JsonProperty("latex_list")
    private List<String> latexList;
    private double latex_confidence;
    @JsonProperty("position")
    private Region region;

    /**
     * Added in the case of an incompatible mathML result.
     */
    @JsonProperty("mathml_error")
    private String mathMLError;

    /**
     * Added in the case of an incompatible Wolfram Alpha result.
     */
    @JsonProperty("wolfram_error")
    private String wolframError;

    public DetectionMap getDetectionMap() {
        return detectionMap;
    }

    public void setDetectionMap(DetectionMap detectionMap) {
        this.detectionMap = detectionMap;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String latex) {
        this.latex = latex;
    }

    public List<String> getLatexList() {
        return latexList;
    }

    public void setLatexList(List<String> latexList) {
        this.latexList = latexList;
    }

    public double getLatex_confidence() {
        return latex_confidence;
    }

    public void setLatex_confidence(double latex_confidence) {
        this.latex_confidence = latex_confidence;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getMathMLError() {
        return mathMLError;
    }

    public void setMathMLError(String mathMLError) {
        this.mathMLError = mathMLError;
    }

    public String getWolframError() {
        return wolframError;
    }

    public void setWolframError(String wolframError) {
        this.wolframError = wolframError;
    }

    public static class DetectionMap {
        /**
         * Contains a visual representation of a discrete dataset.
         */
        @JsonProperty("contains_chart")
        private double containsChart;
        /**
         * Contains a tabular representation of a discrete dataset.
         */
        @JsonProperty("contains_table")
        private double containsTable;
        /**
         * Contains a diagram.
         */
        @JsonProperty("contains_diagram")
        private double containsDiagram;
        /**
         * Contains a 1D, 2D, or 3D graph.
         */
        @JsonProperty("contains_graph")
        private double containsGraph;
        /**
         * Contains chart, table, diagram, or graph.
         */
        @JsonProperty("contains_geometry")
        private double containsGeometry;

        @JsonProperty("isInverted")
        private double isInverted;
        /**
         * No valid equation was detected.
         */
        @JsonProperty("is_not_math")
        private double isNotMath;
        /**
         * The image is taken of printed math, not handwritten math.
         */
        @JsonProperty("is_printed")
        private double isPrinted;
    }
}
