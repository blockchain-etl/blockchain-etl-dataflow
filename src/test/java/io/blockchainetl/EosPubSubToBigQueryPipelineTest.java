package io.blockchainetl;

import com.google.api.services.bigquery.model.TableRow;
import io.blockchainetl.eos.fns.ConvertActionsToTableRowsFn;
import io.blockchainetl.eos.fns.ConvertBlocksToTableRowsFn;
import io.blockchainetl.common.PubSubToBigQueryPipeline;
import io.blockchainetl.common.TableRowsToStringsFn;
import io.blockchainetl.common.TestUtils;
import io.blockchainetl.eos.fns.ConvertTransactionsToTableRowsFn;
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
public class EosPubSubToBigQueryPipelineTest {

    @Rule
    public TestPipeline p = TestPipeline.create();

    @Test
    @Category(ValidatesRunner.class)
    public void testEosBlocks() throws Exception {
        List<String> blockchainData = TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/eos/block1000001.json");
        PCollection<String> collection = p.apply("Input", Create.of(blockchainData));
        
        PCollection<TableRow> tableRows = PubSubToBigQueryPipeline.buildPipeline(
            "Blocks", 
            collection,
            new ConvertBlocksToTableRowsFn("2018-01-01T00:00:00Z", Long.MAX_VALUE)
        );
       
        TestUtils.logPCollection(tableRows);

        PAssert.that(tableRows.apply("TableRowsToStringsFn", ParDo.of(new TableRowsToStringsFn())))
            .containsInAnyOrder(TestUtils.readLines(
                "testdata/PubSubToBigQueryPipelineTest/eos/block1000001Expected.json"));
        
        p.run().waitUntilFinish();
    }

    @Test
    @Category(ValidatesRunner.class)
    public void testEosTransactions() throws Exception {
        List<String> blockchainData = TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/eos/block1000001Transactions.json");
        PCollection<String> collection = p.apply("Input", Create.of(blockchainData));

        PCollection<TableRow> tableRows = PubSubToBigQueryPipeline.buildPipeline(
            "Transactions",
            collection,
            new ConvertTransactionsToTableRowsFn("2018-01-01T00:00:00Z", Long.MAX_VALUE)
        );

        TestUtils.logPCollection(tableRows);

        PAssert.that(tableRows.apply("TableRowsToStringsFn", ParDo.of(new TableRowsToStringsFn())))
            .containsInAnyOrder(TestUtils.readLines(
                "testdata/PubSubToBigQueryPipelineTest/eos/block1000001TransactionsExpected.json"));

        p.run().waitUntilFinish();
    }

    @Test
    @Category(ValidatesRunner.class)
    public void testEosActions() throws Exception {
        List<String> blockchainData = TestUtils.readLines(
            "testdata/PubSubToBigQueryPipelineTest/eos/block1000001Actions.json");
        PCollection<String> collection = p.apply("Input", Create.of(blockchainData));

        PCollection<TableRow> tableRows = PubSubToBigQueryPipeline.buildPipeline(
            "Actions",
            collection,
            new ConvertActionsToTableRowsFn("2018-01-01T00:00:00Z", Long.MAX_VALUE)
        );

        TestUtils.logPCollection(tableRows);

        PAssert.that(tableRows.apply("TableRowsToStringsFn", ParDo.of(new TableRowsToStringsFn())))
            .containsInAnyOrder(TestUtils.readLines(
                "testdata/PubSubToBigQueryPipelineTest/eos/block1000001ActionsExpected.json"));

        p.run().waitUntilFinish();
    }
}
