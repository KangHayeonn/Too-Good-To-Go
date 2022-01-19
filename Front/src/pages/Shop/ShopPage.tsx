import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopTemplate from "../../components/templates/ShopTemplate";
import Categories from "../../components/organisms/Categories/Categories";
import ShopMenuHeader from "../../components/molecules/Shop/ShopMenuHeader";
import ShopTitles from "../../components/organisms/Shop/ShopTitles";
import CartContainer from "../../components/molecules/Shop/CartContainer";
import MenuCards from "../../components/molecules/Shop/MenuCards";

const Shop: React.FC = () => {
	return (
		<PageTemplate
			header={<Categories />}
			isHeader
			section={<ShopTitles />}
			isSection
			isFooter={false}
		>
			<ShopMenuHeader>메뉴</ShopMenuHeader>
			<ShopTemplate
				menu={
					<div>
						<MenuCards />
					</div>
				}
				sidebar={<CartContainer>장바구니에 담기</CartContainer>}
			/>
		</PageTemplate>
	);
};

export default Shop;
