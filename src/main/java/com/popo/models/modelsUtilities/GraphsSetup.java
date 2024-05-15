package com.popo.models.modelsUtilities;

import java.util.List;
import java.util.Vector;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GraphsSetup {

    List<String> labels;

    List<String> values;

    public static GraphsSetup graphTime(Object[][] times) {
        GraphsSetup graphsSetup = new GraphsSetup(new Vector<String>(), new Vector<String>());
        for (Object[] objects : times) {
            // 0 => price_total
            // 1 => time
            graphsSetup.labels.add(objects[1].toString());
            graphsSetup.values.add(objects[0].toString());
        }
        return graphsSetup;
    }

    public GraphsSetup() {
    }

    public GraphsSetup(List<String> labels, List<String> values) {
        this.labels = labels;
        this.values = values;
    }
}
