package com.toogoodtogo.application.shop.shopboard;

import com.toogoodtogo.domain.shop.shopboard.ShopBoard;

import java.util.List;

public interface ShopBoardUseCase {

    List<ShopBoard> findAllShopBoards();
}
