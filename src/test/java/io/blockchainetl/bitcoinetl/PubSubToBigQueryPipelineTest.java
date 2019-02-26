package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Map;


@RunWith(JUnit4.class)
public class PubSubToBigQueryPipelineTest {

    @Rule
    public TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void testDash() throws Exception {
        List<String> blockchainDataStrings = TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/dashBlock1000000.json");

        PCollection<String> blockchainData = p.apply("Blockchain", Create.of(blockchainDataStrings));

        Map<String, PCollection<TableRow>> tableRows = PubSubToBigQueryPipeline.buildPipeline(
            "2018-01-01T00:00:00Z", blockchainData);

        PCollection<TableRow> blocks = tableRows.get("block");
        PCollection<TableRow> transactions = tableRows.get("transaction");

        PCollection<TableRow> allTableRows = PCollectionList.of(blocks).and(transactions)
            .apply("Flatten", Flatten.pCollections());
        
        TestUtils.logPCollection(allTableRows);

        PAssert.that(allTableRows.apply("TransactionTableRowsToStringsFn", ParDo.of(new TableRowsToStringsFn())))
            .containsInAnyOrder(TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/dashBlock1000000Expected.json"));
        
        p.run().waitUntilFinish();
    }
}
