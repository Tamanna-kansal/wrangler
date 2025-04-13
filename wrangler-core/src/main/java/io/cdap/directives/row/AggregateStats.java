package io.cdap.directives.row;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.UsageDefinition;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A directive that aggregates byte sizes and durations from multiple rows into
 * a single row of total MB and seconds.
 */
public class AggregateStats implements Directive {

    private String byteSizeCol;
    private String timeDurationCol;
    private String outputSizeCol;
    private String outputTimeCol;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .define("byteSizeCol", TokenType.COLUMN)
                .define("timeDurationCol", TokenType.COLUMN)
                .define("outputSizeCol", TokenType.TEXT)
                .define("outputTimeCol", TokenType.TEXT)
                .build();
    }

    @Override
    public void initialize(Arguments args) {
        byteSizeCol = ((ColumnName) args.value("byteSizeCol")).value();
        timeDurationCol = ((ColumnName) args.value("timeDurationCol")).value();
        outputSizeCol = ((Text) args.value("outputSizeCol")).value();
        outputTimeCol = ((Text) args.value("outputTimeCol")).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext ctx) {
        long totalBytes = 0;
        long totalTimeMs = 0;

        for (Row row : rows) {
            Object sizeObj = row.getValue(byteSizeCol);
            Object durationObj = row.getValue(timeDurationCol);

            if (sizeObj != null && durationObj != null) {
                try {
                    long byteSize = Long.parseLong(sizeObj.toString());
                    long durationInMs = Long.parseLong(durationObj.toString());

                    totalBytes += byteSize;
                    totalTimeMs += durationInMs;
                } catch (NumberFormatException e) {
                    // Log the error or handle it as needed
                    e.printStackTrace();
                }
            }
        }

        List<Row> result = new ArrayList<>();
        Row out = new Row();

        // Convert totalBytes to MB and totalTimeMs to seconds
        out.add(outputSizeCol, totalBytes / (1024.0 * 1024.0)); // MB
        out.add(outputTimeCol, totalTimeMs / 1000.0); // Seconds

        result.add(out);
        return result;
    }
}
