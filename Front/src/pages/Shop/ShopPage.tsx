import React from "react";
import { RouteComponentProps } from "react-router-dom";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopTemplate from "../../components/templates/ShopTemplate";
import Categories from "../../components/organisms/Categories/Categories";
import ShopMenuHeader from "../../components/molecules/Shop/ShopMenuHeader";
import ShopTitles from "../../components/organisms/Shop/ShopTitles";
import CartContainer from "../../components/molecules/Shop/CartContainer";
import MenuCards from "../../components/molecules/Shop/MenuCards";

interface matchParams {
	shopId: string;
}

const Shop: React.FC<RouteComponentProps<matchParams>> = ({ match }) => {
	const { shopId } = match.params;
	return (
		<PageTemplate
			header={<Categories />}
			isHeader
			section={<ShopTitles shopMatchId={shopId} isEdit={false} />}
			isSection
			isFooter={false}
		>
			<ShopMenuHeader isEdit={false} shopMatchId={shopId}>
				메뉴
			</ShopMenuHeader>
			<ShopTemplate
				menu={
					<div>
						<MenuCards shopMatchId={shopId} />
					</div>
				}
				isSide
				sidebar={<CartContainer>장바구니에 담기</CartContainer>}
			/>
		</PageTemplate>
	);
};

export default Shop;
