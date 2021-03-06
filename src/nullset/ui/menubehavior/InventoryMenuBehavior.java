package nullset.ui.menubehavior;

import nullset.battle.Battle;
import nullset.battle.actions.ItemAction;
import nullset.battle.actions.PlayerChoiceAction;
import nullset.battle.fighters.Fighter;
import nullset.main.RootLayer;
import nullset.rpg.Item;
import nullset.rpg.PlayerInventory;
import nullset.ui.dialogbehavior.TextDialogBehavior;

import java.util.List;

import static nullset.main.RootLayer.GameState.BATTLE;
import static nullset.main.RootLayer.GameState.INGAME;
import static nullset.rpg.AttribEnums.Effect.NONE;

public class InventoryMenuBehavior extends MenuBehavior {

    public InventoryMenuBehavior() {
        title = "Inventory";

        List<Item> items = PlayerInventory.getInstance().getItems();
        elements = new String[items.size()+1];
        for (int i = 0; i < elements.length-1; i++) {
            elements[i] = items.get(i).itemName;
        }
        elements[elements.length-1] = "Back";
    }

    @Override
    public void onAction() {
        switch (elements[cursorPos]) {
            case "Back":
                onClose();
                break;
            default:
                Item i = PlayerInventory.getInstance().getItems().get(cursorPos);
                if (RootLayer.getInstance().getCurrentState() == INGAME && i.ingameEffect != NONE)
                {
                    i.useInGame();
                }
                else if (RootLayer.getInstance().getCurrentState() == BATTLE && i.battleEffect != NONE)
                {
                    // TODO handle different types of targeting for items
                    handler.openMenu(new TargetSelectBehavior(this::itemCallback, false,false,true));
                }
                else
                    handler.openDialog("You can't use this here.");
                break;
        }
    }

    @Override
    protected void onHighlight() {
        if (cursorPos == elements.length-1)
            handler.closeFlavorText();
        else {
            Item i = PlayerInventory.getInstance().getItems().get(cursorPos);
            handler.openFlavorText(i.description);
        }
    }

    private void itemCallback(Fighter... fighters) {
        Item i = PlayerInventory.getInstance().getItems().get(cursorPos);
        Battle.getCurrent().addAction(new ItemAction(i, fighters));
        PlayerChoiceAction.getInstance().markAsDone();
    }
}
