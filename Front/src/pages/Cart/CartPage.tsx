import React from "react";
import styled from "@emotion/styled";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopHeaderMenu from "../../components/organisms/ShopMenu/ShopHeaderMenu";
import CartTitle from "../../components/molecules/Cart/CartTitle";
import CartContentWrapper from "../../components/organisms/CartContentWrapper/CartContentWrapper";
import CartSelectionButtons from "../../components/molecules/Cart/CartSelectionButtons";
import PaymentContainer from "../../components/molecules/Cart/PaymentContainer";
import CartCards from "../../components/molecules/Cart/CartCards";

const CartPage = () => {
	return (
		// <PageTemplate header={<ShopHeaderMenu />} isFooter={false}>
		<Wrapper>
			<ShopHeaderMenu />
			<CartTitle />
			<CartSelectionButtons />
			<CartCards />
			<PaymentContainer />
			{/* // </PageTemplate> */}
		</Wrapper>
	);
};

export default CartPage;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
	padding-top: 92px;
	width: 1180px;
	height: 100vh;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	background-color: #ff1;
`;
