package com.ssamardzic;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public interface ScanningJob {

    ScanningType getType();

    String getQuery();

    List<URI> getUris();

    Future<Map<String, Integer>> initiate();
}
