package org.sj.utils.math.symbol.mathrender;


public interface Priority {
    static final int PARENTHESIS_PRIORITY = 4;
    static final int TEXT_PRIORITY = 3;
    static final int DIVISION_PRIORITY = 2;
    static final int PRODUCT_PRIORITY = 2;
    static final int SUM_PRIORITY = 1;
    static final int DEFAULT_PRIORITY = 0;

}
