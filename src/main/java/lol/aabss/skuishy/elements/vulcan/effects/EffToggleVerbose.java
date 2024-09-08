package lol.aabss.skuishy.elements.vulcan.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import lol.aabss.skuishy.elements.vulcan.VulcanHook;
import me.frep.vulcan.api.VulcanAPI$Factory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Vulcan - Toggle Verbose")
@Description("Toggles verbose for a player.")
@Examples({
        "toggle verbose for player"
})
@Since("2.0")
public class EffToggleVerbose extends Effect {

    static{
        if (VulcanHook.vulcanEnabled()) {
            Skript.registerEffect(EffToggleVerbose.class,
                    "toggle [the] [vulcan] verbose for %players%"
            );
        }
    }

    private Expression<Player> p;

    @Override
    protected void execute(@NotNull Event event) {
        for (Player p : this.p.getArray(event)) {
            VulcanAPI$Factory.getApi().toggleVerbose(p);
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event event, boolean debug) {
        return "toggle verbose";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        p = (Expression<Player>) exprs[0];
        return true;
    }
}
