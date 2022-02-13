import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopTemplate from "../../components/templates/ShopTemplate";
import CartSelectionButtons from "../../components/molecules/Cart/CartSelectionButtons";
import PaymentContainer from "../../components/molecules/Cart/PaymentContainer";
import CartCards from "../../components/molecules/Cart/CartCards";
import Categories from "../../components/organisms/Categories/Categories";
import CartTitle from "../../components/molecules/Cart/CartTitle";

const CartPage: React.FC = () => {
	return (
		<PageTemplate
			header={<Categories />}
			isHeader
			isSection={false}
			isFooter={false}
		>
			<CartTitle>장바구니</CartTitle>
			<ShopTemplate
				menu={
					<div>
						<CartSelectionButtons />
						<CartCards />
					</div>
				}
				isSide
				sidebar={<PaymentContainer>선택 결제하기</PaymentContainer>}
			/>
		</PageTemplate>
	);
};

export default CartPage;
