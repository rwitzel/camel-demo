package demos;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ",", crlf = "UNIX")
public class ArticleWeights {

    @DataField(pos = 1)
    public String sku;

    @DataField(pos = 2)
    public BigDecimal efWeight;

    @DataField(pos = 3)
    public BigDecimal mgWeight;

    @DataField(pos = 4)
    public BigDecimal gbWeight;

    @Override
    public String toString() {
        return "ArticleWeights{" +
                "sku='" + sku + '\'' +
                ", efWeight=" + efWeight +
                ", mgWeight=" + mgWeight +
                ", gbWeight=" + gbWeight +
                '}';
    }

}
