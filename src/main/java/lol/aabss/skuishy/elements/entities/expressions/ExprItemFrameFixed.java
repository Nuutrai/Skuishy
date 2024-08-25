package lol.aabss.skuishy.elements.entities.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Item Frame - Fixed state")
@Description("Gets/sets the fixed state of an item frame.")
@Examples({
        "set itemframe fixed mode of {_itemframe} to false"
})
@Since("2.8")
public class ExprItemFrameFixed extends SimplePropertyExpression<Entity, Boolean> {

    static {
        register(ExprItemFrameFixed.class, Boolean.class, "item[ |-]frame fixed [state|mode]", "entities");
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "fixed state";
    }

    @Override
    public @Nullable Boolean convert(Entity entity) {
        if (entity instanceof ItemFrame) {
            return ((ItemFrame) entity).isFixed();
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
                    if (entity instanceof ItemFrame) {
                        ((ItemFrame) entity).setFixed((Boolean) delta[0]);
                    }
                }
            }
        }
    }
}

