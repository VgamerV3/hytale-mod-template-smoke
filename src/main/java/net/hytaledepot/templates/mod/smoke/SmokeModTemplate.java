package net.hytaledepot.templates.mod.smoke;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class SmokeModTemplate {
  // Shared state map for settings, feature flags, and runtime diagnostics.
  private final Map<String, String> state = new ConcurrentHashMap<>();
  // Tracks how many domain operations have been recorded.
  private final AtomicLong operations = new AtomicLong();

  public void onInitialize() {
    // Initialize default values that a real mod system can override via config files.
    state.put("template", "DevDocsSmoke");
    state.put("initializedAt", Instant.now().toString());
    state.put("enabled", "true");
  }

  public void onShutdown() {
    // Persist or export any required values before clearing runtime memory.
    state.put("enabled", "false");
    state.clear();
  }

  public void applySetting(String key, String value) {
    // Generic config mutation helper for mod tuning at runtime.
    state.put(String.valueOf(key), String.valueOf(value));
    operations.incrementAndGet();
  }

  public String readSetting(String key, String fallback) {
    // Safe getter used by gameplay logic to avoid null checks in callers.
    return state.getOrDefault(String.valueOf(key), String.valueOf(fallback));
  }

  public long operationCount() {
    // Returns a metric-like counter for integration tests and health pages.
    return operations.get();
  }

  public String describeStatus() {
    // Human-readable summary for logs, admin UIs, or diagnostics output.
    return "DevDocsSmokeMod[state=" + state.size() + ", operations=" + operations.get() + "]";
  }

  // Mirrors the Dev Docs licensing JSON shape for local validation tests.
  public String buildLicensePayload(String assetId, String key) {
    return "{" + "\"asset_id\":\"" + assetId + "\",\"license_key\":\"" + key + "\"}";
  }

  // Minimal parser used in tests to ensure response payload handling is deterministic.
  public boolean parseAllowed(String responseJson) {
    String body = String.valueOf(responseJson);
    return body.contains("\"allowed\":true") || body.contains("\"allowed\": true");
  }

}
