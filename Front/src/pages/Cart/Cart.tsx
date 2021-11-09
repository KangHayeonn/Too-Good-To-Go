import React from "react";
import styled from "@emotion/styled";
import PageTemplate from "../../components/templates/PageTemplate";
import ShopHeaderMenu from "../../components/organisms/ShopMenu/ShopHeaderMenu";
import CartTitle from "../../components/molecules/CartTitle";

const Cart = () => {
	return (
		<PageTemplate header={<ShopHeaderMenu />} isFooter>
			<Wrapper>
				<CartTitle />
			</Wrapper>
		</PageTemplate>
	);
};

export default Cart;

const Wrapper = styled.div`
	margin: 0;
	padding: 0;
`;
