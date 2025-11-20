package pt.iade.ei.baraflygame.data.repository

import pt.iade.ei.baraflygame.data.db.PurchaseDao
import pt.iade.ei.baraflygame.data.db.PurchasableItemDao
import pt.iade.ei.baraflygame.data.model.Purchase

class PurchaseRepository(
    private val purchaseDao: PurchaseDao,
    private val itemDao: PurchasableItemDao
) {
    suspend fun makePurchase(userId: Int, itemId: Int): Long {
        val p = Purchase(userId = userId, itemId = itemId, timestamp = System.currentTimeMillis())
        return purchaseDao.insert(p)
    }

    suspend fun getPurchases(userId: Int) = purchaseDao.getByUser(userId)

    suspend fun getItems() = itemDao.getAll()
}