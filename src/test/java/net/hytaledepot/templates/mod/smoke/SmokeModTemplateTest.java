package net.hytaledepot.templates.mod.smoke;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class SmokeModTemplateTest {
  @Test
  void buildsLicensePayloadUsingDocumentedShape() {
    SmokeModTemplate template = new SmokeModTemplate();
    String payload = template.buildLicensePayload("asset-88", "HD-KEY-TEST");

    // Keep payload contract synchronized with docs examples.
    assertTrue(payload.contains("\"asset_id\":\"asset-88\""));
    assertTrue(payload.contains("\"license_key\":\"HD-KEY-TEST\""));
  }

  @Test
  void parseAllowedRecognizesExpectedBooleanForms() {
    SmokeModTemplate template = new SmokeModTemplate();

    assertTrue(template.parseAllowed("{\"allowed\":true}"));
    assertTrue(template.parseAllowed("{\"allowed\": true}"));
    assertFalse(template.parseAllowed("{\"allowed\":false}"));
  }
}
