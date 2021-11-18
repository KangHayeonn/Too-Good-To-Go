import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopTemplate from "../../components/templates/ShopTemplate";
import Categories from "../../components/organisms/Categories/Categories";
import ShopMenuHeader from "../../components/molecules/Shop/ShopMenuHeader";
import ShopTitles from "../../components/organisms/Shop/ShopTitles";
import CartSelectionButtons from "../../components/molecules/Cart/CartSelectionButtons";
import PaymentContainer from "../../components/molecules/Cart/PaymentContainer";
import CartCards from "../../components/molecules/Cart/CartCards";

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
						<CartSelectionButtons />
						<CartCards />
					</div>
				}
				sidebar={<PaymentContainer>장바구니에 담기</PaymentContainer>}
			/>
		</PageTemplate>
	);
};

export default Shop;
