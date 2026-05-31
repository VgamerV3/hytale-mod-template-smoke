package net.hytaledepot.templates.mod.smoke;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import javax.annotation.Nonnull;

public final class SmokeModPlugin extends JavaPlugin {
  private final SmokeModTemplate service = new SmokeModTemplate();

  public SmokeModPlugin(@Nonnull JavaPluginInit init) {
    super(init);
  }

  @Override
  protected void setup() {
    service.onInitialize();
    getCommandRegistry().registerCommand(new SmokeStatusCommand());
    getLogger().atInfo().log("[SmokeMod] setup complete");
  }

  @Override
  protected void shutdown() {
    service.onShutdown();
    getLogger().atInfo().log("[SmokeMod] shutdown complete");
  }

  private final class SmokeStatusCommand extends CommandBase {
    private SmokeStatusCommand() {
      super("hdsmokemodstatus", "Shows heartbeat and setup state for the smoke mod.");
      setAllowsExtraArguments(true);
      setPermissionGroups("hytale:Adventurer");
    }

    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
      ctx.sendMessage(Message.raw("[SmokeMod] sender=" + ctx.sender().getUsername() + ", " + service.describeStatus()));
    }
  }
}
