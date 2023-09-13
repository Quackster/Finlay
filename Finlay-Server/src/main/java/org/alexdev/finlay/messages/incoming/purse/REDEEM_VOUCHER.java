package org.alexdev.finlay.messages.incoming.purse;

import org.alexdev.finlay.dao.mysql.CurrencyDao;
import org.alexdev.finlay.dao.mysql.PurseDao;
import org.alexdev.finlay.game.catalogue.CatalogueItem;
import org.alexdev.finlay.game.catalogue.CatalogueManager;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.purse.Voucher;
import org.alexdev.finlay.log.Log;
import org.alexdev.finlay.messages.outgoing.purse.VOUCHER_REDEEM_ERROR;
import org.alexdev.finlay.messages.outgoing.purse.VOUCHER_REDEEM_ERROR.RedeemError;
import org.alexdev.finlay.messages.outgoing.purse.VOUCHER_REDEEM_OK;
import org.alexdev.finlay.messages.outgoing.user.currencies.CREDIT_BALANCE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.DateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class REDEEM_VOUCHER implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException {
        if (!player.isLoggedIn()) {
            return;
        }

        String voucherName = reader.readString();

        //Check and get voucher
        Voucher voucher = PurseDao.redeemVoucher(voucherName, player.getDetails().getId());

        //No voucher was found
        if (voucher == null) {
            player.send(new VOUCHER_REDEEM_ERROR(RedeemError.INVALID));
            return;
        }

        //Redeem items
        List<Item> items = new ArrayList<>();
        List<CatalogueItem> redeemedItems = new ArrayList<>();

        for (String saleCode : voucher.getItems()) {
            var catalogueItem = CatalogueManager.getInstance().getCatalogueItem(saleCode);

            if (catalogueItem == null) {
                Log.getErrorLogger().error("Could not redeem voucher " + voucherName + " with sale code: " + saleCode);
                continue;
            }

            redeemedItems.add(catalogueItem);
            items.addAll(CatalogueManager.getInstance().purchase(player, catalogueItem, "", null, DateUtil.getCurrentTimeSeconds()));
        }

        //A voucher was found, so redeem items and redeem credits
        player.send(new VOUCHER_REDEEM_OK(redeemedItems));

        if (items.size() > 0) {
            player.getInventory().getView("new");
        }

        PurseDao.logVoucher(voucherName, player.getDetails().getId(), voucher.getCredits(), redeemedItems);

        //This voucher gives credits, so increase credits balance
        if (voucher.getCredits() > 0) {
            CurrencyDao.increaseCredits(player.getDetails(), voucher.getCredits());
            player.send(new CREDIT_BALANCE(player.getDetails()));
        }
    }
}
