package com.toogoodtogo.application.shop.shopboard;

import com.toogoodtogo.domain.shop.shopboard.ShopBoard;
import com.toogoodtogo.domain.shop.shopboard.ShopBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopBoardService implements ShopBoardUseCase{
    private final ShopBoardRepository shopBoardRepository;

    @Override
    public List<ShopBoard> findAllShopBoards() { return shopBoardRepository.findAll(); }
}
