package net.hytaledepot.templates.mod.smoke;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class SmokeModTemplate {
  private final Map<String, String> state = new ConcurrentHashMap<>();
  private final AtomicLong operations = new AtomicLong();

  public void onInitialize() {
    state.put("profile", "smoke-template");
    state.put("initializedAt", Instant.now().toString());
    state.put("enabled", "true");
    operations.incrementAndGet();
  }

  public void onShutdown() {
    state.put("enabled", "false");
    state.clear();
  }

  public void applySetting(String key, String value) {
    state.put(String.valueOf(key), String.valueOf(value));
    operations.incrementAndGet();
  }

  public String readSetting(String key, String fallback) {
    return state.getOrDefault(String.valueOf(key), String.valueOf(fallback));
  }

  public long operationCount() {
    return operations.get();
  }

  public String describeStatus() {
    return "stateEntries=" + state.size() + ", operations=" + operations.get() + ", enabled=" + readSetting("enabled", "false");
  }

  public String buildLicensePayload(String assetId, String key) {
    return "{" + "\"asset_id\":\"" + assetId + "\",\"license_key\":\"" + key + "\"}";
  }

  public boolean parseAllowed(String responseJson) {
    String body = String.valueOf(responseJson);
    return body.contains("\"allowed\":true") || body.contains("\"allowed\": true");
  }
}
