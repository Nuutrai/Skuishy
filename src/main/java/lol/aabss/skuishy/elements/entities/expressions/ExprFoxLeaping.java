package lol.aabss.skuishy.elements.entities.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Fox - Leaping")
@Description("Gets/sets the fox leaping state.")
@Examples({
        "set fox leaping state of {_fox} to true"
})
@Since("2.8")
public class ExprFoxLeaping extends SimplePropertyExpression<Entity, Boolean> {

    static {
        register(ExprFoxLeaping.class, Boolean.class, "[fox] leaping [mode|state]", "entities");
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "fox leaping state";
    }

    @Override
    public @Nullable Boolean convert(Entity entity) {
        if (entity instanceof Fox) {
            return ((Fox) entity).isLeaping();
        }
        return null;
    }

    @Override
    public @NotNull Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public Class<?> @NotNull [] acceptChange(Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Boolean.class);
        }
        return null;
    }

    @Override
    public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            if (delta[0] instanceof Boolean) {
                for (Entity entity : getExpr().getArray(e)) {
                    if (entity instanceof Fox) {
                        ((Fox) entity).setLeaping((Boolean) delta[0]);
                    }
                }
            }
        }
    }
}