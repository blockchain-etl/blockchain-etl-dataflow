package io.blockchainetl.bitcoinetl;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;


@RunWith(JUnit4.class)
public class PubSubToBigQueryPipelineTest {

    @Rule
    public TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void testBlocksBasic() throws Exception {
        List<String> blockchainDataStrings = TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/blocksBasic.json");

        PCollection<String> blockchainData = p.apply("Blockchain", Create.of(blockchainDataStrings));

        PCollection<TableRow> tableRows = PubSubToBigQueryPipeline.buildPipeline(
            "2018-01-01T00:00:00Z", blockchainData);

        TestUtils.logPCollection(tableRows);

        PCollection<String> strings = tableRows.apply(ParDo.of(new TableRowsToStringsFn()));

        PAssert.that(strings).containsInAnyOrder(TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/blocksBasicExpected.json"));

        p.run().waitUntilFinish();
    }
}
